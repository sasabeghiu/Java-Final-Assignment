package nl.inholland.ui;

import dao.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import model.Showing;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.swing.*;
import java.util.Optional;

public class PurchaseTickets {
    private final Stage stage;
    private final Database database;
    TableView<Showing> room1TableView;
    TableView<Showing> room2TableView;

    public PurchaseTickets(User user, Database database) {
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
        stage.setTitle("Fantastic Cinema -- purchase tickets -- username: " + user.getUsername());

        //create the menu
        NavigationMenu menuBar = new NavigationMenu();

        //title
        Label pageHeader = new Label("Purchase Tickets");
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
        HBox downPanel = new HBox();
        downPanel.setPadding(new Insets(10));
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

        //creating the elements and the HBox form for selling tickets
        Label room = new Label("Room");
        Label start = new Label("Start");
        Label end = new Label("End");
        Label roomShowing = new Label("");
        Label startShowing = new Label("");
        Label endShowing = new Label("");
        Label title = new Label("Title");
        Label tickets = new Label("No. of seats");
        Label name = new Label("Name");
        Label titleShowing = new Label("");
        ComboBox ticketsShowing = new ComboBox();
        ticketsShowing.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        TextField nameShowing = new TextField();
        Button btn = new Button();
        btn.setVisible(false);
        Button purchaseBtn = new Button("Purchase");
        purchaseBtn.setPrefWidth(100);
        purchaseBtn.setVisible(true);
        Button clearBtn = new Button("Clear");
        clearBtn.setPrefWidth(100);
        clearBtn.setVisible(true);

        //creating VBoxes to arrange the panel in columns
        VBox column1 = new VBox();
        column1.setSpacing(15);
        column1.setPadding(new Insets(10));
        column1.getChildren().addAll(room, start, end);
        VBox column2 = new VBox();
        column2.setSpacing(15);
        column2.setPadding(new Insets(10));
        column2.getChildren().addAll(roomShowing, startShowing, endShowing);
        VBox column3 = new VBox();
        column3.setSpacing(15);
        column3.setPadding(new Insets(10));
        column3.getChildren().addAll(title, tickets, name);
        VBox column4 = new VBox();
        column4.setSpacing(5);
        column4.setPadding(new Insets(10));
        column4.getChildren().addAll(titleShowing, ticketsShowing, nameShowing);
        VBox columnButtons = new VBox();
        columnButtons.setSpacing(1);
        columnButtons.setPadding(new Insets(5));
        columnButtons.getChildren().addAll(btn, purchaseBtn, clearBtn);

        //add the VBox columns to the panel
        downPanel.setSpacing(10);
        downPanel.getChildren().addAll(column1, column2, column3, column4, columnButtons);
        downPanel.setVisible(false);

        //add everything to layout
        layout.add(menu, 0, 0);
        layout.add(pageHeader, 0, 1);
        layout.add(vboxPanels, 0, 2);
        layout.add(downPanel, 0, 3);
        layout.add(errorPanel, 0, 4);

        //styles
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.DARK);
        layout.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        stage.show();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //selecting a showing in room 1 and display the form for purchasing tickets
        room1TableView.getSelectionModel().selectedItemProperty().addListener((observableValue, showing, t1) -> {
            if (room1TableView.getSelectionModel().getSelectedItem() != null) {
                downPanel.setVisible(true);
                roomShowing.setText("Room 1");
                startShowing.setText(room1TableView.getSelectionModel().getSelectedItem().getStartTime());
                endShowing.setText(room1TableView.getSelectionModel().getSelectedItem().getEndTime());
                titleShowing.setText(room1TableView.getSelectionModel().getSelectedItem().getMovieTitle());
            }
        });

        //selecting a showing in room 2 and display the form for purchasing tickets
        room2TableView.getSelectionModel().selectedItemProperty().addListener((observableValue, showing, t1) -> {
            if (room2TableView.getSelectionModel().getSelectedItem() != null) {
                downPanel.setVisible(true);
                roomShowing.setText("Room 2");
                startShowing.setText(room2TableView.getSelectionModel().getSelectedItem().getStartTime());
                endShowing.setText(room2TableView.getSelectionModel().getSelectedItem().getEndTime());
                titleShowing.setText(room2TableView.getSelectionModel().getSelectedItem().getMovieTitle());
            }
        });

        //purchase button (purchase tickets form)
        purchaseBtn.setOnAction(actionEvent -> {
            if (ticketsShowing.getValue() == null || nameShowing.getText().isEmpty()) {
                error.setVisible(true);
                error.setText("You did not introduce your name or select the number of tickets.");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you want to buy this ticket?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (!room1TableView.getSelectionModel().isEmpty()) {
                        Showing movieRoom1 = room1TableView.getSelectionModel().getSelectedItem();
                        movieRoom1.setSeatsNumber(movieRoom1.getSeatsNumber() - Integer.parseInt(ticketsShowing.getSelectionModel().getSelectedItem().toString()));
                    } else if (!room2TableView.getSelectionModel().isEmpty()) {
                        Showing movieRoom2 = room2TableView.getSelectionModel().getSelectedItem();
                        movieRoom2.setSeatsNumber(movieRoom2.getSeatsNumber() - Integer.parseInt(ticketsShowing.getSelectionModel().getSelectedItem().toString()));
                    } else {
                        error.setText("Error");
                    }
                    nameShowing.clear();
                    ticketsShowing.getSelectionModel().clearSelection();
                    error.setText("Purchase completed");
                    error.setVisible(true);
                    Timer timer = new Timer(1000, e -> error.setVisible(false));
                    timer.setRepeats(false);
                    timer.start();
                    downPanel.setVisible(false);
                    room1TableView.refresh();
                    room1TableView.getSelectionModel().clearSelection();
                    room2TableView.refresh();
                    room2TableView.getSelectionModel().clearSelection();
                } else {
                    error.setText("You canceled the purchase");
                    error.setVisible(true);
                }
            }
        });

        //clear button (purchase tickets form)
        clearBtn.setOnAction(actionEvent -> {
            roomShowing.setText("");
            startShowing.setText("");
            endShowing.setText("");
            titleShowing.setText("");
            nameShowing.clear();
            ticketsShowing.getSelectionModel().clearSelection();
            downPanel.setVisible(false);
            error.setVisible(false);
        });

        //stop the program when closing the app (press right top corner X)
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }
}