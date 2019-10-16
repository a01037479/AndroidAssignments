package com.example.harrypotter;

public class Book implements java.io.Serializable{

    private String id;
    private String title;
    private String thumbNailUrl;
    private String[] authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String ISBN;

    public Book(){}

    public Book(String id, String title, String[] authorList, String publisher, String publishDate, String description, String ISBN, String url){
        this.id = id;
        this.title = title;
        this.authors = authorList;
        this.publisher = publisher;
        this.publishedDate = publishDate;
        this.description = description;
        this.ISBN = ISBN;
        this.thumbNailUrl = url;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }



}



