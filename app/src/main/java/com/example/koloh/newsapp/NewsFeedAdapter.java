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

    NewsFeedAdapter(@NonNull Context context, @NonNull List<NewsFeedActivity> newsList) {
        super ( context, 0, newsList );
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder item_holder;
        if (convertView == null) {
            convertView = LayoutInflater.from ( getContext () ).inflate ( R.layout.news_list_item, parent, false );
            item_holder = new ViewHolder ( convertView );
            convertView.setTag ( item_holder );
        } else {
            item_holder = (ViewHolder) convertView.getTag ();

        }
        item_holder.title.setText ( getItem ( position ).getTitle () );
        item_holder.section.setText ( getItem ( position ).getSection () );
        item_holder.date.setText ( getItem ( position ).getDate () );
        item_holder.author.setText ( getItem ( position ).getAuthor () );
        return convertView;
    }


    class ViewHolder {
        private TextView title;
        private TextView section;
        private TextView date;
        private TextView author;

        public ViewHolder(View view) {
            this.title = view.findViewById ( R.id.title_textview );
            this.section = view.findViewById ( R.id.section_textview );
            this.date = view.findViewById ( R.id.date_textview );
            this.author = view.findViewById ( R.id.author_textview );

        }
    }
}

