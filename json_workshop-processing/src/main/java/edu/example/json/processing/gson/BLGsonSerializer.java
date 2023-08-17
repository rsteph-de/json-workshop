package edu.example.json.processing.gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;

// https://openliberty.io/docs/latest/json-p-b.html
// https://rieckpil.de/whatis-json-processing-json-p/
// https://mkyong.com/java/gson-streaming-to-read-and-write-json/

public class BLGsonSerializer implements BLJsonProcessor {

    public Booklist readBooklist(String resource) {
        try (JsonReader reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(resource)))) {
            return process(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (JsonReader reader = new JsonReader(Files.newBufferedReader(path))) {
            return process(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Booklist process(JsonReader reader) throws IOException {
        Booklist booklist = new Booklist();
        
        reader.beginObject();
        while (reader.peek() != JsonToken.END_OBJECT) {
            switch (reader.nextName()) {
                case "category" -> booklist.setCategory(reader.nextString());
                case "calendarWeek" -> booklist.setCalendarWeek(reader.nextString());
                case "items" -> {
                    booklist.setItems(new ArrayList<Book>());
                    reader.beginArray();
                    while (reader.peek() != JsonToken.END_ARRAY) {
                        Book book = new Book();
                        reader.beginObject();
                        while (reader.peek() != JsonToken.END_OBJECT) {
                            switch (reader.nextName()) {
                                case "pos" -> book.setPos(reader.nextInt());
                                //check;
                                case "newListing" -> book.setNewListing(reader.nextBoolean());
                                case "weeks" -> book.setWeeks(reader.nextInt());
                                case "title" -> book.setTitle(reader.nextString());
                                case "authors" -> {
                                    book.setAuthors(new ArrayList<Author>());
                                    reader.beginArray();
                                    while (reader.peek() != JsonToken.END_ARRAY) {
                                        Author author = new Author();
                                        reader.beginObject();
                                        while (reader.peek() != JsonToken.END_OBJECT) {
                                            switch (reader.nextName()) {
                                                case "lastname" -> author.setLastname(reader.nextString());
                                                case "firstname" -> author.setFirstname(reader.nextString());
                                            }
                                        }
                                        reader.endObject();
                                        book.getAuthors().add(author);
                                    }
                                    reader.endArray();
                                }
                                case "publisher" -> book.setPublisher(reader.nextString());
                                case "price" -> book.setPrice(reader.nextDouble());

                            }
                        }
                        reader.endObject();
                        booklist.getItems().add(book);
                    }
                    reader.endArray();
                }
            }
        }
        reader.endObject();
        
        return booklist;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        try (JsonWriter w = new JsonWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            w.setIndent("  ");
            w.beginObject()
                .name("calendarWeek").value(booklist.getCalendarWeek())
                .name("category").value(booklist.getCategory())
                .name("items").beginArray();
            for (Book book : booklist.getItems()) {
                w.beginObject()
                    .name("pos").value(book.getPos())
                    .name("newListing").value(book.isNewListing())
                    .name("weeks").value(book.getWeeks())
                    .name("title").value(book.getTitle())
                    .name("authors").beginArray();
                for (Author author : book.getAuthors()) {
                    w.beginObject()
                        .name("lastname").value(author.getLastname())
                        .name("firstname").value(author.getFirstname())
                        .endObject(); //object author
                }
                w.endArray(); //array author;
                w.name("publisher").value(book.getPublisher())
                    .name("price").value(book.getPrice())
                    .endObject(); //object book
            }
            w.endArray(); //array books
            w.endObject(); //object booklist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
