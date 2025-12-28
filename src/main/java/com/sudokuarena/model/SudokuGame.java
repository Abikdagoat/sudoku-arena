package com.sudokuarena.model;

public class SudokuGame {
    public final int[][] puzzle;
    public final int[][] solution;
    public final boolean[][] fixed;
    public final Difficulty difficulty;

    public SudokuGame(int[][] puzzle, int[][] solution, boolean[][] fixed, Difficulty difficulty) {
        this.puzzle = puzzle;
        this.solution = solution;
        this.fixed = fixed;
        this.difficulty = difficulty;
    }

    public boolean isSolved(int[][] current) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (current[r][c] != solution[r][c]) return false;
        return true;
    }
}