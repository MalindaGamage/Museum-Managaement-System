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
                        rs.getDate("visit_date"),
                        rs.getInt("group_size")
                );
                visitors.add(visitor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitors;
    }

    public void addVisitor(Visitor visitor) {
        String sql = "INSERT INTO visitors (visitor_id, full_name, email, phone, visit_date, group_size) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visitor.getVisitorId().toString());
            stmt.setString(2, visitor.getFullName());
            stmt.setString(3, visitor.getEmail());
            stmt.setString(4, visitor.getPhone());
            stmt.setDate(5, new java.sql.Date(visitor.getVisitDate().getTime()));
            stmt.setInt(6, visitor.getGroupSize());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVisitor(Visitor visitor) {
        String sql = "UPDATE visitors SET full_name = ?, email = ?, phone = ?, visit_date = ?, group_size = ? WHERE visitor_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visitor.getFullName());
            stmt.setString(2, visitor.getEmail());
            stmt.setString(3, visitor.getPhone());
            stmt.setDate(4, new java.sql.Date(visitor.getVisitDate().getTime()));
            stmt.setInt(5, visitor.getGroupSize());
            stmt.setString(6, visitor.getVisitorId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVisitor(UUID visitorId) {
        String sql = "DELETE FROM visitors WHERE visitor_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visitorId.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Visitor getVisitorById(UUID visitorId) {
        String sql = "SELECT * FROM visitors WHERE visitor_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visitorId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Visitor(
                        UUID.fromString(rs.getString("visitor_id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("visit_date"),
                        rs.getInt("group_size")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateVisitor(String email, UUID visitorId) {
        String sql = "SELECT * FROM visitors WHERE email = ? AND visitor_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, visitorId.toString());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
