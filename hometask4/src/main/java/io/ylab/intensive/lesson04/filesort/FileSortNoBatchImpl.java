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

public class FileSortNoBatchImpl implements FileSorter {
    private DataSource dataSource;

    public FileSortNoBatchImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        Connection conn = null;
        try {
            // Загружаем данные в БД
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO numbers (val) VALUES (?)");
            BufferedReader reader = new BufferedReader(new FileReader(data));
            String line = null;
            while ((line = reader.readLine()) != null) {
                insertStmt.setLong(1, Long.parseLong(line));
                insertStmt.executeUpdate();
            }
            insertStmt.close();
            reader.close();
            conn.commit();

            // Sort data in database
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT val FROM numbers ORDER BY val ASC");
            File outputFile = new File("hometask4/src/main/resources/static/files/sortedNoBatch.txt");
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
