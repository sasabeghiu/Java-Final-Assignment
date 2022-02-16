package dao;

import model.Movie;
import model.Showing;
import model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private final List<User> userList = new ArrayList<>();
    private final List<Movie> movieList = new ArrayList<>();
    private final List<Showing> showingsRoom1 = new ArrayList<>();
    private final List<Showing> showingsRoom2 = new ArrayList<>();

    public Database() {
        if (getUserList().isEmpty()) {
            getUserList().add(new User("John", "admin12!", User.UserType.Admin));
            getUserList().add(new User("Jack", "user123!", User.UserType.User));
        }
        if (getMovieList().isEmpty()) {
            Movie movie1 = new Movie("No time to lie", 12.00, 125);
            Movie movie2 = new Movie("The Addams Family 19", 9.00, 92);
            movieList.add(movie1);
            movieList.add(movie2);
        }
        if (getShowingsRoom1().isEmpty()) {
            getShowingsRoom1().add(new Showing(LocalDateTime.of(2021, 10, 9, 20, 0), LocalDateTime.of(2021, 10, 9, 22, 5), movieList.get(0), 200));
            getShowingsRoom1().add(new Showing(LocalDateTime.of(2021, 10, 9, 22, 30), LocalDateTime.of(2021, 10, 10, 0, 2), movieList.get(1), 200));
        }
        if (getShowingsRoom2().isEmpty()) {
            getShowingsRoom2().add(new Showing(LocalDateTime.of(2021, 10, 9, 20, 0), LocalDateTime.of(2021, 10, 9, 21, 32), movieList.get(1), 100));
            getShowingsRoom2().add(new Showing(LocalDateTime.of(2021, 10, 9, 22, 0), LocalDateTime.of(2021, 10, 10, 0, 5), movieList.get(0), 100));
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public List<Showing> getShowingsRoom1() {
        return showingsRoom1;
    }

    public List<Showing> getShowingsRoom2() {
        return showingsRoom2;
    }

    public Movie findMovie(String movieTitle) throws Exception {
        for (Movie m : movieList) {
            if (m.getMovieTitle().equals(movieTitle))
                return m;
        }
        throw new Exception("Error");
    }

    public void addShowing(Showing showing, String room) {
        if (room.equals("Room 1")) {
            showingsRoom1.add(showing);
        } else {
            showingsRoom2.add(showing);
        }
    }

    public void addMovie(Movie movie) {
        if (!movieList.contains(movie))
            movieList.add(movie);
    }
}