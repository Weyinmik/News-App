package com.example.koloh.newsapp;


/**
 * {@link NewsFeedActivity} represents more news topics that users want to know and read.
 * It contains a default category of the news and title, as well as dates the news article was published.
 */
public class NewsFeedActivity {


    private String newsTitle;
    private String newsSection;
    private String newsAuthor;
    private String newsDate;
    private String newsUrl;

    public NewsFeedActivity(String title, String section, String author, String date, String url) {
        newsTitle = title;
        newsSection = section;
        newsAuthor = author;
        newsDate = date;
        newsUrl = url;
    }

    public String getTitle() {
        return newsTitle;
    }

    public String getSection() {
        return newsSection;
    }

    public String getAuthor() {
        return newsAuthor;
    }

    public String getDate() {
        return newsDate;
    }

    public String getUrl() {
        return newsUrl;
    }






}


