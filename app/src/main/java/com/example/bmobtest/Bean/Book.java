package com.example.bmobtest.Bean;

/**
 * Created by 戚春阳 on 2018/1/23.
 */

public class Book {
    private String name;
    private String author;
    private String press;
    private String language;
    private String museum_collection;
    private String available;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMuseum_collection() {
        return museum_collection;
    }

    public void setMuseum_collection(String museum_collection) {
        this.museum_collection = museum_collection;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", language='" + language + '\'' +
                ", museum_collection='" + museum_collection + '\'' +
                ", available='" + available + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
