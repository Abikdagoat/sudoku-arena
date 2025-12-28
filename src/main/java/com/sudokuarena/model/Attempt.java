package com.sudokuarena.model;

public record Attempt(int id, int userId, int puzzleId, int timeSeconds, int mistakes, boolean solved) {}
