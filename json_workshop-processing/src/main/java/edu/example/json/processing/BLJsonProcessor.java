package edu.example.json.processing;

import java.nio.file.Path;

import edu.example.json.model.Booklist;

public interface BLJsonProcessor {
    public Booklist readBooklist(String resource);

    public Booklist readBooklist(Path path);

    public void writeBooklist(Path path, Booklist booklist);
}
