package hometask3.orgstructure;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {

    /**
     * Метод parseStructure должен считывать данные из файла и возвращать ссылку на
     * Босса (Генерального директора) - сотрудника, атрибут boss_id которого не задан.
     * Cчитать, что такой сотрудник в файле ровно один.
     *
     * @param csvFile - входной файл
     * @return ссылку на Босса (Генерального директора) - сотрудника, атрибут boss_id которого не задан
     * @throws IOException
     */
    Employee parseStructure(File csvFile) throws IOException;

}