package dao;

import model.User;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDAO {
    // Method to fetch user by username for authentication
    public User getUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getBoolean("is_visitor")
                );
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving user: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Ensure resources are closed
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.err.println("SQLException on closing: " + ex.getMessage());
            }
        }
        return null;
    }

    // Method to add a new user to the database
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // Remember to hash the password in real-world applications
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Date, Integer> visitorCountByDate() {
        Map<Date, Integer> counts = new HashMap<>();
        String sql = "SELECT visit_date, COUNT(*) as count FROM visitors GROUP BY visit_date";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                counts.put(rs.getDate("visit_date"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET password = ?, role = ?, email = ? WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword()); // Ensure password is correctly hashed if necessary
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUsername());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No rows affected, check if the username exists: " + user.getUsername());
                // Optionally, throw an exception or handle this case as needed
            } else {
                System.out.println("model.User updated successfully: " + user.getUsername());
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            // Re-throw if necessary
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
                        rs.getInt("user_id"),
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
    // Optionally, add methods to update and delete users if necessary
}
