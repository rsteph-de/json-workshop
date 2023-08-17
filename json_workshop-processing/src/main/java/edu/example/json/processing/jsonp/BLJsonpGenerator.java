package edu.example.json.processing.jsonp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;

// https://openliberty.io/docs/latest/json-p-b.html
// https://rieckpil.de/whatis-json-processing-json-p/
// https://docs.oracle.com/javaee/7/tutorial/jsonp001.htm

public class BLJsonpGenerator implements BLJsonProcessor {

    public Booklist readBooklist(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            return process(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return process(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Booklist process(Reader reader) {
        Booklist booklist = new Booklist();
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonBooklist = jsonReader.readObject();
        booklist.setCategory(jsonBooklist.getString("category"));
        booklist.setCalendarWeek(jsonBooklist.getString("calendarWeek"));
        booklist.setItems(new ArrayList<Book>());
        jsonBooklist.getJsonArray("items").forEach(x -> {
            JsonObject jsonBook = x.asJsonObject();
            Book book = new Book();
            book.setPos(jsonBook.getInt("pos"));
            book.setNewListing(jsonBook.getBoolean("newListing"));
            book.setWeeks(jsonBook.getInt("weeks"));
            book.setTitle(jsonBook.getString("title"));
            book.setAuthors(new ArrayList<Author>());
            jsonBook.getJsonArray("authors").forEach(a -> {
                JsonObject jsonAuthor = a.asJsonObject();
                Author author = new Author();
                author.setLastname(jsonAuthor.getString("lastname"));
                author.setFirstname(jsonAuthor.getString("firstname"));
                book.getAuthors().add(author);
            });
            book.setPublisher(jsonBook.getString("publisher"));
            book.setPrice(jsonBook.getJsonNumber("price").doubleValue());
            booklist.getItems().add(book);
        });
        return booklist;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        JsonObject jsonBL = Json.createObjectBuilder()
            .add("category", booklist.getCategory())
            .add("calendarWeek", booklist.getCalendarWeek())
            .add("items", Json.createArrayBuilder(
                booklist.getItems().stream()
                    .map(book -> Json.createObjectBuilder()
                        .add("pos", book.getPos())
                        .add("newListing", book.isNewListing())
                        .add("weeks", book.getWeeks())
                        .add("title", book.getTitle())
                        .add("authors", Json.createArrayBuilder(
                            book.getAuthors().stream()
                                .map(author -> Json.createObjectBuilder()
                                    .add("lastname", author.getLastname())
                                    .add("firstname", author.getFirstname())
                                    .build())
                                .collect(Collectors.toList()))
                            .build())
                        .add("publisher", book.getPublisher())
                        .add("price", book.getPrice())
                        .build())
                    .collect(Collectors.toList()))
                .build())
            .build();

        JsonWriterFactory writerFac = Json.createWriterFactory(Map.of(JsonGenerator.PRETTY_PRINTING, true));
        try (JsonWriter jsonWriter = writerFac.createWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            jsonWriter.write(jsonBL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
