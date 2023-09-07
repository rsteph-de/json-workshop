package edu.example.json.advanced.jsonb.adapter;

import edu.example.json.model.Price2;
import jakarta.json.Json;
import jakarta.json.JsonString;
import jakarta.json.bind.adapter.JsonbAdapter;

public class Price2Adapter implements JsonbAdapter<Price2, JsonString> {

    @Override
    public JsonString adaptToJson(Price2 a) throws Exception {
        return Json.createValue(String.join(" ", a.currency(), Double.toString(a.value())));
    }

    @Override
    public Price2 adaptFromJson(JsonString json) throws Exception {
        String s = json.getString();
        Price2 p = new Price2(s.substring(0, s.indexOf(" ")),
            Double.parseDouble(s.substring(s.indexOf(" ") + 1)));
        return p;
    }
}
