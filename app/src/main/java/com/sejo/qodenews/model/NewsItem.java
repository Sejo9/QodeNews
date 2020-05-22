package com.sejo.qodenews.model;

/*
    A class definition for an article
*/

public class NewsItem {

    //Variables
    private String title;
    private String author;
    private String description;
    private String imageURL;

    //Constructors
    public NewsItem() {
    }

    public NewsItem(String title, String author, String description, String imageURL) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageURL = imageURL;
    }

    //Getters for private variables
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }
}
