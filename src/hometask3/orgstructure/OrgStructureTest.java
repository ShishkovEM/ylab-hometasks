package hometask3.orgstructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureTest {
    static String csvFilePath = "./src/hometask3/orgstructure/structure.csv";

    public static void main(String[] args) {
        try {
            File file = new File(csvFilePath);
            if (!file.isFile()) {
                throw new IOException("file not specified");
            }

            OrgStructureParser parser = new OrgStructureImpl();

            Employee boss = parser.parseStructure(file);

            System.out.println(boss); // Employee{id=1, bossId=null, name='Иван Иванович', position='Генеральный директор'}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
