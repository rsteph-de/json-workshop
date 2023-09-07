package edu.example.json.advanced.jackson.adapter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.example.json.model.Price2;

public class Price2Serializer extends StdSerializer<Price2> {
    private static final long serialVersionUID = -7022640932437016468L;

    public Price2Serializer() {
        this(null);
    }

    protected Price2Serializer(Class<Price2> vc) {
        super(vc);
    }

    @Override
    public void serialize(Price2 p, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(String.join(" ", p.currency(), Double.toString(p.value())));

    }
}
