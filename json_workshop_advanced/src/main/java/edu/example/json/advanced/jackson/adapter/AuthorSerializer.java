package edu.example.json.advanced.jackson.adapter;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.example.json.model.Author;

public class AuthorSerializer extends StdSerializer<Author> {
    private static final long serialVersionUID = -7022640932437016468L;

    public AuthorSerializer() {
        this(null);
    }

    protected AuthorSerializer(Class<Author> vc) {
        super(vc);
    }

    @Override
    public void serialize(Author a, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(String.join(" ", a.getFirstname(), a.getLastname()));

    }
}
