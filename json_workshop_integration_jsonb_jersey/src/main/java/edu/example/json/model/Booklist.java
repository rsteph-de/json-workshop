package edu.example.json.model;

import java.util.List;

public class Booklist {
    private String category;
    private String calendarWeek;
    private List<Book> items;

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCalendarWeek() {
        return calendarWeek;
    }
    public void setCalendarWeek(String calendarWeek) {
        this.calendarWeek = calendarWeek;
    }
    public List<Book> getItems() {
        return items;
    }
    public void setItems(List<Book> items) {
        this.items = items;
    }

}
