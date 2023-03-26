package io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать 
 */
public class PersistentMapImpl implements PersistentMap {
  
  private DataSource dataSource;
  private String name;

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    this.name = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT value FROM persistent_map WHERE map_name = ? AND key = ?")) {
      stmt.setString(1, name);
      stmt.setString(2, key);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    }
  }

  @Override
  public List<String> getKeys() throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT key FROM persistent_map WHERE map_name = ?")) {
      stmt.setString(1, name);
      try (ResultSet rs = stmt.executeQuery()) {
        List<String> keys = new ArrayList<>();
        while (rs.next()) {
          keys.add(rs.getString("key"));
        }
        return keys;
      }
    }
  }

  @Override
  public String get(String key) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT value FROM persistent_map WHERE map_name = ? AND key = ?")) {
      stmt.setString(1, name);
      stmt.setString(2, key);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getString("value");
        } else {
          return null;
        }
      }
    }
  }

  @Override
  public void remove(String key) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM persistent_map WHERE map_name = ? AND key = ?")) {
      stmt.setString(1, name);
      stmt.setString(2, key);
      stmt.executeUpdate();
    }
  }

  @Override
  public void put(String key, String value) throws SQLException {
    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      try {
        try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM persistent_map WHERE map_name = ? AND key = ?")) {
          stmt1.setString(1, name);
          stmt1.setString(2, key);
          stmt1.executeUpdate();
        }
        try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)")) {
          stmt2.setString(1, name);
          stmt2.setString(2, key);
          stmt2.setString(3, value);
          stmt2.executeUpdate();
        }
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }
    }
  }

  @Override
  public void clear() throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM persistent_map WHERE map_name = ?")) {
      stmt.setString(1, name);
      stmt.executeUpdate();
    }
  }
}
