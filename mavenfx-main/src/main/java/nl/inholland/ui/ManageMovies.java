package nl.inholland.ui;

import dao.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import model.Movie;
import model.User;

import java.util.Optional;

public class ManageMovies {
    private final Stage stage;
    private final Database database;
    TableView<Movie> movieTableView;

    public ManageMovies(User user, Database database) {
        this.stage = new Stage();
        this.database = database;
        movieTableView = new TableView<>();

        fillWindowWithData(user);
    }

    private void fillWindowWithData(User user) {
        //stage settings
        stage.setHeight(600);
        stage.setWidth(1350);
        stage.setTitle("Fantastic Cinema -- manage movies -- username: " + user.getUsername());

        //create the menu
        NavigationMenu menuBar = new NavigationMenu();

        //title
        Label pageHeader = new Label("Manage Movies");
        pageHeader.setPadding(new Insets(20));
        pageHeader.setFont(new Font("Arial", 22));

        //create the tables
        CreateTables table = new CreateTables();
        ObservableList<Movie> movies = FXCollections.observableArrayList(database.getMovieList());
        table.fillTableMovies(movies, movieTableView);

        //error box
        Label error = new Label("");
        error.setVisible(false);

        //layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        HBox menu = new HBox();
        VBox leftPanel = new VBox();
        leftPanel.setPadding(new Insets(10));
        HBox vboxPanels = new HBox();
        vboxPanels.setPadding(new Insets(10));
        HBox moviePanel = new HBox();
        moviePanel.setPadding(new Insets(10));
        HBox errorPanel = new HBox();
        errorPanel.setPadding(new Insets(10));

        //make panels wider
        leftPanel.prefWidthProperty().bind(stage.widthProperty().multiply(0.60));

        //add everything to vBoxes and hBoxes
        menu.getChildren().add(menuBar.getMenuBar(stage, user, database));
        leftPanel.getChildren().add(movieTableView);
        vboxPanels.getChildren().add(leftPanel);
        errorPanel.getChildren().add(error);

        //creating the elements and the HBox form for managing shows(admin)
        Label movieTitleLbl = new Label("Movie Title");
        Label moviePriceLbl = new Label("Movie Price");
        Label movieDurationLbl = new Label("Movie Duration");
        TextField movieTitleTxt = new TextField();
        TextField moviePriceTxt = new TextField();
        TextField movieDurationTxt = new TextField();
        Button addMovieBtn = new Button("Add movie");

        //creating VBoxes to arrange the panel in columns
        VBox columnLabels = new VBox();
        columnLabels.setSpacing(15);
        columnLabels.setPadding(new Insets(10));
        columnLabels.getChildren().addAll(movieTitleLbl, moviePriceLbl, movieDurationLbl);
        VBox columnTextBoxes = new VBox();
        columnTextBoxes.setSpacing(3);
        columnTextBoxes.setPadding(new Insets(10));
        columnTextBoxes.getChildren().addAll(movieTitleTxt, moviePriceTxt, movieDurationTxt);
        VBox columnButtons = new VBox();
        columnButtons.setSpacing(10);
        columnButtons.setPadding(new Insets(10));
        columnButtons.getChildren().add(addMovieBtn);

        //add the VBox columns to the panel
        moviePanel.setSpacing(10);
        moviePanel.getChildren().addAll(columnLabels, columnTextBoxes, columnButtons);

        //add everything to layout
        layout.add(menu, 0, 0);
        layout.add(pageHeader, 0, 1);
        layout.add(vboxPanels, 0, 2);
        layout.add(moviePanel, 0, 3);
        layout.add(errorPanel, 0, 4);

        //styles
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.DARK);
        layout.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        stage.show();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //add new movie
        addMovieBtn.setOnAction(actionEvent -> {
            try {
                String title = movieTitleTxt.getText();
                Double price = Double.parseDouble(moviePriceTxt.getText());
                Integer duration = Integer.parseInt(movieDurationTxt.getText());
                Movie movie = new Movie(title, price, duration);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you want to add this movie?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() != ButtonType.OK) {
                    error.setText("You canceled the process");
                    error.setVisible(true);
                    return;
                }
                database.addMovie(movie);
                movieTableView.getItems().add(movie);
                movieTitleTxt.clear();
                moviePriceTxt.clear();
                movieDurationTxt.clear();
            } catch (Exception e) {
                error.setText("Please use String for Title, Double for Price and Integer for Duration.");
                error.setVisible(true);
            }
        });

        //stop the program when closing the app (press right top corner X)
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }
}