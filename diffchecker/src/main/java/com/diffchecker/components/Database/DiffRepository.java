package com.diffchecker.components.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiffRepository {
  private final DB db;

  public DiffRepository(DB db) {
    this.db = db;
  }

  public DiffData getDiffById(int id) {
    String sql = "SELECT title, left_text, right_text FROM diff_tabs WHERE id = ?";
    try (Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        String title = rs.getString("title");
        String left = rs.getString("left_text");
        String right = rs.getString("right_text");
        return new DiffData(title, left, right);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
