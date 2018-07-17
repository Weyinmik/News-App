package com.example.koloh.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeedActivity> {


    public NewsFeedAdapter(MainActivity context, List<NewsFeedActivity> newsfeed) {
                super ( context, 0, newsfeed );

        }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from ( getContext () ).inflate (
                    R.layout.news_list_item, parent, false );
        }


        NewsFeedActivity currentNewsItem = getItem ( position );

        String originalDate = currentNewsItem.getDate ();
        String finalDate;
        String finalTime;

        String[] parts = originalDate.split ( "T" );
        finalDate = parts[0];
        finalTime = parts[1];
        String finalTimeLessZ = finalTime.substring ( 0, finalTime.lastIndexOf ( "Z" ) );

        TextView titleTextView = (TextView) listItemView.findViewById ( R.id.title );
        titleTextView.setText ( currentNewsItem.getTitle () );


        TextView sectionTextView = (TextView) listItemView.findViewById ( R.id.section );
        sectionTextView.setText ( currentNewsItem.getSection () );


        TextView dateTextView = (TextView) listItemView.findViewById ( R.id.date );
        dateTextView.setText ( finalDate );

        TextView timeTextView = (TextView) listItemView.findViewById ( R.id.time_text_view );
        timeTextView.setText ( finalTimeLessZ );

        return listItemView;
    }


}