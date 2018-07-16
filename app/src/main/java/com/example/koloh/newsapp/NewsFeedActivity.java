package com.example.koloh.newsapp;


/**
 * {@link NewsFeedActivity} represents more news topics that users want to know and read.
 * It contains a default category of the news and title, as well as dates the news article was published.
 */
public class NewsFeedActivity {

    // Category of a particular news article

    private String newsDefaultCategory;


    // Title of a particular news article

    private String newsDefaultTitle;


    // Date when a particular news article is published

    private String newsDefaultDatePublished;


    /**
     * Create a new JobNews object.
     *
     * @param newsCategory  the category of the news article
     * @param newsTitle     the title of the news article
     * @param datePublished the date the news article was published
     */
    public NewsFeedActivity(String newsCategory, String newsTitle, String datePublished) {
        newsDefaultCategory = newsCategory;
        newsDefaultTitle = newsTitle;
        newsDefaultDatePublished = datePublished;

    }


    // Returns the default category of the news article.

    public String getNewsCategory() {
        return newsDefaultCategory;
    }


    // Returns  the title of the news article.

    public String getNewsTitle() {
        return newsDefaultTitle;
    }


    // Returns  the date the news article was published.

    public String getDatePublished() {
        return newsDefaultDatePublished;
    }


}

