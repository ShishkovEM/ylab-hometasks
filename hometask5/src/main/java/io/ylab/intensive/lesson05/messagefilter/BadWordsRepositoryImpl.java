package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Component
public class BadWordsRepositoryImpl implements BadWordsRepository {
    private final static int BATCH_SIZE = 100;
    private final DataSource dataSource;
    private final String tableName;
    private final String ddl;
    private final String sourcePath;

    @Autowired
    public BadWordsRepositoryImpl(DataSource datasource, String tableName, String ddl, String sourcePath) {
        this.dataSource = datasource;
        this.tableName = tableName;
        this.ddl = ddl;
        this.sourcePath = sourcePath;
    }

    @Override
    public void createTableIfNotExists() {
        try (Connection conn = dataSource.getConnection()) {
            if (!tableExists(conn, tableName)) {
                applyDdl(ddl, dataSource);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating bad words table: ", e);
        }
    }

    @Override
    public void loadWordsFromFile() {
        try (Connection conn = dataSource.getConnection()) {
            File data = getFile();
            wipeTable();
            conn.setAutoCommit(false);
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO " + tableName + " (val) VALUES (?)");
            BufferedReader reader = new BufferedReader(new FileReader(data));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                insertStmt.setString(1, line);
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
        } catch (SQLException | IOException e) {
            throw new RuntimeException("can't load words to DB: " + e);
        }
    }

    @Override
    public boolean wordExists(String word) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM " + tableName + " WHERE val = ?")) {
            boolean result;
            stmt.setString(1, word.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt(1) > 0;
            rs.close();
            return result;
        } catch (SQLException ex) {
            throw new RuntimeException("Error checking if word exists in DB", ex);
        }
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        boolean result;
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(null, null, tableName, null);
        result = rs.next();
        rs.close();
        return result;
    }

    private void applyDdl(String ddl, DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        }
    }

    private File getFile() {
        return new File(sourcePath);
    }

    private void wipeTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE " + tableName);
        }
    }
}
