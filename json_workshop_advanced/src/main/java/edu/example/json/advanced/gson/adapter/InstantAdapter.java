package edu.example.json.advanced.gson.adapter;

import java.io.IOException;
import java.time.Instant;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class InstantAdapter extends TypeAdapter<Instant> { 
   @Override 
   public Instant read(JsonReader reader) throws IOException { 
      return Instant.now();
   } 
   
   @Override 
   public void write(JsonWriter writer, Instant time) throws IOException {
       if(time==null) {
           writer.nullValue();
       }
       else {
           writer.value(time.toString());
       }
   } 
}