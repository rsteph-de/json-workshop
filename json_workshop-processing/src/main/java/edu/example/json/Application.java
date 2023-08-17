package edu.example.json;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import edu.example.json.model.Booklist;
import edu.example.json.processing.BLJsonProcessor;
import edu.example.json.processing.gson.BLGsonBuilder;
import edu.example.json.processing.gson.BLGsonGenerator;
import edu.example.json.processing.gson.BLGsonSerializer;
import edu.example.json.processing.jackson.BLJacksonBuilder;
import edu.example.json.processing.jackson.BLJacksonGenerator;
import edu.example.json.processing.jackson.BLJacksonSerializer;
import edu.example.json.processing.jsonp.BLJsonbBuilder;
import edu.example.json.processing.jsonp.BLJsonpGenerator;
import edu.example.json.processing.jsonp.BLJsonpSerializer;

/**
 * Main application
 * that runs every JSON processor to parse and output 
 * (or: read and write, or: deserialize and serialize) a JSON object
 * 
 */
public class Application {

    private static final Path OUTDIR_PATH = Paths.get("c:\\Temp\\jsonlibrarycheck");
    private static final String BOOKLIST_JSON_RESOURCE = "/spiegel_bestseller_paperback-sachbuch_2023-W33.json";

    public static void main(String[] args) {
        List<BLJsonProcessor> processors = List.of(
            new BLGsonBuilder(), new BLGsonGenerator(), new BLGsonSerializer(),
            new BLJacksonBuilder(), new BLJacksonGenerator(), new BLJacksonSerializer(),
            new BLJsonbBuilder(), new BLJsonpGenerator(), new BLJsonpSerializer());

        for (BLJsonProcessor p : processors) {
            Booklist bl = p.readBooklist(BOOKLIST_JSON_RESOURCE);
            p.writeBooklist(OUTDIR_PATH.resolve(p.getClass().getSimpleName() + ".json"), bl);
        }
    }
}
