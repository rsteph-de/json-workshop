package edu.example.json.integration.jsonb_spring.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.example.json.model.Book;
import jakarta.json.bind.Jsonb;
import jakarta.servlet.http.HttpServletResponse;

//https://andbin.dev/java/spring-boot/json-binding-libraries

@RestController
public class JsonController {
    @Autowired
    Jsonb jsonb;
    
    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of("message", "Hello from Spring Boot JSON-B Controller!");
    }

    @PostMapping("/process-mapping")
    public Book updateBook(@RequestBody Book book, HttpServletResponse response) {
        return book;
    }
    
    @PostMapping("/process-string")
    public Book updateBook(@RequestBody String book, HttpServletResponse response) {
        Book b = jsonb.fromJson(book, Book.class);
        return b;
    }
}
