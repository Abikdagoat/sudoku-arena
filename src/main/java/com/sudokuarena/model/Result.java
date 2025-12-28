package com.sudokuarena.model;

public class Result {

    private int id;
    private String username;
    private String difficulty;
    private int timeSeconds;
    private boolean solved;
    private String createdAt;

    public Result(int id, String username, String difficulty, int timeSeconds, boolean solved, String createdAt) {
        this.id = id;
        this.username = username;
        this.difficulty = difficulty;
        this.timeSeconds = timeSeconds;
        this.solved = solved;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public boolean isSolved() {
        return solved;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}