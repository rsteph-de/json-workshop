package edu.example.json.processing.jackson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;

// https://cassiomolin.com/2019/08/19/combining-jackson-streaming-api-with-objectmapper-for-parsing-json/
// https://jenkov.com/tutorials/java-json/jackson-objectmapper.html#write-json-from-objects

public class BLJacksonSerializer implements BLJsonProcessor {
    private ObjectMapper om = new ObjectMapper();

    public Booklist readBooklist(String resource) {
        try (JsonParser parser = om.createParser(new InputStreamReader(getClass().getResourceAsStream(resource)))) {
            return process(parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (JsonParser parser = om.createParser(Files.newBufferedReader(path))) {
            return process(parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Booklist process(JsonParser parser) throws IOException {
        Booklist booklist = new Booklist();

        parser.nextToken(); //StartObject
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            // parser on "FIELD_NAME" after first call
            switch (parser.getCurrentName()) {
                case "category" -> booklist.setCategory(parser.nextTextValue());
                case "calendarWeek" -> booklist.setCalendarWeek(parser.nextTextValue());
                case "items" -> {
                    booklist.setItems(new ArrayList<Book>());
                    parser.nextToken(); //StartArray
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        Book book = new Book();
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                            // Evaluate each property name and extract the value
                            switch (parser.getCurrentName()) {
                                case "pos" -> book.setPos(parser.nextIntValue(0));
                                case "newListing" -> book.setNewListing(parser.nextBooleanValue());
                                case "weeks" -> book.setWeeks(parser.nextIntValue(0));
                                case "title" -> book.setTitle(parser.nextTextValue());
                                case "authors" -> {
                                    book.setAuthors(new ArrayList<Author>());
                                    parser.nextToken(); // StartArray
                                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                                        Author author = new Author();
                                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                                            switch (parser.getCurrentName()) {
                                                case "lastname" -> author.setLastname(parser.nextTextValue());
                                                case "firstname" -> author.setFirstname(parser.nextTextValue());
                                            }
                                        }
                                        book.getAuthors().add(author);
                                    }
                                }
                                case "publisher" -> book.setPublisher(parser.nextTextValue());
                                case "price" -> {
                                    // kein parser.nextDoubleValue();
                                    parser.nextToken();
                                    book.setPrice(parser.getValueAsDouble());
                                }
                            }
                        }
                        booklist.getItems().add(book);
                    }
                }
            }
        }
        return booklist;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            JsonGenerator gen = om.writerWithDefaultPrettyPrinter().createGenerator(writer);
            gen.writeStartObject(); //booklist;
            gen.writeStringField("category", booklist.getCategory());
            gen.writeStringField("calendarWeek", booklist.getCalendarWeek());
            gen.writeArrayFieldStart("items"); //items

            for (Book book : booklist.getItems()) {
                gen.writeStartObject(); //book;

                gen.writeNumberField("pos", book.getPos());
                gen.writeBooleanField("newListing", book.isNewListing());
                gen.writeNumberField("weeks", book.getWeeks());
                gen.writeStringField("title", book.getTitle());
                gen.writeArrayFieldStart("authors");
                for (Author author : book.getAuthors()) {
                    gen.writeStartObject(); //author
                    gen.writeStringField("lastname", author.getLastname());
                    gen.writeStringField("firstname", author.getFirstname());
                    gen.writeEndObject(); //author

                }
                gen.writeEndArray();
                gen.writeStringField("publisher", book.getPublisher());
                gen.writeNumberField("price", book.getPrice());
                gen.writeEndObject(); //book
            }
            gen.writeEndArray(); // items
            gen.writeEndObject(); //booklist

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
