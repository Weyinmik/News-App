package com.example.koloh.newsapp;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper methods related to requesting and receiving newsfeed data from Guardian API.
 */
public final class NewsQueryUtils {

    private static final String SOURCE = "http://content.guardianapis.com/search?";
    private static final String QUERY_PARAMETER = "q";
    private static final String API_PARAMETER = "api-key";
    private static final String SHOW_TAG_PARAMETER = "show-tags";
    private static final String CONTRIBUTOR_AUTHOR_TAG = "contributor";
    private static final String API_KEY = "640deee2-109e-47f6-80d1-8038dc69cbcd";
    private static final String SORT_PARAMETER = "order-by";
    private static final String ORDER_BY_MOST_RECENT = "newest";
    private static final String PAGE_ENQUIRY_PARAMETER = "page";

    static URL getUrl(String search, int page) {
        if (search != null) {
            Uri uri = Uri.parse ( SOURCE ).buildUpon ()
                    .appendQueryParameter ( QUERY_PARAMETER, search )
                    .appendQueryParameter ( API_PARAMETER, API_KEY )
                    .appendQueryParameter ( SHOW_TAG_PARAMETER, CONTRIBUTOR_AUTHOR_TAG )
                    .appendQueryParameter ( SORT_PARAMETER, ORDER_BY_MOST_RECENT )
                    .appendQueryParameter ( PAGE_ENQUIRY_PARAMETER, String.valueOf ( page ) ).build ();
            return createUrl ( uri );
        } else {
            Uri uri = Uri.parse ( SOURCE ).buildUpon ()
                    .appendQueryParameter ( API_PARAMETER, API_KEY )
                    .appendQueryParameter ( SHOW_TAG_PARAMETER, CONTRIBUTOR_AUTHOR_TAG )
                    .appendQueryParameter ( SORT_PARAMETER, ORDER_BY_MOST_RECENT )
                    .appendQueryParameter ( PAGE_ENQUIRY_PARAMETER, String.valueOf ( page ) ).build ();
            return createUrl ( uri );
        }
    }

    private static URL createUrl(Uri uri) {
        URL url = null;
        try {
            url = new URL ( uri.toString () );
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        }
        return url;
    }
}