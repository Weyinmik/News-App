package com.example.koloh.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements android.app.LoaderManager.LoaderCallbacks<List<NewsFeedActivity>> {

    //Declare and initialise all necessary variables.
    private final String IS_SCREEN_ROTATED = "screenOrientation"; //Screen Orientation
    private final String TAG_MAIN = "MainActivity";
    private final String CURRENT_LIST_ITEM = "currentItem";
    private final String PAGE_SCROLL = "pages";
    private final String DATA_FEED = "DataFeeds";
    private final int ITEM_LOADER_ID = 0;
    private ListView item_listView;
    private TextView emptystate_textView;
    private SwipeRefreshLayout userSwipeRefreshLayout;
    private int pages = 1;
    private ArrayList<NewsFeedActivity> data = new ArrayList<> ();
    private Adapter adapter = null;
    private View footerDisplay;
    private boolean onScreenOrientation = false;
    private LinearLayout loadingDisplayLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            onScreenOrientation = savedInstanceState.getBoolean ( IS_SCREEN_ROTATED );
        }
        getLoaderManager ().initLoader ( ITEM_LOADER_ID, null, this );
        item_listView = findViewById ( R.id.list );
        userSwipeRefreshLayout = findViewById ( R.id.swiperefresh );
        emptystate_textView = findViewById ( R.id.loading_error );
        loadingDisplayLayout = findViewById ( R.id.loading_linear_layout );
        footerDisplay = getLayoutInflater ().inflate ( R.layout.loading_list, null );
        userSwipeRefreshLayout.setOnRefreshListener (
                new SwipeRefreshLayout.OnRefreshListener () {
                    @Override
                    public void onRefresh() {
                        Log.i ( TAG_MAIN, String.valueOf ( R.string.toast_refresh ) );
                        refresh ();
                    }
                }
        );
        item_listView.setOnScrollListener ( new AbsListView.OnScrollListener () {
            public int currentListScrollState;
            public int currentVisibleListItemCount;
            public int currentFirstVisibleListItem;
            int totalListItemCount;

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleListItem = firstVisibleItem;
                this.currentVisibleListItemCount = visibleItemCount;
                this.totalListItemCount = totalItemCount;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentListScrollState = scrollState;
                this.isScrollCompleted ();
            }

            private void isScrollCompleted() {
                if (this.currentVisibleListItemCount + currentFirstVisibleListItem == totalListItemCount && this.currentListScrollState == SCROLL_STATE_IDLE) {
                    if (isConnected ()) {
                        pages++;
                        item_listView.addFooterView ( footerDisplay );
                        item_listView.setSelection ( item_listView.getCount () - 1 );
                        getLoaderManager ().restartLoader ( 0, null, MainActivity.this );
                    }
                }
            }
        } );

        if (isConnected ()) {
            getLoaderManager ().initLoader ( ITEM_LOADER_ID, null, this );
        } else {
            item_listView.setEmptyView ( emptystate_textView );
            emptystate_textView.setText ( R.string.loading_fail_connection );
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt ( PAGE_SCROLL, pages );
        outState.putSerializable ( DATA_FEED, data );
        outState.putInt ( CURRENT_LIST_ITEM, item_listView.getSelectedItemPosition () );
        onScreenOrientation = true;
        outState.putBoolean ( IS_SCREEN_ROTATED, onScreenOrientation );
        super.onSaveInstanceState ( outState );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        pages = savedInstanceState.getInt ( PAGE_SCROLL, 1 );
        data = (ArrayList<NewsFeedActivity>) savedInstanceState.getSerializable ( DATA_FEED );
        adapter = new NewsFeedAdapter ( this, data );
        item_listView.setAdapter ( (ListAdapter) adapter );
        item_listView.setSelection ( savedInstanceState.getInt ( CURRENT_LIST_ITEM, 1 ) );
        loadingDisplayLayout.setVisibility ( View.GONE );

        super.onRestoreInstanceState ( savedInstanceState );
    }


    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService ( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo ();
        }
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting ();

    }

    @Override
    public Loader<List<NewsFeedActivity>> onCreateLoader(int id, Bundle args) {
        return new NewsFeedLoader ( MainActivity.this, null, pages );

    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeedActivity>> loader, final List<NewsFeedActivity> newsList) {
        if (!onScreenOrientation) {
            item_listView.removeFooterView ( footerDisplay );
            loadingDisplayLayout.setVisibility ( View.GONE );
            if (userSwipeRefreshLayout.isRefreshing ()) {
                userSwipeRefreshLayout.setRefreshing ( false );
            }
            if (!newsList.isEmpty ()) {
                if (data.isEmpty ()) {
                    adapter = new NewsFeedAdapter ( MainActivity.this, newsList );
                    item_listView.setAdapter ( (ListAdapter) adapter );
                } else {
                    newsList.addAll ( newsList );
                    adapter.notifyAll ();
                }
                data.addAll ( newsList );

                Log.i ( TAG_MAIN, String.valueOf ( (R.string.loading_finished) + data.size () ) );
                item_listView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent ( Intent.ACTION_VIEW );
                        i.setData ( Uri.parse ( data.get ( position ).getWebUrl () ) );
                        startActivity ( i );
                    }
                } );
            } else {
                item_listView.setEmptyView ( emptystate_textView );
                emptystate_textView.setText ( R.string.data_error );
            }
        } else {
            onScreenOrientation = false;
        }
    }


    private void refresh() {
        if (isConnected ()) {
            data.clear ();
            pages = 1;
            getLoaderManager ().restartLoader ( 0, null, this );
        } else {
            Toast.makeText ( this, R.string.toast_no_internet, Toast.LENGTH_SHORT ).show ();
            if (userSwipeRefreshLayout.isRefreshing ()) {
                userSwipeRefreshLayout.setRefreshing ( false );
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsFeedActivity>> loader) {
        loader.abandon ();
    }
}

//References: @Ahmed3010 has helped greatly with explanations and corrections in order for me to complete this project.
// githublink: https://github.com/ahmed3010; slackname:@Ahmad