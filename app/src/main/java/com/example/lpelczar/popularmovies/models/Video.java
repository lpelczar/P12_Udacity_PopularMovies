package com.example.lpelczar.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lpelczar on 02.03.18.
 */

public class Video implements Parcelable {

    private String key;
    private String name;
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String getYoutubeLink() {
        return "https://www.youtube.com/watch?v=" + key;
    }

    public static class VideoResult {
        private List<Video> results;

        public List<Video> getResults() {
            return results;
        }
    }

    static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    private Video(Parcel in){
        this.key = in.readString();
        this.name = in.readString();
        this.type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.type);
    }

    @Override
    public String toString() {
        return "Name: " + name + " Type: " + type + " Key: " + key;
    }
}
