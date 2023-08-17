package edu.example.json.integration.jsonb_jersey.controller;

import edu.example.json.model.Book;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyOrderStrategy;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// Plain old Java Object it does not extend as class or implements an interface
// The class registers its methods for the HTTP GET request using the @GET annotation.
@Path("/")
public class JsonbJerseyController {

    private Jsonb jsonb;

    public JsonbJerseyController() {
        JsonbConfig cfg = new JsonbConfig();
        //reverse order just for testing
        cfg.setProperty(JsonbConfig.PROPERTY_ORDER_STRATEGY, PropertyOrderStrategy.REVERSE);
        jsonb = JsonbBuilder.create(cfg);
    }

    // This method is called if HTML is request
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
            + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }

    @POST
    @Path("/process-mapping")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBook(Book book) {
        return book;
    }

    @POST
    @Path("/process-string")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBook(String book) {
        Book b = jsonb.fromJson(book, Book.class);
        return b;
    }

}
