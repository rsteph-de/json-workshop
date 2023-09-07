package edu.example.json.advanced.jackson.adapter;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.example.json.model.Author;

public class AuthorDeserializer extends StdDeserializer<Author> {
    private static final long serialVersionUID = -7022640932437016468L;

    public AuthorDeserializer() {
        this(null);
    }

    protected AuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Author deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String name = p.getValueAsString();
        Author a = new Author();
        a.setFirstname(name.substring(0, name.lastIndexOf(" ")));
        a.setLastname(name.substring(name.lastIndexOf(" ") + 1));
        return a;
    }
}
