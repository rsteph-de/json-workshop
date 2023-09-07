package edu.example.json.advanced.jsonb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import edu.example.json.advanced.BLJsonProcessor;
import edu.example.json.advanced.jsonb.adapter.AuthorAdapter;
import edu.example.json.advanced.jsonb.adapter.Price2Adapter;
import edu.example.json.model.Book;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

public class BJsonBBuilder implements BLJsonProcessor {
    
    private JsonbConfig cfg = new JsonbConfig()
        .withFormatting(true)
        .withAdapters(new AuthorAdapter(), new Price2Adapter());
    private Jsonb jsonb = JsonbBuilder.create(cfg);
    
    public Book readBook(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            return jsonb.fromJson(reader, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book readBook(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return jsonb.fromJson(reader, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBook(Path path, Book book) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            jsonb.toJson(book, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
