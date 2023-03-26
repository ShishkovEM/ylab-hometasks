package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    persistentMap.init("my_map");
    persistentMap.put("foo", "bar");
    persistentMap.put("hello", "world");
    System.out.println(persistentMap.containsKey("foo")); // true
    System.out.println(persistentMap.containsKey("bar")); // false
    System.out.println(persistentMap.containsKey("hello")); // true
    System.out.println(persistentMap.containsKey("world")); // false
    System.out.println(persistentMap.get("foo")); // bar
    System.out.println(persistentMap.get("bar")); // null
    System.out.println(persistentMap.get("hello")); // world
    System.out.println(persistentMap.get("world")); // null
    System.out.println(persistentMap.getKeys()); // [foo, hello]
    persistentMap.remove("foo");
    System.out.println(persistentMap.containsKey("foo")); // false
    persistentMap.clear();
    System.out.println(persistentMap.getKeys()); // []
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
