package edu.example.json.advanced.gson.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.example.json.model.Price2;

public class Price2Adapter extends TypeAdapter<Price2> {
    @Override
    public Price2 read(JsonReader reader) throws IOException {
        String name = reader.nextString();
        Price2 a = new Price2(name.substring(0, name.indexOf(" ")),
            Double.parseDouble(name.substring(name.indexOf(" ") + 1)));
        return a;
    }

    @Override
    public void write(JsonWriter writer, Price2 a) throws IOException {
        if (a == null) {
            writer.nullValue();
        } else {
            writer.value(String.join(" ", a.currency(), Double.toString(a.value())));
        }
    }
}
