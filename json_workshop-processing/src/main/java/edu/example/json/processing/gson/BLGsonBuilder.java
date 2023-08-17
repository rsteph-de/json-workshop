package edu.example.json.processing.gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;

public class BLGsonBuilder implements BLJsonProcessor {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Booklist readBooklist(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            return gson.fromJson(reader, Booklist.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return gson.fromJson(reader, Booklist.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            JsonElement jsonBooklist = gson.toJsonTree(booklist);
            gson.toJson(jsonBooklist, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
