package com.sudokuarena.model;

public class SudokuSolver {

    public boolean solve(int[][] grid) {
        int[] pos = findEmpty(grid);
        if (pos == null) return true;

        int r = pos[0], c = pos[1];

        for (int v = 1; v <= 9; v++) {
            if (isValid(grid, r, c, v)) {
                grid[r][c] = v;
                if (solve(grid)) return true;
                grid[r][c] = 0;
            }
        }
        return false;
    }

    // Счётчик решений (для уникальности). Останавливается на 2.
    public int countSolutions(int[][] grid, int limit) {
        int[] pos = findEmpty(grid);
        if (pos == null) return 1;

        int r = pos[0], c = pos[1];
        int count = 0;

        for (int v = 1; v <= 9; v++) {
            if (isValid(grid, r, c, v)) {
                grid[r][c] = v;
                count += countSolutions(grid, limit);
                grid[r][c] = 0;
                if (count >= limit) return count;
            }
        }
        return count;
    }

    public boolean isValid(int[][] grid, int row, int col, int val) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == val) return false;
            if (grid[i][col] == val) return false;
        }
        int sr = (row / 3) * 3;
        int sc = (col / 3) * 3;
        for (int r = sr; r < sr + 3; r++)
            for (int c = sc; c < sc + 3; c++)
                if (grid[r][c] == val) return false;

        return true;
    }

    private int[] findEmpty(int[][] grid) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (grid[r][c] == 0) return new int[]{r, c};
        return null;
    }

    public static int[][] copy(int[][] a) {
        int[][] b = new int[9][9];
        for (int r = 0; r < 9; r++) System.arraycopy(a[r], 0, b[r], 0, 9);
        return b;
    }
}