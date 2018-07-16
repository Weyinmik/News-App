package com.example.koloh.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        // Create a fake list of news feed category.
        ArrayList<NewsFeedActivity> news = new ArrayList<NewsFeedActivity> ();
        news.add ( new NewsFeedActivity ( "Technology", "How can I invest in Bitcoin", "July 1, 2018" ) );


        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById ( R.id.list );

        // Create a new {@link ArrayAdapter} of news.
        NewsFeedAdapter adapter = new NewsFeedAdapter ( this, news );

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter ( adapter );
    }
}
