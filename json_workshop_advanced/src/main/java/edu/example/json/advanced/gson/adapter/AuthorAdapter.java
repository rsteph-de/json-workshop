package edu.example.json.advanced.gson.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.example.json.model.Author;

public class AuthorAdapter extends TypeAdapter<Author> {
    @Override
    public Author read(JsonReader reader) throws IOException {
        String name = reader.nextString();
        Author a = new Author();
        a.setFirstname(name.substring(0, name.lastIndexOf(" ")));
        a.setLastname(name.substring(name.lastIndexOf(" ") + 1));
        return a;
    }

    @Override
    public void write(JsonWriter writer, Author a) throws IOException {
        if (a == null) {
            writer.nullValue();
        } else {
            writer.value(String.join(" ", a.getFirstname(), a.getLastname()));
        }
    }
}
