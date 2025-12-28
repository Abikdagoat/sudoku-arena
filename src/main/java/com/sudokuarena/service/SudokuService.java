package com.sudokuarena.service;

import java.util.*;

public final class SudokuService {

    // Map used for fast lookup (Collections requirement)
    private final Map<Integer, Set<Character>> rowUsed = new HashMap<>();
    private final Map<Integer, Set<Character>> colUsed = new HashMap<>();
    private final Map<Integer, Set<Character>> boxUsed = new HashMap<>();

    public SudokuService() {}

    public boolean isMoveValid(char[] current81, int r, int c, char value) {
        if (value < '1' || value > '9') return false;
        int idx = r * 9 + c;
        if (current81[idx] != '0') return false;

        char[] copy = Arrays.copyOf(current81, 81);
        copy[idx] = value;
        return isGridValid(copy);
    }

    public boolean isGridValid(char[] grid) {
        // clear maps
        rowUsed.clear(); colUsed.clear(); boxUsed.clear();
        for (int i = 0; i < 9; i++) {
            rowUsed.put(i, new HashSet<>());
            colUsed.put(i, new HashSet<>());
            boxUsed.put(i, new HashSet<>());
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char v = grid[r * 9 + c];
                if (v == '0') continue;
                int b = (r / 3) * 3 + (c / 3);

                if (rowUsed.get(r).contains(v)) return false;
                if (colUsed.get(c).contains(v)) return false;
                if (boxUsed.get(b).contains(v)) return false;

                rowUsed.get(r).add(v);
                colUsed.get(c).add(v);
                boxUsed.get(b).add(v);
            }
        }
        return true;
    }

    public boolean isSolved(char[] current81, String solution81) {
        return new String(current81).equals(solution81);
    }

    // Helper: parse string "81 chars" -> char[81]
    public char[] toChar81(String s) {
        if (s == null || s.length() != 81) throw new IllegalArgumentException("Grid must be 81 chars");
        return s.toCharArray();
    }

    // Helper: list of empty cells (List requirement)
    public List<Integer> emptyCells(char[] grid) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 81; i++) if (grid[i] == '0') res.add(i);
        return res;
    }
}