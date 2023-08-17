package edu.example.json.processing.jackson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;

public class BLJacksonBuilder implements BLJsonProcessor {
    private ObjectMapper om = new ObjectMapper();

    public Booklist readBooklist(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            Booklist booklist = om.readValue(reader, Booklist.class);
            return booklist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Booklist booklist = om.readValue(reader, Booklist.class);
            return booklist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            om.writerWithDefaultPrettyPrinter().writeValue(writer, booklist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
