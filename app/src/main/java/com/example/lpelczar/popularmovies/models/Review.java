package com.example.lpelczar.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lpelczar on 03.03.18.
 */

public class Review implements Parcelable, Serializable {

    private String author;
    private String content;
    private String url;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ReviewResult {
        private List<Review> results;

        public List<Review> getResults() {
            return results;
        }
    }

    static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    private Review(Parcel in){
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }


}
