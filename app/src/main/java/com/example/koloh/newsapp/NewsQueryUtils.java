package com.example.koloh.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class NewsQueryUtils {

    private static final String NEWS_TAG = NewsQueryUtils.class.getSimpleName ();

    /**
     * Create a private constructor because no one should ever create a {@link NewsQueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name NewsQueryUtils (and an object instance of QueryUtils is not needed).
     */
    private NewsQueryUtils() {
    }


    public static List<NewsFeedActivity> fetchNewsFeedData(String requestUrl) {

        // forces the network to be slow and the loading indicator is displayed for 2 seconds
        try {
            Thread.sleep ( 2000 );
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        // Create URL object
        URL url = createUrl ( requestUrl );


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest ( url );
        } catch (IOException e) {
            Log.e ( NEWS_TAG, "Problem making the HTTP request.", e );
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<NewsFeedActivity> newsfeed = extractFeatureFromJson ( jsonResponse );

        // Return the list of {@link NewsFeedActivity}
        return newsfeed;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL ( stringUrl );
        } catch (MalformedURLException e) {
            Log.e ( NEWS_TAG, "Problem building the URL ", e );
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection ();
            urlConnection.setReadTimeout ( 10000 /* milliseconds */ );
            urlConnection.setConnectTimeout ( 15000 /* milliseconds */ );
            urlConnection.setRequestMethod ( "GET" );
            urlConnection.connect ();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode () == 200) {
                inputStream = urlConnection.getInputStream ();
                jsonResponse = readFromStream ( inputStream );
            } else {
                Log.e ( NEWS_TAG, "Error response code: " + urlConnection.getResponseCode () );
            }
        } catch (IOException e) {
            Log.e ( NEWS_TAG, "Problem retrieving the newsfeed JSON results.", e );
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect ();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close ();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder ();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader ( inputStream, Charset.forName ( "UTF-8" ) );
            BufferedReader reader = new BufferedReader ( inputStreamReader );
            String line = reader.readLine ();
            while (line != null) {
                output.append ( line );
                line = reader.readLine ();
            }
        }
        return output.toString ();
    }


    private static List<NewsFeedActivity> extractFeatureFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty ( jsonResponse )) {
            return null;
        }
        // Created an empty ArrayList to start adding newsfeed to
        List<NewsFeedActivity> newsfeed = new ArrayList<> ();
        try {
            JSONObject jsonObject = new JSONObject ( jsonResponse );
            JSONObject response = jsonObject.getJSONObject ( "response" );
            JSONArray results = response.getJSONArray ( "results" );


            for (int i = 0; i < results.length (); i++) {
                JSONObject currentObject = results.getJSONObject ( i );

                String title = currentObject.getString ( "webTitle" );
                String section = currentObject.getString ( "sectionName" );
                String author = currentObject.getJSONArray ( "tags" ).getJSONObject ( 0 ).getString ( "" );
                String date = currentObject.getString ( "webPublicationDate" ); //
                String url = currentObject.getString ( "webUrl" );

                // Created a new {@link NewsFeedActivity} object with the title,section,author,date,url from JSON response

                newsfeed.add ( new NewsFeedActivity ( title, section, author, date, url ) );


            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e ( "NewsQueryUtils", "Problem parsing the newsfeed JSON results", e );
        }
        return newsfeed;
    }

}
