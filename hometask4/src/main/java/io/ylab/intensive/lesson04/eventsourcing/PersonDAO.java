package io.ylab.intensive.lesson04.eventsourcing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class PersonDAO {
    private static final String SAVE_PERSON = "SAVE_PERSON";
    private static final String DELETE_PERSON = "DELETE_PERSON";
    private static final String QUEUE_NAME = "person_queue";
    private final ConnectionFactory connectionFactory;
    private final DataSource dataSource;

    public PersonDAO(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    public void deletePerson(Long personId) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = DELETE_PERSON + "," + personId.toString();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            System.out.println("Error while sending delete person message: " + e.getMessage());
        }
    }

    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = SAVE_PERSON + "," + personId.toString() + "," + firstName + "," + lastName + "," + middleName;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            System.out.println("Error while sending save person message: " + e.getMessage());
        }
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
