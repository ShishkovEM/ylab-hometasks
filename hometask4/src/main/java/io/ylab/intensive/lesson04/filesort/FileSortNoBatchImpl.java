package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class FileSortNoBatchImpl implements FileSorter {
    private static final String INSERT_QUERY_STRING = "INSERT INTO numbers (val) VALUES (?)";
    private static final String SORTING_QUERY = "SELECT val FROM numbers ORDER BY val ASC";
    private static final String OUTPUT_FILE_PATH = "hometask4/src/main/resources/static/files/sortedNoBatch.txt";
    private final DataSource dataSource;

    public FileSortNoBatchImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            FileSortUtils.loadFileToDatabaseNoBatch(conn, data, INSERT_QUERY_STRING);

            return FileSortUtils.sortInsideDatabase(conn, SORTING_QUERY, OUTPUT_FILE_PATH);
        } catch (SQLException | IOException e) {
            FileSortUtils.rollbackConnection(conn);
            throw new RuntimeException("Error sorting file: " + e.getMessage(), e);
        } finally {
            FileSortUtils.closeConnection(conn);
        }
    }

}
