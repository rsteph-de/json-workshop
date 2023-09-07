package edu.example.json.advanced.jsonb.adapter;

import edu.example.json.model.Author;
import jakarta.json.Json;
import jakarta.json.JsonString;
import jakarta.json.bind.adapter.JsonbAdapter;

public class AuthorAdapter implements JsonbAdapter<Author, JsonString> {
    @Override
    public JsonString adaptToJson(Author a) throws Exception {
        return Json.createValue(String.join(" ", a.getFirstname(), a.getLastname()));
    }

    @Override
    public Author adaptFromJson(JsonString json) throws Exception {
        String name = json.getString();
        Author a = new Author();
        a.setFirstname(name.substring(0, name.lastIndexOf(" ")));
        a.setLastname(name.substring(name.lastIndexOf(" ") + 1));
        return a;
    }
}
