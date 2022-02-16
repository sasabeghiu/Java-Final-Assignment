package model;

import java.io.Serializable;

public class Movie implements Serializable {
    private final String movieTitle;
    private final Double moviePrice;
    private final Integer movieDuration;

    public Movie(String movieTitle, Double moviePrice, Integer movieDuration) {
        this.movieTitle = movieTitle;
        this.moviePrice = moviePrice;
        this.movieDuration = movieDuration;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Double getMoviePrice() {
        return moviePrice;
    }

    public Integer getMovieDuration() {
        return movieDuration;
    }
}