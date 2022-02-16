package nl.inholland.ui;

import dao.Database;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.util.Optional;

public class NavigationMenu {
    private final MenuBar menuBar;
    private final Menu admin;
    private final MenuItem purchaseTickets;
    private final MenuItem manageShowings;
    private final MenuItem manageMovies;
    private final Menu help;
    private final MenuItem about;
    private final Menu logout;
    private final MenuItem logout1;

    public NavigationMenu() {
        menuBar = new MenuBar();
        admin = new Menu("Admin");
        purchaseTickets = new MenuItem("Purchase Tickets");
        manageShowings = new MenuItem("Manage Showings");
        manageMovies = new MenuItem("Manage Movies");
        help = new Menu("Help");
        about = new MenuItem("About");
        logout = new Menu("Logout");
        logout1 = new MenuItem("Logout");
    }

    public MenuBar getMenuBar(Stage stage, User user, Database database) {
        admin.setVisible(user.getUsertype() == User.UserType.Admin);

        admin.getItems().addAll(purchaseTickets, manageShowings, manageMovies);
        help.getItems().add(about);
        logout.getItems().add(logout1);
        menuBar.getMenus().addAll(admin, help, logout);

        purchaseTickets.setOnAction(actionEvent -> {
            new PurchaseTickets(user, database);
            stage.close();
        });

        manageShowings.setOnAction(actionEvent -> {
            new ManageShowings(user, database);
            stage.close();
        });

        manageMovies.setOnAction(actionEvent -> {
            new ManageMovies(user, database);
            stage.close();
        });

        logout1.setOnAction(actionEvent -> {
            try {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Are you sure you want to log out?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    new Login(database);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.close();
        });
        return menuBar;
    }
}