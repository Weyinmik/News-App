package com.example.koloh.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class NewsFeedLoader extends AsyncTaskLoader<List<NewsFeedActivity>> {

    /**
     * Tag for log messages
     */
    private static final String NEWS_TAG = NewsFeedLoader.class.getName ();

    /**
     * Query URL
     */
    private String newsUrl;

    /**
     * Constructs a new {@link NewsFeedLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsFeedLoader(Context context, String url) {
        super ( context );
        newsUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad ();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NewsFeedActivity> loadInBackground() {
        if (newsUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of newsfeed.
        List<NewsFeedActivity> newsfeed = NewsQueryUtils.fetchNewsFeedData ( newsUrl );
        return newsfeed;
    }
}