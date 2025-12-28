package com.sudokuarena.controller;

import com.sudokuarena.app.SceneManager;
import com.sudokuarena.dao.ResultDao;
import com.sudokuarena.dao.UserDao;
import com.sudokuarena.model.Result;
import com.sudokuarena.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {

    @FXML private TableView<Result> table;

    @FXML private TableColumn<Result, Integer> colId;
    @FXML private TableColumn<Result, String> colUsername;
    @FXML private TableColumn<Result, String> colDifficulty;
    @FXML private TableColumn<Result, Integer> colTime;
    @FXML private TableColumn<Result, Boolean> colSolved;
    @FXML private TableColumn<Result, String> colCreated;

    // ===== USERS TABLE =====
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> usernameCol;
    @FXML private TableColumn<User, String> roleCol;

    // ===== INIT =====
    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("timeSeconds"));
        colSolved.setCellValueFactory(new PropertyValueFactory<>("solved"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        usersTable.setItems(FXCollections.observableArrayList(UserDao.getAllUsers()));
        table.setItems(FXCollections.observableArrayList(ResultDao.getAll()));
    }


    // ===== INIT DB =====
    @FXML
    public void onInitDb() {
        UserDao.createTable();
        ResultDao.createTable();
        UserDao.createAdminIfNotExists();

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Database");
        a.setHeaderText("Database initialized");
        a.setContentText("Tables created and admin ensured");
        a.showAndWait();

        // refresh
        if (table != null) {
            table.setItems(FXCollections.observableArrayList(ResultDao.getAll()));
        }
        if (usersTable != null) {
            usersTable.setItems(
                    FXCollections.observableArrayList(UserDao.getAllUsers())
            );
        }
    }

    // ===== BACK =====
    @FXML
    public void onBack() {
        SceneManager.show("menu.fxml", "Sudoku Arena");
    }
}