package dao;

import model.Exhibition;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDAO {
    public List<Exhibition> getAllExhibitions() {
        List<Exhibition> exhibitions = new ArrayList<>();
        String sql = "SELECT * FROM exhibitions";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Exhibition exhibition = new Exhibition(
                        rs.getInt("exhibition_id"),
                        rs.getString("title"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("description"),
                        rs.getBoolean("is_active")
                );
                exhibitions.add(exhibition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibitions;
    }

    public void addExhibition(Exhibition exhibition) {
        String sql = "INSERT INTO exhibitions (title, start_date, end_date, description, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exhibition.getTitle());
            stmt.setDate(2, new java.sql.Date(exhibition.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(exhibition.getEndDate().getTime()));
            stmt.setString(4, exhibition.getDescription());
            stmt.setBoolean(5, exhibition.isActive());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExhibition(Exhibition exhibition) {
        String sql = "UPDATE exhibitions SET title = ?, start_date = ?, end_date = ?, description = ?, is_active = ? WHERE exhibition_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exhibition.getTitle());
            stmt.setDate(2, new java.sql.Date(exhibition.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(exhibition.getEndDate().getTime()));
            stmt.setString(4, exhibition.getDescription());
            stmt.setBoolean(5, exhibition.isActive());
            stmt.setInt(6, exhibition.getExhibitionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExhibition(int exhibitionId) {
        String sql = "DELETE FROM exhibitions WHERE exhibition_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exhibitionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}