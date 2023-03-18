package hometask3.orgstructure;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrgStructureImpl implements OrgStructureParser {

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Map<Long, Employee> struct = new HashMap<>();
        String csvSplitter = ";";

        try (
                FileReader fr = new FileReader(csvFile);
                BufferedReader br = new BufferedReader(fr)
        ) {
            br.readLine();
            String line = br.readLine();

            while (line != null) {
                String[] tmp = line.split(csvSplitter);
                if (tmp.length != 4) {
                    throw new RuntimeException("incorrect file structure");
                }

                Employee employee = new Employee();
                employee.setId(Objects.equals(tmp[0], "") ? null : Long.parseLong(tmp[0]));
                employee.setBossId(Objects.equals(tmp[1], "") ? null : Long.parseLong(tmp[1]));
                employee.setName(Objects.equals(tmp[2], "") ? null : tmp[2]);
                employee.setPosition(Objects.equals(tmp[3], "") ? null : tmp[3]);
                struct.put(Long.parseLong(tmp[0]), employee);

                line = br.readLine();
            }

            this.setHierarchy(struct);
        }

        return searchBoss(struct);
    }

    /**
     * Метод добавляет ссылки в поля boss и subordaintate в соответствии со значениями bossId
     *
     * @param struct - структура вида Map<Long, Employee>
     */
    private void setHierarchy(Map<Long, Employee> struct) {
        struct.forEach((employeeId, currentEmployee) -> {
            if (currentEmployee.getBoss() == null && currentEmployee.getBossId() != null) {
                currentEmployee.setBoss(struct.get(currentEmployee.getBossId()));
                struct.get(currentEmployee.getBossId()).getSubordinate().add(currentEmployee);
            }
        });
    }

    private Employee searchBoss(Map<Long, Employee> struct) {
        for (Employee employee : struct.values()) {
            if (employee.getBossId() == null) {
                return employee;
            }
        }
        throw new RuntimeException("no boss found");
    }
}
