package io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.sql.*;

public class FileSortUtils {

    private FileSortUtils() {
    }

    public static void loadFileToDatabaseBatched(Connection conn, File data, int batchSize, String insertQuery) throws SQLException, IOException {
        conn.setAutoCommit(false);
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        BufferedReader reader = new BufferedReader(new FileReader(data));
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            insertStmt.setLong(1, Long.parseLong(line));
            insertStmt.addBatch();
            if (++count % batchSize == 0) {
                insertStmt.executeBatch();
            }
        }
        if (count % batchSize != 0) {
            insertStmt.executeBatch();
        }
        insertStmt.close();
        reader.close();
        conn.commit();
    }

    public static void loadFileToDatabaseNoBatch(Connection conn, File data, String insertQuery) throws SQLException, IOException {
        conn.setAutoCommit(false);
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        BufferedReader reader = new BufferedReader(new FileReader(data));
        String line;
        while ((line = reader.readLine()) != null) {
            insertStmt.setLong(1, Long.parseLong(line));
            insertStmt.executeUpdate();
        }
        insertStmt.close();
        reader.close();
        conn.commit();
    }

    public static File sortInsideDatabase(Connection conn, String sortingQuery, String outputFilePath) throws SQLException, IOException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sortingQuery);
        File outputFile = new File(outputFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        while (rs.next()) {
            writer.write(rs.getLong("val") + "\n");
        }
        writer.close();
        rs.close();
        stmt.close();

        return outputFile;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // игнорируем
            }
        }
    }

    public static void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                // игнорируем
            }
        }
    }
}
