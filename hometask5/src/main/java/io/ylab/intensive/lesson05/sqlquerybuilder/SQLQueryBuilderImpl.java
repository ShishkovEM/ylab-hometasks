package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

   private final DataSource dataSource;

   @Autowired
   public SQLQueryBuilderImpl(DataSource dataSource) {
       this.dataSource = dataSource;
   }

    @Override
    public String queryForTable(String tableName) {
        try (Connection conn = dataSource.getConnection()) {
            // проверяем, что таблица существует
            if (!tableExists(conn, tableName)) {
                return null;
            }
            // получаем список колонок
            List<String> columns = getColumns(conn, tableName);
            // составляем строку запроса
            StringBuilder query = new StringBuilder("SELECT ");
            for (String column : columns) {
                query.append(column).append(", ");
            }
            query.delete(query.length() - 2, query.length()); // удаляем последнюю запятую
            query.append(" FROM ").append(tableName);
            return query.toString();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query table " + tableName, e);
        }
    }

    @Override
    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, new String[] {"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get list of tables", e);
        }
        return tables;
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(null, null, tableName, null);
        return rs.next();
    }

    private List<String> getColumns(Connection conn, String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(null, null, tableName, null);
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        return columns;
    }
}