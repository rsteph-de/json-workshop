package edu.example.json.model;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class Book {

 

    private String title;
    private int copiesSold;
    private boolean availableInStore;
    private List<Author> authors;
    private Price1 price1;
    private Price2 price2;
    private Date publishedAt;
    private Instant boughtAt;
    private LocalDateTime firstReadAt;
    private ZonedDateTime lastReadAt;

    private String publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public void setCopiesSold(int copiesSold) {
        this.copiesSold = copiesSold;
    }

    public boolean isAvailableInStore() {
        return availableInStore;
    }

    public void setAvailableInStore(boolean availableInStore) {
        this.availableInStore = availableInStore;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Price1 getPrice1() {
        return price1;
    }

    public void setPrice1(Price1 price1) {
        this.price1 = price1;
    }
    
    public Price2 getPrice2() {
        return price2;
    }

    public void setPrice2(Price2 price2) {
        this.price2 = price2;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(Instant boughtAt) {
        this.boughtAt = boughtAt;
    }

    public LocalDateTime getFirstReadAt() {
        return firstReadAt;
    }

    public void setFirstReadAt(LocalDateTime firstReadAt) {
        this.firstReadAt = firstReadAt;
    }

    public ZonedDateTime getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(ZonedDateTime lastReadAt) {
        this.lastReadAt = lastReadAt;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}
