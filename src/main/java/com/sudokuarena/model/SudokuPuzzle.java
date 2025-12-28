package com.sudokuarena.model;

public class SudokuPuzzle {

    private final int[][] grid = new int[9][9];

    // Проверка: можно ли поставить value в (row, col)
    public boolean isValidMove(int row, int col, int value) {

        // строка
        for (int c = 0; c < 9; c++) {
            if (grid[row][c] == value) {
                return false;
            }
        }

        // колонка
        for (int r = 0; r < 9; r++) {
            if (grid[r][col] == value) {
                return false;
            }
        }

        // квадрат 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (grid[r][c] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setValue(int row, int col, int value) {
        grid[row][col] = value;
    }

    public void clearValue(int row, int col) {
        grid[row][col] = 0;
    }

    public int getValue(int row, int col) {
        return grid[row][col];
    }
}