package edu.example.json.advanced.gson.adapter;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public LocalDateTime read(JsonReader reader) throws IOException {
        return LocalDateTime.now();
    }

    @Override
    public void write(JsonWriter writer, LocalDateTime time) throws IOException {
        if (time == null) {
            writer.nullValue();
        } else {
            writer.value(time.toString());
        }
    }
}
