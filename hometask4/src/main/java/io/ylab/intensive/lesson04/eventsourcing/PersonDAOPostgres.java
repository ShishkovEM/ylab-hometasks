package io.ylab.intensive.lesson04.eventsourcing;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonDAOPostgres {
    private final DataSource dataSource;

    public PersonDAOPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Person findPerson(Long personId) {
        try (java.sql.Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM person WHERE person_id = ?")) {
                statement.setLong(1, personId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String middleName = resultSet.getString("middle_name");
                        return new Person(personId, firstName, lastName, middleName);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while finding person: " + e.getMessage());
            return null;
        }
    }

    public List<Person> findAll() {
        try (java.sql.Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM person")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Person> people = new ArrayList<>();
                    while (resultSet.next()) {
                        Long personId = resultSet.getLong("person_id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String middleName = resultSet.getString("middle_name");
                        people.add(new Person(personId, firstName, lastName, middleName));
                    }
                    return people;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while finding all people: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
