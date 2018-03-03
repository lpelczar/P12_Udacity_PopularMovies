package com.example.lpelczar.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lpelczar.popularmovies.models.Video;

import java.util.List;


public class TrailerAdapter extends ArrayAdapter<Video> {

    public TrailerAdapter(Context context, List<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trailer_item, parent, false);
        }

        Video currentVideo = getItem(position);

        TextView trailerTextView = listItemView.findViewById(R.id.trailer_name);


        if (currentVideo != null) {
            trailerTextView.setText(currentVideo.getName());
        } else {
            trailerTextView.setText(R.string.trailer);
        }

        return listItemView;
    }
}