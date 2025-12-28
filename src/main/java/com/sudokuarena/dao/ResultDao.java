package com.sudokuarena.dao;

import com.sudokuarena.model.Result;
import com.sudokuarena.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDao {

    public static void createTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS results (
                              id INTEGER PRIMARY KEY AUTOINCREMENT,
                              username TEXT NOT NULL,
                              difficulty TEXT NOT NULL,
                              time_seconds INTEGER NOT NULL,
                              solved INTEGER NOT NULL,
                              created_at TEXT NOT NULL
                          );
    """;

        try (Connection c = Database.get();
             Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(String username, String difficulty, int timeSeconds, boolean solved) {

        String sql = """
        INSERT INTO results(username, difficulty, time_seconds, solved, created_at)
        VALUES (?, ?, ?, ?, datetime('now'))
    """;

        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, difficulty);
            ps.setInt(3, timeSeconds);
            ps.setBoolean(4, solved);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Result> getAll() {
        List<Result> list = new ArrayList<>();

        try (Connection c = Database.get();
             Statement st = c.createStatement()){
             String sql = """
               SELECT id, username, difficulty, time_seconds, solved, created_at
               FROM results
               ORDER BY created_at DESC
               """;

             ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Result(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("difficulty"),
                        rs.getInt("time_seconds"),
                        rs.getBoolean("solved"),
                        rs.getString("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}