package com.sudokuarena.model;

import java.util.Random;

public class SudokuGenerator {
    private final Random rnd = new Random();
    private final SudokuSolver solver = new SudokuSolver();

    public SudokuGame generate(Difficulty diff) {
        int[][] solution = new int[9][9];
        fillDiagonalBoxes(solution);
        solver.solve(solution);

        int[][] puzzle = SudokuSolver.copy(solution);

        // делаем дырки, сохраняя уникальность
        int removed = 0;
        int attempts = 0;

        while (removed < diff.holes && attempts < 2000) {
            attempts++;
            int r = rnd.nextInt(9);
            int c = rnd.nextInt(9);
            if (puzzle[r][c] == 0) continue;

            int backup = puzzle[r][c];
            puzzle[r][c] = 0;

            int[][] tmp = SudokuSolver.copy(puzzle);
            int solutions = solver.countSolutions(tmp, 2);

            if (solutions != 1) { // не уникально — откат
                puzzle[r][c] = backup;
            } else {
                removed++;
            }
        }

        boolean[][] fixed = new boolean[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                fixed[r][c] = puzzle[r][c] != 0;

        return new SudokuGame(puzzle, solution, fixed, diff);
    }

    private void fillDiagonalBoxes(int[][] grid) {
        for (int k = 0; k < 3; k++) fillBox(grid, k * 3, k * 3);
    }

    private void fillBox(int[][] grid, int row, int col) {
        boolean[] used = new boolean[10];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int v;
                do { v = 1 + rnd.nextInt(9); } while (used[v]);
                used[v] = true;
                grid[row + r][col + c] = v;
            }
        }
    }
}
