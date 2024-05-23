package dao;

import model.Visitor;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VisitorDAO {
    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        String sql = "SELECT * FROM visitors";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Visitor visitor = new Visitor(
                        UUID.fromString(rs.getString("visitor_id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("visit_date")
                );
                visitors.add(visitor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitors;
    }

    public void addVisitor(Visitor visitor) {
        String sql = "INSERT INTO visitors (fullName, email, phone, visitDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visitor.getFullName());
            stmt.setString(2, visitor.getEmail());
            stmt.setString(3, visitor.getPhone());
            stmt.setDate(4, new java.sql.Date(visitor.getVisitDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVisitor(Visitor visitor) {
        String sql = "UPDATE visitors SET fullName = ?, email = ?, phone = ?, visitDate = ? WHERE visitorId = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visitor.getFullName());
            stmt.setString(2, visitor.getEmail());
            stmt.setString(3, visitor.getPhone());
            stmt.setDate(4, new java.sql.Date(visitor.getVisitDate().getTime()));
            stmt.setString(5, visitor.getVisitorId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVisitor(int visitorId) {
        String sql = "DELETE FROM visitors WHERE visitorId = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, visitorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Visitor getVisitorByEmail(String email) {
        String sql = "SELECT * FROM visitors WHERE email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Visitor(
                        UUID.fromString(rs.getString("visitor_id")),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("visitDate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateVisitor(String email, int visitorId) {
        String sql = "SELECT * FROM visitors WHERE email = ? AND visitorId = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setInt(2, visitorId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
