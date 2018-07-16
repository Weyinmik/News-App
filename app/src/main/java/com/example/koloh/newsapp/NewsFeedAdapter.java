package com.example.koloh.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeedActivity> {

    public NewsFeedAdapter(Context context, List<NewsFeedActivity> news) {

        super ( context, 0, news );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from ( getContext () ).inflate (
                    R.layout.news_list_item, parent, false );
        }

        // Get the {@link JobNews} object located at this position in the list
        NewsFeedActivity currentJobNews = getItem ( position );


        // Find the TextView in the list_item.xml layout with the ID news_category
        TextView newsCategoryTextView = (TextView) listItemView.findViewById ( R.id.news_category );
        // Get the version name from the current NewsFeedActivity object and
        // set this text on the news_category TextView
        newsCategoryTextView.setText ( currentJobNews.getNewsCategory () );

        // Find the TextView in the list_item.xml layout with the ID news_title
        TextView newsTitleTextView = (TextView) listItemView.findViewById ( R.id.news_title );
        // Get the version name from the current NewsFeedActivity object and
        // set this text on the news_title TextView
        newsTitleTextView.setText ( currentJobNews.getNewsTitle () );


        // Find the TextView in the list_item.xml layout with the ID date_published
        TextView datepublishedTextView = (TextView) listItemView.findViewById ( R.id.date_published );
        // Get the version number from the current date of news object and
        // set this text on the date_published TextView
        datepublishedTextView.setText ( currentJobNews.getDatePublished () );


        // Return the whole list item layout (containing 3 TextViews )
        // so that it can be shown in the ListView
        return listItemView;
    }
}
