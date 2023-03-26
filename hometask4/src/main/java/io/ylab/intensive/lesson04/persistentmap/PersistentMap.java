package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import java.util.List;

public interface PersistentMap {

  /**
   * Метод используется для инициализации нового экземпляра Map. Принимает имя
   * текущего экземпляра. Данные всех экземпляров хранятся в одной таблице, и имя
   * используется для того, чтобы отделять данные одного экземпляра от данных другого
   * @param name - имя экземпляра Map
   */
  void init(String name);

  /**
   * Возвращает true тогда и только тогда, когда существует значение,
   * связанное с данным ключом, false - в противном случае
   * @param key - ключ
   * @return true тогда и только тогда, когда существует значение, связанное с данным ключом
   * @throws SQLException
   */
  boolean containsKey(String key) throws SQLException;

  /**
   * Возвращает список ключей, для которых есть значения в БД
   * @return список ключей, для которых есть значения в БД
   * @throws SQLException
   */
  List<String> getKeys() throws SQLException;

  /**
   * Возвращает значение, связанное с переданным ключом
   * @param key - переданный ключ
   * @return значение, связанное с переданным ключом
   * @throws SQLException
   */
  String get(String key) throws SQLException;

  /**
   * Удаляет пару ключ/значение из Map
   * @param key - переданный ключ
   * @throws SQLException
   */
  void remove(String key) throws SQLException;

  /**
   * Служит для добавления новой пары ключ-значение. В своей работе сначала
   * удаляет существую пару из Map (если она есть), а затем добавляет новую
   * @param key - переданный ключ
   * @param value - переданное значение
   * @throws SQLException
   */
  void put(String key, String value) throws SQLException;

  /**
   * Удаляет все данные из текущего экземпляра Map
   * @throws SQLException
   */
  void clear() throws SQLException;
}
