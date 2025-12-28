package com.sudokuarena.app;

import com.sudokuarena.dao.ResultDao;
import com.sudokuarena.dao.UserDao;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        UserDao.createTable();
        ResultDao.createTable();
        UserDao.createAdminIfNotExists();

        SceneManager.init(stage);
        SceneManager.show("login.fxml", "Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}