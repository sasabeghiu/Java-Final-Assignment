package nl.inholland.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Movie;
import model.Showing;

public class CreateTables {

    public CreateTables() {
    }

    public void fillTableShowings(ObservableList<Showing> showings, TableView<Showing> showingTableView) {
        TableColumn<Showing, String> startColumn1 = new TableColumn<>("Start");
        startColumn1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Showing, String> endColumn1 = new TableColumn<>("End");
        endColumn1.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<Showing, String> titleColumn1 = new TableColumn<>("Title");
        titleColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieTitle()));
        TableColumn<Showing, String> seatsColumn1 = new TableColumn<>("Seats");
        seatsColumn1.setCellValueFactory(new PropertyValueFactory<>("seatsNumber"));
        seatsColumn1.setResizable(false);
        TableColumn<Showing, String> priceColumn1 = new TableColumn<>("Price");
        priceColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMoviePrice()));

        showingTableView.getColumns().addAll(startColumn1, endColumn1, titleColumn1, seatsColumn1, priceColumn1);
        showingTableView.setItems(showings);
    }

    public void fillTableMovies(ObservableList<Movie> movies, TableView<Movie> showingTableView) {
        TableColumn<Movie, String> titleColumn1 = new TableColumn<>("Title");
        titleColumn1.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        TableColumn<Movie, String> priceColumn1 = new TableColumn<>("Price");
        priceColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMoviePrice().toString()));
        TableColumn<Movie, String> durationColumn1 = new TableColumn<>("Duration");
        durationColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieDuration().toString() + " minutes"));
        durationColumn1.setPrefWidth(120);

        showingTableView.getColumns().addAll(titleColumn1, priceColumn1, durationColumn1);
        showingTableView.setItems(movies);
    }
}