package com.sudokuarena.model;

public enum Difficulty {
    EASY(40),    // сколько пустых клеток (примерно)
    MEDIUM(50),
    HARD(58);

    public final int holes;

    Difficulty(int holes) {
        this.holes = holes;
    }
}