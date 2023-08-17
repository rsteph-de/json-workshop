package edu.example.json.processing.gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;

public class BLGsonGenerator implements BLJsonProcessor {

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
        
        JsonElement json = JsonParser.parseReader(reader);
        JsonObject jsonBooklist = json.getAsJsonObject();
        booklist.setCategory(jsonBooklist.getAsJsonPrimitive("category").getAsString());
        booklist.setCalendarWeek(jsonBooklist.getAsJsonPrimitive("calendarWeek").getAsString());
        booklist.setItems(new ArrayList<Book>());
        JsonArray jsonItems = jsonBooklist.getAsJsonArray("items");
        for (JsonElement elBook : jsonItems) {
            JsonObject jsonBook = elBook.getAsJsonObject();
            Book book = new Book();
            book.setPos(jsonBook.getAsJsonPrimitive("pos").getAsInt());
            book.setNewListing(jsonBook.getAsJsonPrimitive("newListing").getAsBoolean());
            book.setWeeks(jsonBook.getAsJsonPrimitive("weeks").getAsInt());
            book.setTitle(jsonBook.getAsJsonPrimitive("title").getAsString());
            book.setAuthors(new ArrayList<Author>());
            for (JsonElement elAuthor : jsonBook.getAsJsonArray("authors")) {
                JsonObject jsonAuthor = elAuthor.getAsJsonObject();
                Author author = new Author();
                author.setLastname(jsonAuthor.getAsJsonPrimitive("lastname").getAsString());
                author.setFirstname(jsonAuthor.getAsJsonPrimitive("firstname").getAsString());
                book.getAuthors().add(author);
            }
            book.setPublisher(jsonBook.getAsJsonPrimitive("publisher").getAsString());
            book.setPrice(jsonBook.getAsJsonPrimitive("price").getAsDouble());
            booklist.getItems().add(book);
        }
        return booklist;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        JsonObject jsonBooklist = new JsonObject();
        jsonBooklist.addProperty("category", booklist.getCategory());
        jsonBooklist.addProperty("calendarWeek", booklist.getCalendarWeek());
        jsonBooklist.add("items", new JsonArray());
        for (Book book : booklist.getItems()) {
            JsonObject jsonBook = new JsonObject();
            jsonBook.addProperty("pos", book.getPos());
            jsonBook.addProperty("newListing", book.isNewListing());
            jsonBook.addProperty("weeks", book.getWeeks());
            jsonBook.addProperty("title", book.getTitle());
            jsonBook.add("authors", new JsonArray());
            for (Author author : book.getAuthors()) {
                JsonObject jsonAuthor = new JsonObject();
                jsonAuthor.addProperty("lastname", author.getLastname());
                jsonAuthor.addProperty("firstname", author.getFirstname());
                jsonBook.getAsJsonArray("authors").add(jsonAuthor);
            }
            jsonBook.addProperty("publisher", book.getPublisher());
            jsonBook.addProperty("price", book.getPrice());
            jsonBooklist.getAsJsonArray("items").add(jsonBook);
        }

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonBooklist, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
