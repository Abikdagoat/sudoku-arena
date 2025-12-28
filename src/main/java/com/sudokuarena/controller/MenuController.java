package com.sudokuarena.controller;

import com.sudokuarena.app.AppState;
import com.sudokuarena.app.SceneManager;
import com.sudokuarena.model.Difficulty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class MenuController {

    @FXML
    private ChoiceBox<String> difficultyChoice;

    @FXML
    private Button adminBtn;

    @FXML
    public void initialize() {
        // difficulty
        difficultyChoice.getItems().addAll("EASY", "MEDIUM", "HARD");
        difficultyChoice.setValue("EASY");

        // hide admin button if not admin
        if (AppState.currentUser == null || !AppState.currentUser.isAdmin()) {
            adminBtn.setVisible(false);
        }
    }

    @FXML
    public void onStart() {
        AppState.difficulty = Difficulty.valueOf(difficultyChoice.getValue());
        SceneManager.show("sudoku.fxml", "Sudoku — " + AppState.difficulty);
    }

    @FXML
    public void onAdmin() {
        if (AppState.currentUser == null || !AppState.currentUser.isAdmin()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Access denied");
            a.setHeaderText("Admin only");
            a.setContentText("You must be admin to access this panel");
            a.show();
            return;
        }

        SceneManager.show("admin.fxml", "Admin Panel");
    }

    @FXML
    public void onLogout() {
        AppState.currentUser = null;
        SceneManager.show("login.fxml", "Login");
    }

    @FXML
    public void onExit() {
        Platform.exit();
    }
}