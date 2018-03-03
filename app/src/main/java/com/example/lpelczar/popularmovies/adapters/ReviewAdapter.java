package com.example.lpelczar.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lpelczar.popularmovies.R;
import com.example.lpelczar.popularmovies.models.Review;

import java.util.List;


public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review_item, parent, false);
        }

        Review currentReview = getItem(position);

        TextView authorTextView = listItemView.findViewById(R.id.review_author);

        if (currentReview != null) {
            authorTextView.setText(currentReview.getAuthor());
        }

        TextView contentTextView = listItemView.findViewById(R.id.review_content);

        if (currentReview != null) {
            contentTextView.setText(currentReview.getContent());
        }

        return listItemView;
    }
}
