package com.sudokuarena.controller;

import com.sudokuarena.app.AppState;
import com.sudokuarena.app.SceneManager;
import com.sudokuarena.dao.ResultDao;
import com.sudokuarena.model.SudokuGame;
import com.sudokuarena.model.SudokuGenerator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class SudokuController {

    @FXML private GridPane grid;
    @FXML private Label timerLabel;
    @FXML private Label difficultyLabel;

    private final TextField[][] cells = new TextField[9][9];
    private final SudokuGenerator generator = new SudokuGenerator();

    private SudokuGame game;
    private Timeline timeline;
    private int seconds = 0;
    private boolean gameFinished = false;

    @FXML
    public void initialize() {
        startNewGame();
    }


    private void startNewGame() {
        gameFinished = false;
        seconds = 0;

        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timerLabel.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        game = generator.generate(AppState.difficulty);
        difficultyLabel.setText(game.difficulty.name());

        buildGrid();
        highlightConflicts();
    }

    private void buildGrid() {
        grid.getChildren().clear();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                TextField tf = new TextField();
                tf.setPrefSize(44, 44);

                String border = borderStyle(r, c);
                tf.setStyle("-fx-font-size:16px; -fx-alignment:center;" + border);

                if (game.fixed[r][c]) {
                    tf.setText(String.valueOf(game.puzzle[r][c]));
                    tf.setEditable(false);
                    tf.setStyle("-fx-font-size:16px; -fx-alignment:center; -fx-font-weight:bold; -fx-opacity:1.0;" + border);
                } else {
                    tf.textProperty().addListener((obs, oldV, newV) -> {
                        if (newV == null || newV.isEmpty()) {
                            tf.setStyle("-fx-font-size:16px; -fx-alignment:center;" + border);
                            return;
                        }

                        if (!newV.matches("[1-9]")) {
                            tf.setText("");
                            return;
                        }

                        if (newV.length() > 1) {
                            tf.setText(newV.substring(newV.length() - 1));
                        }

                        highlightConflicts();
                    });
                }

                cells[r][c] = tf;
                grid.add(tf, c, r);
            }
        }
    }

    @FXML
    public void onHint() {
        if (gameFinished) return;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!game.fixed[r][c]) {
                    String text = cells[r][c].getText();
                    if (text == null || text.isEmpty()) {
                        cells[r][c].setText(String.valueOf(game.solution[r][c]));
                        highlightConflicts();
                        return;
                    }
                }
            }
        }
    }

    @FXML
    public void onCheck() {
        if (gameFinished) return;

        int[][] cur = readCurrent();

        if (hasAnyConflicts(cur)) {
            alert("Check", "There are mistakes", "Fix highlighted cells first.");
            ResultDao.save(
                    AppState.currentUser.getUsername(),
                    game.difficulty.name(),
                    seconds,
                    false
            );
        }

        if (isSolved(cur)) {
            onWin();
        } else {
            alert("Check", "Not solved yet", "Keep going 🙂");
            ResultDao.save(
                    AppState.currentUser.getUsername(),
                    game.difficulty.name(),
                    seconds,
                    false
            );
        }
    }

    private void onWin() {
        gameFinished = true;
        timeline.stop();

        String username = AppState.currentUser.getUsername();

        ResultDao.save(
                AppState.currentUser.getUsername(),
                game.difficulty.name(),
                seconds,
                true
        );

        alert("Victory!",
                "You solved the Sudoku!",
                "Time: " + timerLabel.getText() + "\nDifficulty: " + game.difficulty);
    }

    /* ===================== HELPERS ===================== */

    private boolean isSolved(int[][] cur) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (cur[r][c] != game.solution[r][c])
                    return false;
        return true;
    }

    private boolean hasAnyConflicts(int[][] cur) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (cur[r][c] != 0 && !game.fixed[r][c] && hasConflict(cur, r, c, cur[r][c]))
                    return true;
        return false;
    }

    private int[][] readCurrent() {
        int[][] cur = new int[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                String t = cells[r][c].getText();
                cur[r][c] = (t == null || t.isEmpty()) ? 0 : Integer.parseInt(t);
            }
        return cur;
    }

    private void highlightConflicts() {
        int[][] cur = readCurrent();

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (!game.fixed[r][c]) {
                    String border = borderStyle(r, c);
                    cells[r][c].setStyle("-fx-font-size:16px; -fx-alignment:center;" + border);
                }

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                int v = cur[r][c];
                if (v != 0 && !game.fixed[r][c] && hasConflict(cur, r, c, v)) {
                    String border = borderStyle(r, c);
                    cells[r][c].setStyle("-fx-font-size:16px; -fx-alignment:center; -fx-background-color:#ffcccc;" + border);
                }
            }
    }

    private boolean hasConflict(int[][] cur, int row, int col, int v) {
        for (int c = 0; c < 9; c++) if (c != col && cur[row][c] == v) return true;
        for (int r = 0; r < 9; r++) if (r != row && cur[r][col] == v) return true;

        int sr = (row / 3) * 3;
        int sc = (col / 3) * 3;
        for (int r = sr; r < sr + 3; r++)
            for (int c = sc; c < sc + 3; c++)
                if (!(r == row && c == col) && cur[r][c] == v)
                    return true;

        return false;
    }

    private String borderStyle(int r, int c) {
        int top = (r % 3 == 0) ? 2 : 1;
        int left = (c % 3 == 0) ? 2 : 1;
        int bottom = (r == 8) ? 2 : 1;
        int right = (c == 8) ? 2 : 1;
        return String.format("-fx-border-color:#555; -fx-border-width:%d %d %d %d;",
                top, right, bottom, left);
    }

    private void alert(String title, String header, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(text);
        a.show();
    }

    /* ===================== NAV ===================== */

    @FXML
    public void onNewGame() {
        startNewGame();
    }

    @FXML
    public void onBack() {
        if (timeline != null) timeline.stop();
        SceneManager.show("menu.fxml", "Sudoku Arena");
    }
}