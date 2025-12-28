package com.sudokuarena.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:sqlite:sudoku.db";

    public static Connection get() throws Exception {
        return DriverManager.getConnection(URL);
    }
}