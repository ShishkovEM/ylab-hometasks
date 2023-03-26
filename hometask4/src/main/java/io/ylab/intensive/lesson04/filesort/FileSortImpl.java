package io.ylab.intensive.lesson04.filesort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
  private static final int BATCH_SIZE = 1000;
  private DataSource dataSource;

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) {
    Connection conn = null;
    try {
      // Загружаем содержимое файла в БД
      conn = dataSource.getConnection();
      conn.setAutoCommit(false);
      PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO numbers (val) VALUES (?)");
      BufferedReader reader = new BufferedReader(new FileReader(data));
      String line;
      int count = 0;
      while ((line = reader.readLine()) != null) {
        insertStmt.setLong(1, Long.parseLong(line));
        insertStmt.addBatch();
        if (++count % BATCH_SIZE == 0) {
          insertStmt.executeBatch();
        }
      }
      if (count % BATCH_SIZE != 0) {
        insertStmt.executeBatch();
      }
      insertStmt.close();
      reader.close();
      conn.commit();

      // Сортируем содержимое в БД
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT val FROM numbers ORDER BY val ASC");
      File outputFile = new File("hometask4/src/main/resources/static/files/sortedWithBatch.txt");
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
      while (rs.next()) {
        writer.write(rs.getLong("val") + "\n");
      }
      writer.close();
      rs.close();
      stmt.close();

      return outputFile;
    } catch (SQLException | IOException e) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException ex) {
          // игнорируем
        }
      }
      throw new RuntimeException("Error sorting file: " + e.getMessage(), e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          // игнорируем
        }
      }
    }
  }
}
