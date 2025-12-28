package com.sudokuarena.dao;

import com.sudokuarena.model.User;
import com.sudokuarena.util.Database;
import com.sudokuarena.util.Hash;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static void createAdminIfNotExists() {
        String sql = """
        INSERT INTO users(username, password, role, created_at)
        SELECT ?, ?, 'ADMIN', datetime('now')
        WHERE NOT EXISTS (
            SELECT 1 FROM users WHERE username='admin'
        );
    """;

        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "admin");
            ps.setString(2, Hash.sha256("admin"));
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT UNIQUE NOT NULL,
            password TEXT NOT NULL,
            role TEXT NOT NULL DEFAULT 'USER',
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

    public static User login(String username, String password) {

        String sql = """
        SELECT username, role
        FROM users
        WHERE username = ? AND password = ?
    """;

        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, Hash.sha256(password)); // 🔐 ХЭШ

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        null,
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean register(String username, String password) {
        String sql = """
        INSERT INTO users(username, password, role, created_at)
        VALUES (?, ?, 'USER', datetime('now'))
    """;

        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, Hash.sha256(password)); // 🔐 ХЭШ

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT username, role FROM users";

        try (Connection c = Database.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new User(
                        rs.getString("username"),
                        "",
                        rs.getString("role")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    

}
