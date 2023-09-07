package edu.example.json.advanced.gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import edu.example.json.advanced.BLJsonProcessor;
import edu.example.json.advanced.gson.adapter.AuthorAdapter;
import edu.example.json.advanced.gson.adapter.InstantAdapter;
import edu.example.json.advanced.gson.adapter.LocalDateTimeAdapter;
import edu.example.json.advanced.gson.adapter.Price2Adapter;
import edu.example.json.advanced.gson.adapter.ZonedDateTimeAdapter;
import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Price2;

//Probleme mit der Default-Serialisierung für java.util.Date:
//Ausgabe ist sprachabhängig "publishedAt": "Okt. 20, 1969"
//Der String konnte reverse nicht zurück in ein Date konvertiert werden.
//siehe Implementierung: com.google.gson.internal.bind.DateTypeAdapter

//neue DatumsKlassen brauchen eigene Adapter:
// Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Instant.class, new InstantAdapter())
//                .build();

public class BGsonBuilder implements BLJsonProcessor {
    private Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Instant.class, new InstantAdapter())
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
        .registerTypeAdapter(Author.class, new AuthorAdapter())
        .registerTypeAdapter(Price2.class, new Price2Adapter())
        .create();

    public Book readBook(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            return gson.fromJson(reader, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book readBook(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return gson.fromJson(reader, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBook(Path path, Book book) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            JsonElement jsonBook = gson.toJsonTree(book);
            gson.toJson(jsonBook, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
