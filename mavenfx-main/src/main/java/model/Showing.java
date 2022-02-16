package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Showing implements Serializable {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Movie movie;
    private Integer seatsNumber;

    public Showing(LocalDateTime startTime, LocalDateTime endTime, Movie movie, Integer seatsNumber) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.movie = movie;
        this.seatsNumber = seatsNumber;
    }

    public String getStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public String getEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getMovieTitle() {
        return movie.getMovieTitle();
    }

    public String getMoviePrice() {
        return String.format("%.2f", movie.getMoviePrice());
    }
}