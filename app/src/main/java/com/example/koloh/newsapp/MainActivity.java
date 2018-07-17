package com.example.koloh.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsFeedActivity>> {


    /**
     * URL for newsfeed data from the Guardian Api
     */
    public static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?show-elements=all&order-by=relevance&show-tags=author&q=%22technology%20news%22&api-key=640deee2-109e-47f6-80d1-8038dc69cbcd";


    private static final int NEWSFEED_LOADER_ID = 1;

    /**
     * Adapter for the list of newsfeed
     */
    private NewsFeedAdapter adapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView emptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsfeedListView = (ListView) findViewById ( R.id.list );

        // Create a new adapter that takes an empty list of newsfeed as input
        adapter = new NewsFeedAdapter ( this, new ArrayList<NewsFeedActivity>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsfeedListView.setAdapter ( adapter );

        emptyStateTextView = (TextView) findViewById ( R.id.no_article_text_view );
        newsfeedListView.setEmptyView ( emptyStateTextView );

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected newsfeed.
        newsfeedListView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current newsfeed that was clicked on
                NewsFeedActivity currentNewsfeed = adapter.getItem ( position );

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsfeedUri = Uri.parse ( currentNewsfeed.getUrl () );

                // Create a new intent to view the newsfeed URI
                Intent websiteIntent = new Intent ( Intent.ACTION_VIEW, newsfeedUri );

                // Send the intent to launch a new activity
                startActivity ( websiteIntent );
            }
        } );

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService ( Context.CONNECTIVITY_SERVICE );

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo ();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected ()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager ();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader ( NEWSFEED_LOADER_ID, null, this );
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById ( R.id.indicator );
            loadingIndicator.setVisibility ( View.GONE );

            // Update empty state with no connection error message
            emptyStateTextView.setText ( R.string.empty_text );
        }
    }


    @Override
    public Loader<List<NewsFeedActivity>> onCreateLoader(int i, Bundle bundle) {


        // Create a new loader for the given URL
        return new NewsFeedLoader ( this, GUARDIAN_REQUEST_URL );
    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeedActivity>> loader, List<NewsFeedActivity> newsList) {
        // Hide loading indicator because the data has been loaded
        View progressBar = findViewById ( R.id.indicator );
        progressBar.setVisibility ( View.GONE );

        // Set empty state text to display "No earthquakes found."
        emptyStateTextView.setText ( R.string.no_news_found );
        // Clear the adapter of previous earthquake data

        adapter.clear ();
        // check if there are articles then show them
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsList != null && !newsList.isEmpty ()) {
            adapter.addAll ( newsList );
            emptyStateTextView.setVisibility ( View.GONE );
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsFeedActivity>> loader) {
        // clear adapter
        adapter.clear();
    }

}