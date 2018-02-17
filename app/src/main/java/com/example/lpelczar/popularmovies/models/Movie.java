package com.example.lpelczar.popularmovies.models;

import java.time.LocalDate;

/**
 * Created by lpelczar on 17.02.18.
 */

public class Movie {

    private String title;
    private LocalDate releaseDate;
    private String poster;
    private double averageVote;
    private String plot;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return "http://t2.gstatic.com/images?q=tbn:ANd9GcQW3LbpT94mtUG1PZIIzJNxmFX399wr_NcvoppJ82k7z99Hx6in";
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(double averageVote) {
        this.averageVote = averageVote;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
