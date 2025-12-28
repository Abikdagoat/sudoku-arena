package com.sudokuarena.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private static final String URL = "jdbc:sqlite:sudoku.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}