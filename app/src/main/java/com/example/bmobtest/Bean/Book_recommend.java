package com.example.bmobtest.Bean;

/**
 * Created by 戚春阳 on 2018/1/25.
 */

public class Book_recommend {
    private String name;
    private String url;
    private String author;
    private String press;
    private String number;
    private String collection;
    private String lend;
    private String rate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getLend() {
        return lend;
    }

    public void setLend(String lend) {
        this.lend = lend;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Book_recommend{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", number='" + number + '\'' +
                ", collection='" + collection + '\'' +
                ", lend='" + lend + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
