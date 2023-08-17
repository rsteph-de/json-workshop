package edu.example.json.processing.jsonp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;
import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonGeneratorFactory;
import jakarta.json.stream.JsonParser;

// https://openliberty.io/docs/latest/json-p-b.html
// https://rieckpil.de/whatis-json-processing-json-p/
 
public class BLJsonpSerializer implements BLJsonProcessor {

    public Booklist readBooklist(String resource) {
        try (JsonParser parser = Json.createParser(getClass().getResourceAsStream(resource))) {
            return process(parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (JsonParser parser = Json.createParser(Files.newBufferedReader(path))) {
            return process(parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Booklist process(JsonParser parser) {
        Booklist booklist = new Booklist();

        parser.next(); //StartObject
        while (parser.next() != JsonParser.Event.END_OBJECT) {
            // Evaluate each property name and extract the value
            //String field = Objects.requireNonNullElse(parser.getCurrentName(), parser.nextFieldName());
            //switch (field) {
            String key = parser.getString();
            parser.next(); //value
            switch (key) {
                case "category" -> booklist.setCategory(parser.getString());
                case "calendarWeek" -> booklist.setCalendarWeek(parser.getString());
                case "items" -> {
                    booklist.setItems(new ArrayList<Book>());
                    while (parser.next() != JsonParser.Event.END_ARRAY) {
                        Book book = new Book();
                        while (parser.next() != JsonParser.Event.END_OBJECT) {
                            String keyB = parser.getString();
                            parser.next();
                            switch (keyB) {
                                case "pos" -> book.setPos(parser.getInt());
                                //check;
                                case "newListing" -> book.setNewListing(parser.getValue().equals(JsonValue.TRUE));
                                case "weeks" -> book.setWeeks(parser.getInt());
                                case "title" -> book.setTitle(parser.getString());
                                case "authors" -> {
                                    book.setAuthors(new ArrayList<Author>());
                                    while (parser.next() != JsonParser.Event.END_ARRAY) {
                                        Author author = new Author();
                                        while (parser.next() != JsonParser.Event.END_OBJECT) {
                                            String keyA = parser.getString();
                                            parser.next();
                                            switch (keyA) {
                                                case "lastname" -> author.setLastname(parser.getString());
                                                case "firstname" -> author.setFirstname(parser.getString());
                                            }
                                        }
                                        book.getAuthors().add(author);
                                    }
                                }
                                case "publisher" -> book.setPublisher(parser.getString());
                                case "price" -> book.setPrice(((JsonNumber) parser.getValue()).doubleValue());

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
        JsonGeneratorFactory genFac = Json.createGeneratorFactory(Map.of(JsonGenerator.PRETTY_PRINTING, true));
        try (JsonGenerator gen = genFac.createGenerator(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            gen.writeStartObject()
                .write("category", booklist.getCategory())
                .write("calendarWeek", booklist.getCalendarWeek())
                .writeStartArray("items");
            booklist.getItems().forEach(book -> {
                gen.writeStartObject()
                    .write("pos", book.getPos())
                    .write("newListing", book.isNewListing())
                    .write("weeks", book.getWeeks())
                    .write("title", book.getTitle())
                    .writeStartArray("authors");
                book.getAuthors().forEach(author -> {
                    gen.writeStartObject()
                        .write("lastname", author.getLastname())
                        .write("firstname", author.getFirstname())
                        .writeEnd(); //object author
                });
                gen.writeEnd(); //array author;
                gen.write("publisher", book.getPublisher())
                    .write("price", book.getPrice())
                    .writeEnd(); //object book
            });
            gen.writeEnd(); //array books
            gen.writeEnd(); //object booklist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
