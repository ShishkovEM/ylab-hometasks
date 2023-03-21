package hometask3.transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorDictionary {
    private final Map<Character, String> dictionary;

    /**
     * Возвращает инстанс словаря транслитерации символов кириллицы в латиницу
     */
    public TransliteratorDictionary() {
        this.dictionary = new HashMap<>();
        this.dictionary.put('А', "A");
        this.dictionary.put('Б', "B");
        this.dictionary.put('В', "V");
        this.dictionary.put('Г', "G");
        this.dictionary.put('Д', "D");
        this.dictionary.put('Е', "E");
        this.dictionary.put('Ё', "E");
        this.dictionary.put('Ж', "ZH");
        this.dictionary.put('З', "Z");
        this.dictionary.put('И', "I");
        this.dictionary.put('Й', "I");
        this.dictionary.put('К', "K");
        this.dictionary.put('Л', "L");
        this.dictionary.put('М', "M");
        this.dictionary.put('Н', "N");
        this.dictionary.put('О', "O");
        this.dictionary.put('П', "P");
        this.dictionary.put('Р', "R");
        this.dictionary.put('С', "S");
        this.dictionary.put('Т', "T");
        this.dictionary.put('У', "U");
        this.dictionary.put('Ф', "F");
        this.dictionary.put('Х', "KH");
        this.dictionary.put('Ц', "TS");
        this.dictionary.put('Ч', "CH");
        this.dictionary.put('Ш', "SH");
        this.dictionary.put('Щ', "SHCH");
        this.dictionary.put('Ы', "Y");
        this.dictionary.put('Ь', "");
        this.dictionary.put('Ъ', "IE");
        this.dictionary.put('Э', "E");
        this.dictionary.put('Ю', "IU");
        this.dictionary.put('Я', "IA");
    }

    /**
     * Проверяет, содержится в словаре передаваемый в метод символ.
     *
     * @param key - проверяемый символ
     * @return true, если ключ в словаре присутствует, false - если отсутствует
     */
    public boolean containsKey(Character key) {
        return this.dictionary.containsKey(key);
    }

    /**
     * Возвращает строку, соответствующую передаваемому в метод символу-ключу.
     * Если символ-ключ отсутствует в словаре - возвращается пустая строка.
     *
     * @param key - символ-ключ словаря
     * @return соответствующее ключу строковое значение
     */
    public String getValue(Character key) {
        return this.dictionary.getOrDefault(key, "");
    }
}
