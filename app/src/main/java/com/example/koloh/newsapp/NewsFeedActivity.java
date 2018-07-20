package com.example.koloh.newsapp;


import java.io.Serializable;

/**
 * {@link NewsFeedActivity} represents more news topics that users want to know and read.
 * It contains a default category of the news and title, as well as dates the news article was published.
 */
public class NewsFeedActivity implements Serializable {

    private String title, section, date, author, webUrl;

    public NewsFeedActivity(String title, String section, String date, String webUrl, String author) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.webUrl = webUrl;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
