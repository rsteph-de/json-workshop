package edu.example.json.advanced;

import java.nio.file.Path;

import edu.example.json.model.Book;

public interface BLJsonProcessor {
    public Book readBook(String resource);

    public Book readBook(Path path);

    public void writeBook(Path path, Book book);
}
