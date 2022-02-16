package nl.inholland.ui;

import dao.Database;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Database database = new Database();
        new Login(database);
        stage.close();
    }
}