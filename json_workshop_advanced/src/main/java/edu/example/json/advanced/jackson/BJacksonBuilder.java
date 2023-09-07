package edu.example.json.advanced.jackson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import edu.example.json.advanced.BLJsonProcessor;
import edu.example.json.advanced.jackson.adapter.AuthorDeserializer;
import edu.example.json.advanced.jackson.adapter.AuthorSerializer;
import edu.example.json.advanced.jackson.adapter.Price2Deserializer;
import edu.example.json.advanced.jackson.adapter.Price2Serializer;
import edu.example.json.model.Author;
import edu.example.json.model.Book;
import edu.example.json.model.Price2;

public class BJacksonBuilder implements BLJsonProcessor {
    
    private ObjectMapper om = new ObjectMapper();
    
    public BJacksonBuilder() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Author.class, new AuthorDeserializer());
        module.addSerializer(Author.class, new AuthorSerializer());
        module.addDeserializer(Price2.class, new Price2Deserializer());
        module.addSerializer(Price2.class, new Price2Serializer());
        
        om.registerModule(module);
        om.registerModule(new JavaTimeModule());
    }
    
    public Book readBook(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            return om.readValue(reader, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book readBook(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return om.readValue(reader, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBook(Path path, Book book) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            om.writerWithDefaultPrettyPrinter().writeValue(writer, book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
