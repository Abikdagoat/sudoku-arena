package com.sudokuarena.controller;

import com.sudokuarena.app.SceneManager;
import com.sudokuarena.dao.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void goRegister() {
        boolean ok = UserDao.register(
                usernameField.getText(),
                passwordField.getText()
        );

        if (!ok) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Username already exists");
            a.setContentText("Please choose another username");
            a.show();
            return;
        }

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success");
        a.setHeaderText("Account created");
        a.show();

        SceneManager.show("login.fxml", "Login");
    }

    @FXML
    public void onBack() {
        SceneManager.show("login.fxml", "Login");
    }

    private void show(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}