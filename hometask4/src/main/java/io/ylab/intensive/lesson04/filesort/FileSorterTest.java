package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;

import io.ylab.intensive.lesson04.DbUtil;

public class FileSorterTest {
  public static void main(String[] args) throws SQLException, IOException {
    DataSource dataSource = initDb();
    File data = new File("hometask4/src/main/resources/static/files/data.txt");
    long startTime = System.currentTimeMillis();
    FileSorter fileSorter = new FileSortImpl(dataSource);
    File res1 = fileSorter.sort(data);
    long endTime = System.currentTimeMillis();
    System.out.println("Время сортировки с использованием batch-processing: " + (endTime - startTime) + " ms");

    // Очищаем БД
    initDb();
    startTime = System.currentTimeMillis();
    fileSorter = new FileSortNoBatchImpl(dataSource);
    File res2 = fileSorter.sort(data);
    endTime = System.currentTimeMillis();
    System.out.println("Время сортировки без использования batch-processing: " + (endTime - startTime) + " ms");

    System.out.println("Контроль эквивалентности содержимого отсортированных файлов: " + FileUtils.contentEquals(res1, res2));
  }
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;" 
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }
}
