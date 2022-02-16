package nl.inholland.ui;

import dao.Database;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import model.User;
import service.UserService;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Login {
    private final UserService userService = new UserService();

    public Login(Database database) {
        Stage stage = new Stage();
        stage.setHeight(200);
        stage.setWidth(350);
        stage.setTitle("Fabulous Cinema -- Login");

        GridPane myGrid = new GridPane();
        myGrid.setPadding(new Insets(10, 10, 10, 10));
        myGrid.setVgap(10);
        myGrid.setHgap(8);

        Label userLabel = new Label("Username: ");
        myGrid.add(userLabel, 0, 0);
        TextField userTextField = new TextField();
        userTextField.setPromptText("username");
        myGrid.add(userTextField, 1, 0);

        Label passwordLabel = new Label("Password: ");
        myGrid.add(passwordLabel, 0, 1);
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.setPromptText("password");
        StringProperty passwordProperty = passwordTextField.textProperty();
        myGrid.add(passwordTextField, 1, 1);

        Label secretLabel = new Label("Bad credentials!");
        secretLabel.setVisible(false);
        myGrid.add(secretLabel, 0, 3);

        Button logIn = new Button("Log In");
        logIn.setVisible(false);
        myGrid.add(logIn, 1, 2);

        Scene scene = new Scene(myGrid);
        stage.setScene(scene);

        JMetro jMetro = new JMetro(Style.DARK);
        myGrid.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        jMetro.setScene(scene);
        stage.show();

        logIn.setOnAction(actionEvent -> {
            if (!userTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
                User user = userService.isValidUsername(userTextField.getText(), passwordTextField.getText());
                if (user != null) {
                    new PurchaseTickets(user, database);
                    stage.close();
                }
            }
            secretLabel.setVisible(true);
            secretLabel.setTextFill(Color.RED);
        });

        passwordProperty.addListener((observableValue, oldValue, newValue) -> logIn.setVisible(userService.isValidPassword(passwordTextField.getText())));

        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }
}