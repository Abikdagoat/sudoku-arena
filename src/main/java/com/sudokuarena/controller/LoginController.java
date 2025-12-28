package com.sudokuarena.controller;

import com.sudokuarena.app.AppState;
import com.sudokuarena.app.SceneManager;
import com.sudokuarena.dao.UserDao;
import com.sudokuarena.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void onLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = UserDao.login(username, password);

        if (user == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Login error");
            a.setHeaderText("Invalid credentials");
            a.setContentText("Username or password is incorrect");
            a.show();
            return;
        }

        AppState.currentUser = user;

        System.out.println(
                "Logged user: " + user.getUsername() + ", role=" + user.getRole()
        );

        SceneManager.show("menu.fxml", "Menu");
    }

    @FXML
    public void onRegister() {
        SceneManager.show("register.fxml", "Register");
    }
}