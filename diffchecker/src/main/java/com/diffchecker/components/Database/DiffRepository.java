package com.diffchecker.components.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiffRepository {
  private final DB db;

  public DiffRepository(DB db) {
    this.db = db;
  }

  public List<DiffData> getAllDiffs() {
    List<DiffData> list = new ArrayList<>();
    String sql = "SELECT id, title, left_text, right_text FROM diff_tabs";

    try (Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        String title = rs.getString("title");
        String left = rs.getString("left_text");
        String right = rs.getString("right_text");
        list.add(new DiffData(title, left, right));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean saveDiff(DiffData data) {
    String sql = "INSERT INTO diff_tabs (title, left_text, right_text) VALUES (?, ?, ?)";
    try (Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, data.title);
      stmt.setString(2, data.leftText);
      stmt.setString(3, data.rightText);
      stmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
