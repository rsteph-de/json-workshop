package edu.example.json.advanced.jackson.adapter;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.example.json.model.Price2;

public class Price2Deserializer extends StdDeserializer<Price2> {
    private static final long serialVersionUID = -7022640932437016468L;

    public Price2Deserializer() {
        this(null);
    }

    protected Price2Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Price2 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String name = p.getValueAsString();
        Price2 a = new Price2(name.substring(0, name.indexOf(" ")),
            Double.parseDouble(name.substring(name.indexOf(" ") + 1)));
        return a;
    }
}
