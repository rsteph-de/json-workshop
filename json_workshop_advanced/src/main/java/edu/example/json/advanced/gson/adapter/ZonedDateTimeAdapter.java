package edu.example.json.advanced.gson.adapter;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
    @Override
    public ZonedDateTime read(JsonReader reader) throws IOException {
        return ZonedDateTime.now();
    }

    @Override
    public void write(JsonWriter writer, ZonedDateTime time) throws IOException {
        if (time == null) {
            writer.nullValue();
        } else {
            writer.value(time.toString());
        }
    }
}
