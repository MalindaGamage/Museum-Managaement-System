package dao;

import model.User;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDAO {
    public User getUserByUsername(String username) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            UUID.fromString(rs.getString("user_id")),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getBoolean("is_visitor")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving user: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (user_id, username, password, role, email, is_visitor) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserId().toString());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getEmail());
            stmt.setBoolean(6, user.isVisitor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ?, email = ?, is_visitor = ? WHERE user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getEmail());
            stmt.setBoolean(5, user.isVisitor());
            stmt.setString(6, user.getUserId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        UUID.fromString(rs.getString("user_id")),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getBoolean("is_visitor")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
