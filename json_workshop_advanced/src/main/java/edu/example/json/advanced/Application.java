package edu.example.json.advanced;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import edu.example.json.advanced.gson.BGsonBuilder;
import edu.example.json.advanced.jackson.BJacksonBuilder;
import edu.example.json.advanced.jsonb.BJsonBBuilder;
import edu.example.json.model.Book;

/**
 * Main application
 * that runs every JSON processor to parse and output 
 * (or: read and write, or: deserialize and serialize) a JSON object
 * 
 */
public class Application {

    private static final Path OUTDIR_PATH = Paths.get("c:\\Temp\\jsonlibrarycheck");
    private static final String BOOK_JSON_RESOURCE = "/book__lord_of_the_rings.json";

    public static void main(String[] args) {
        List<BLJsonProcessor> processors = List.of(
            //new BGsonBuilder()
            //new BJsonBBuilder()
            new BJacksonBuilder()
            );

        for (BLJsonProcessor p : processors) {
            Book b = null;
            if(p instanceof BGsonBuilder) {
                b = p.readBook("/book__lord_of_the_rings__gson.json");
            }
            if(p instanceof BJsonBBuilder) {
                b = p.readBook("/book__lord_of_the_rings__jsonb.json");
            }
            if(p instanceof BJacksonBuilder) {
                b = p.readBook("/book__lord_of_the_rings__jackson.json");
            }
            b.setPublishedAt(new Date(69,9,20)); // = 20.10.1969
            b.setBoughtAt(Instant.now());
            b.setFirstReadAt(LocalDateTime.now());
            b.setLastReadAt(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
            p.writeBook(OUTDIR_PATH.resolve(p.getClass().getSimpleName() + ".lotr.json"), b);
        }
    }
}
