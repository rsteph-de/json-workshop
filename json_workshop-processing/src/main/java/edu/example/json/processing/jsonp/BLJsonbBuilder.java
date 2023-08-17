package edu.example.json.processing.jsonp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

// https://javaee.github.io/jsonb-spec/getting-started.html
// https://www.e4developer.com/2018/03/04/introducing-json-b-with-spring-boot-2-0/

public class BLJsonbBuilder implements BLJsonProcessor {

    private JsonbConfig cfg = new JsonbConfig().withFormatting(true);

    public Booklist readBooklist(String resource) {
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resource))) {
            Jsonb jsonb = JsonbBuilder.create(cfg);
            return jsonb.fromJson(reader, Booklist.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booklist readBooklist(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Jsonb jsonb = JsonbBuilder.create(cfg);
            return jsonb.fromJson(reader, Booklist.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBooklist(Path path, Booklist booklist) {
        Jsonb jsonb = JsonbBuilder.create(cfg);
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            jsonb.toJson(booklist, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
