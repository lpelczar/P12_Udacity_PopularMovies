package com.example.lpelczar.popularmovies.models;

import java.util.List;

/**
 * Created by lpelczar on 02.03.18.
 */

public class Video {

    public static final String YOUTUBE_PATH = "https://www.youtube.com/watch?v=";

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
        return YOUTUBE_PATH + key;
    }

    public static class VideoResult {
        private List<Video> results;

        public List<Video> getResults() {
            return results;
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + " Type: " + type + " Key: " + key;
    }
}
