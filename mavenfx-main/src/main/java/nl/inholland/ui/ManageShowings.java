package nl.inholland.ui;

import dao.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import model.Movie;
import model.Showing;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ManageShowings {
    private final Stage stage;
    private final Database database;
    TableView<Showing> room1TableView;
    TableView<Showing> room2TableView;

    public ManageShowings(User user, Database database) {
        this.stage = new Stage();
        this.database = database;
        room1TableView = new TableView<>();
        room2TableView = new TableView<>();

        fillWindowWithData(user);
    }

    private void fillWindowWithData(User user) {
        //stage settings
        stage.setHeight(600);
        stage.setWidth(1350);
        stage.setTitle("Fantastic Cinema -- manage showings -- username: " + user.getUsername());

        //create the menu
        NavigationMenu menuBar = new NavigationMenu();

        //title
        Label pageHeader = new Label("Manage Showings");
        pageHeader.setPadding(new Insets(20));
        pageHeader.setFont(new Font("Arial", 22));

        //create the tables
        CreateTables table = new CreateTables();
        ObservableList<Showing> room1 = FXCollections.observableArrayList(database.getShowingsRoom1());
        ObservableList<Showing> room2 = FXCollections.observableArrayList(database.getShowingsRoom2());
        table.fillTableShowings(room1, room1TableView);
        table.fillTableShowings(room2, room2TableView);

        //error box
        Label error = new Label("");
        error.setVisible(false);

        //layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        HBox menu = new HBox();
        VBox leftPanel = new VBox();
        leftPanel.setPadding(new Insets(10));
        VBox rightPanel = new VBox();
        rightPanel.setPadding(new Insets(10));
        HBox vboxPanels = new HBox();
        vboxPanels.setPadding(new Insets(10));
        HBox showingsPanel = new HBox();
        showingsPanel.setPadding(new Insets(10));
        HBox errorPanel = new HBox();
        errorPanel.setPadding(new Insets(10));

        //make panels wider
        leftPanel.prefWidthProperty().bind(stage.widthProperty().multiply(0.60));
        rightPanel.prefWidthProperty().bind(stage.widthProperty().multiply(0.60));

        //add everything to vBoxes and hBoxes
        menu.getChildren().add(menuBar.getMenuBar(stage, user, database));
        leftPanel.getChildren().add(new Label("Room 1"));
        leftPanel.getChildren().add(room1TableView);
        rightPanel.getChildren().add(new Label("Room 2"));
        rightPanel.getChildren().add(room2TableView);
        vboxPanels.getChildren().addAll(leftPanel, rightPanel);
        errorPanel.getChildren().add(error);

        //creating the elements and the HBox form for managing shows(admin)
        Label showingTitleLbl = new Label("Movie Title");
        Label showingRoomLbl = new Label("Room");
        Label showingSeatsLbl = new Label("No. of seats");
        ComboBox<String> selectShowingTitle = new ComboBox();
        List<Movie> movies = database.getMovieList();
        for (Movie m : movies) {
            selectShowingTitle.getItems().add(m.getMovieTitle());
        }
        ComboBox selectShowingRoom = new ComboBox();
        selectShowingRoom.getItems().addAll("Room 1", "Room 2");
        Label selectShowingSeats = new Label("");
        Label startShowings = new Label("Start");
        Label endShowings = new Label("End");
        Label priceShowings = new Label("Price");
        DatePicker datePicker = new DatePicker();
        TextField timePicker = new TextField();
        Label selectEndShowings = new Label("");
        Label selectPriceShowings = new Label("");
        Button btn1 = new Button();
        btn1.setVisible(false);
        Button addShowingBtn = new Button("Add showings");
        addShowingBtn.setPrefWidth(100);
        addShowingBtn.setVisible(true);
        Button clearBtn = new Button("Clear");
        clearBtn.setVisible(true);
        clearBtn.setPrefWidth(100);

        //creating VBoxes to arrange the panel in columns
        VBox column1 = new VBox();
        column1.setSpacing(15);
        column1.setPadding(new Insets(10));
        column1.getChildren().addAll(showingTitleLbl, showingRoomLbl, showingSeatsLbl);
        VBox column2 = new VBox();
        column2.setSpacing(3);
        column2.setPadding(new Insets(10));
        column2.getChildren().addAll(selectShowingTitle, selectShowingRoom, selectShowingSeats);
        VBox column3 = new VBox();
        column3.setSpacing(15);
        column3.setPadding(new Insets(10));
        column3.getChildren().addAll(startShowings, endShowings, priceShowings);
        VBox column4 = new VBox();
        column4.setSpacing(9);
        column4.setPadding(new Insets(10));
        column4.getChildren().addAll(datePicker, selectEndShowings, selectPriceShowings);
        VBox columnTimePick = new VBox();
        columnTimePick.setSpacing(5);
        columnTimePick.setPadding(new Insets(10));
        columnTimePick.getChildren().add(timePicker);
        VBox columnButtons = new VBox();
        columnButtons.setSpacing(1);
        columnButtons.setPadding(new Insets(10));
        columnButtons.getChildren().addAll(btn1, addShowingBtn, clearBtn);

        //add the VBox columns to the panel
        showingsPanel.setSpacing(10);
        showingsPanel.getChildren().addAll(column1, column2, column3, column4, columnTimePick, columnButtons);

        //add everything to layout
        layout.add(menu, 0, 0);
        layout.add(pageHeader, 0, 1);
        layout.add(vboxPanels, 0, 2);
        layout.add(showingsPanel, 0, 3);
        layout.add(errorPanel, 0, 4);

        //styles
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.DARK);
        layout.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        stage.show();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //display number of seats depending on room
        selectShowingRoom.setOnAction((EventHandler<ActionEvent>) actionEvent -> {
            String value = (String) selectShowingRoom.getSelectionModel().getSelectedItem();
            if (Objects.equals(value, "Room 1")) {
                selectShowingSeats.setText("200");
            } else if (Objects.equals(value, "Room 2")) {
                selectShowingSeats.setText("100");
            } else {
                error.setText("Error");
            }
        });

        //display prices of showings depending on movie
        selectShowingTitle.setOnAction(actionEvent -> {
            String value1 = selectShowingTitle.getSelectionModel().getSelectedItem();
            if (Objects.equals(value1, "No time to lie")) {
                selectPriceShowings.setText("12.00");
            } else if (Objects.equals(value1, "The Addams Family 19")) {
                selectPriceShowings.setText("9.00");
            } else {
                error.setText("Error");
            }
        });

        //display the end time based on start time
        EventHandler handler = actionEvent ->
        {
            try {
                Movie movie = database.findMovie(selectShowingTitle.getSelectionModel().getSelectedItem());
                LocalDate startDate = datePicker.getValue();
                String startTime0 = timePicker.getText().trim();

                if (startDate == null || startTime0.isEmpty()) {
                    selectEndShowings.setText("");
                    return;
                }
                try {
                    LocalTime startTime = LocalTime.parse(startTime0);
                    LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                    LocalDateTime endTime = startDateTime.plusMinutes(movie.getMovieDuration());
                    selectEndShowings.setText(endTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                } catch (Exception x) {
                    selectEndShowings.setText("Wrong time format (use f.e. 15:00)");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        datePicker.setOnAction(handler);
        timePicker.setOnKeyTyped(handler);

        //add showing
        addShowingBtn.setOnAction(actionEvent -> {
            if (selectShowingTitle.getSelectionModel().isEmpty() || selectShowingRoom.getSelectionModel().isEmpty() || datePicker.getEditor().getText().isEmpty() || timePicker.getText().isEmpty()) {
                error.setText("Missing fields");
                error.setVisible(true);
                return;
            }
            try {
                Movie movie = database.findMovie(selectShowingTitle.getSelectionModel().getSelectedItem());
                Integer seatsNr = Integer.parseInt(selectShowingSeats.getText());
                String roomNr = selectShowingRoom.getValue().toString();
                LocalDateTime startDateTime;
                try {
                    LocalDate startDate = datePicker.getValue();
                    String stringStartTime = timePicker.getText().trim();
                    LocalTime startTime = LocalTime.parse(stringStartTime);
                    startDateTime = LocalDateTime.of(startDate, startTime);
                } catch (Exception e) {
                    return;
                }
                LocalDateTime endTime = startDateTime.plusMinutes(movie.getMovieDuration());
                Showing showing = new Showing(startDateTime, endTime, movie, seatsNr);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you want to add this showing?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() != ButtonType.OK) {
                    error.setText("You canceled the process");
                    error.setVisible(true);
                    return;
                }
                database.addShowing(showing, roomNr);

                if (roomNr.equals("Room 1")) {
                    room1TableView.getItems().add(showing);
                } else {
                    room2TableView.getItems().add(showing);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //clear form
        clearBtn.setOnAction(actionEvent -> {
            selectShowingSeats.setText(" ");
            selectEndShowings.setText(" ");
            selectPriceShowings.setText(" ");
            timePicker.setText("");
            datePicker.getEditor().setText("");
            selectShowingRoom.getSelectionModel().clearSelection();
            selectShowingTitle.getSelectionModel().clearSelection();
        });

        //stop the program when closing the app (press right top corner X)
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }
}