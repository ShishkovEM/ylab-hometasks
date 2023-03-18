package hometask3.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс реализует хранение пар "ключ-значение" для строк.
 * Пары хранятся в private-поле primaryMap, которое реализует интерфейс Map.
 * Даты добавления значений хранятся во вспомогательном поле dateMap.
 */
public class DatedMapImpl implements DatedMap {
    private final Map<String, String> primaryMap;
    private final Map<String, Date> dateMap;

    public DatedMapImpl() {
        this.primaryMap = new HashMap<>();
        this.dateMap = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        this.primaryMap.put(key, value);
        this.dateMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return this.primaryMap.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return this.primaryMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        this.primaryMap.remove(key);
        this.dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return this.primaryMap.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return this.dateMap.get(key);
    }
}
