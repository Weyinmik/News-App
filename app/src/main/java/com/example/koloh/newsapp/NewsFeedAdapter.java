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

        private static final String NEW_LINE = "\n";
        private static final int ZERO = 0;
        private static final int MAX_LENGTH_LINE_1 = 11;
        private static final String T = "T";
        private static final String Z = "Z";
        private static final String EMPTY_TEXT = "";
        private final Context context;


        public NewsFeedAdapter(@NonNull Context context, List<NewsFeedActivity> newsfeed) {
                super ( context, 0, newsfeed );
                this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View rootView = convertView;
                ViewHolderItem viewHolderItem;
                if (rootView == null) {
                        rootView = LayoutInflater.from ( context ).inflate ( R.layout.news_list_item, parent, false );
                        viewHolderItem = new ViewHolderItem ();
                        viewHolderItem.titleTextView = rootView.findViewById ( R.id.title );
                        viewHolderItem.sectionTextView = rootView.findViewById ( R.id.section );
                        viewHolderItem.authorTextView = rootView.findViewById ( R.id.author );
                        viewHolderItem.dateTextView = rootView.findViewById ( R.id.date );

                        rootView.setTag ( viewHolderItem );
                } else {
                        viewHolderItem = (ViewHolderItem) rootView.getTag ();
                }
                NewsFeedActivity currentArticle = getItem ( position );
                viewHolderItem.titleTextView.setText ( currentArticle.getTitle () );
                viewHolderItem.sectionTextView.setText ( currentArticle.getSection () );
                viewHolderItem.authorTextView.setText ( currentArticle.getAuthor () );
                viewHolderItem.dateTextView.setText ( makeDateInTwoLines ( currentArticle.getDate () ) );
                return rootView;
        }

        private String makeDateInTwoLines(String inputDate) {
                String date = inputDate.substring ( ZERO, MAX_LENGTH_LINE_1 ).replace ( T, EMPTY_TEXT ); // get first 11 chars to it (Date like 2018-3-13T)
                String time = inputDate.substring ( MAX_LENGTH_LINE_1 ).replace ( Z, EMPTY_TEXT ); // get all remained chars
                return date + NEW_LINE + time;
        }


        private static class ViewHolderItem {
                TextView titleTextView;
                TextView sectionTextView;
                TextView authorTextView;
                TextView dateTextView;
        }
}