package com.sudokuarena.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    public static void init(Stage primaryStage) {
        stage = primaryStage;
        stage.setMinWidth(900);
        stage.setMinHeight(600);
    }

    public static void show(String fxml, String title) {
        try {
            var url = SceneManager.class.getResource("/fxml/" + fxml);
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene(loader.load());

            var css = SceneManager.class.getResource("/css/app.css");
            if (css != null) {
                scene.getStylesheets().add(css.toExternalForm());
            }

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}