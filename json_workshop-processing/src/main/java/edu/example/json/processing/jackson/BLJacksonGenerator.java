package edu.example.json.processing.jackson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;

public class BLJacksonGenerator implements BLJsonProcessor {
    private ObjectMapper om = new ObjectMapper();

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

    private Booklist process(Reader reader) throws IOException {
        Booklist booklist = new Booklist();
        ObjectNode jsonBooklist = (ObjectNode) om.readTree(reader);
        booklist.setCategory(jsonBooklist.get("category").asText());
        booklist.setCalendarWeek(jsonBooklist.get("calendarWeek").asText());
        booklist.setItems(new ArrayList<Book>());
        for (Iterator<JsonNode> jsonItemsIter = jsonBooklist.withArray("items").elements();
            jsonItemsIter.hasNext();) {
            ObjectNode jsonBook = (ObjectNode) jsonItemsIter.next();
            Book book = new Book();
            book.setPos(jsonBook.get("pos").asInt());
            book.setNewListing(jsonBook.get("newListing").asBoolean());
            book.setWeeks(jsonBook.get("weeks").asInt());
            book.setTitle(jsonBook.get("title").asText());
            book.setAuthors(new ArrayList<Author>());
            for (Iterator<JsonNode> jsonAuthorsIter = jsonBook.withArray("authors").elements();
                jsonAuthorsIter.hasNext();) {
                ObjectNode jsonAuthor = (ObjectNode) jsonAuthorsIter.next();
                Author author = new Author();
                author.setLastname(jsonAuthor.get("lastname").asText());
                author.setFirstname(jsonAuthor.get("firstname").asText());
                book.getAuthors().add(author);
            }
            book.setPublisher(jsonBook.get("publisher").asText());
            book.setPrice(jsonBook.get("price").asDouble());
            booklist.getItems().add(book);
        }

        return booklist;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        ObjectNode jsonBooklist = om.createObjectNode();
        jsonBooklist.put("category", booklist.getCategory());
        jsonBooklist.put("calendarWeek", booklist.getCalendarWeek());
        ArrayNode jsonBooks = jsonBooklist.putArray("items");
        for (Book book : booklist.getItems()) {
            ObjectNode jsonBook = om.createObjectNode();
            jsonBook.put("pos", book.getPos());
            jsonBook.put("newListing", book.isNewListing());
            jsonBook.put("weeks", book.getWeeks());
            jsonBook.put("title", book.getTitle());
            ArrayNode jsonAuthors = jsonBook.putArray("authors");
            for (Author author : book.getAuthors()) {
                ObjectNode jsonAuthor = om.createObjectNode();
                jsonAuthor.put("lastname", author.getLastname());
                jsonAuthor.put("firstname", author.getFirstname());
                jsonAuthors.add(jsonAuthor);
            }
            jsonBook.put("publisher", book.getPublisher());
            jsonBook.put("price", book.getPrice());
            jsonBooks.add(jsonBook);
        }

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            om.writerWithDefaultPrettyPrinter().writeValue(writer, jsonBooklist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
