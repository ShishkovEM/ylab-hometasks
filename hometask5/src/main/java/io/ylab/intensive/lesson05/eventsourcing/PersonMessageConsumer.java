package io.ylab.intensive.lesson05.eventsourcing;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class PersonMessageConsumer extends DefaultConsumer {
    private static final String INSERT_QUERY =
            "INSERT INTO person (person_id, first_name, last_name, middle_name) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (person_id) DO UPDATE SET first_name = ?, last_name = ?, middle_name = ?";
    private static final String DELETE_QUERY = "DELETE FROM person WHERE person_id = ?";

    private final Channel channel;

    private final String personQueue;

    private final DataSource dataSource;

    @Autowired
    public PersonMessageConsumer(@Qualifier("personChannel") Channel channel,
                                 @Qualifier("personChannel") DataSource dataSource,
                                 @Qualifier("personChannel") String personQueue) {
        super(channel);
        this.channel = channel;
        this.dataSource = dataSource;
        this.personQueue = personQueue;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, StandardCharsets.UTF_8);
        String[] parts = message.split(",");
        String command = parts[0];
        Long personId = Long.parseLong(parts[1]);

        try (Connection dbConnection = dataSource.getConnection()) {
            switch (command) {
                case "SAVE_PERSON":
                    performSavePerson(parts, personId, dbConnection);
                    break;
                case "DELETE_PERSON":
                    performDeletion(personId, dbConnection);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Error while executing DB query: " + e.getMessage());
        } finally {
            getChannel().basicAck(envelope.getDeliveryTag(), false);
        }
    }

    public void listenAndServe() throws IOException, InterruptedException {
        this.channel.basicConsume(personQueue, false, this);
        while (true) {
            Thread.sleep(1000);
        }
    }

    private void performSavePerson(String[] parts, Long personId, Connection dbConnection) throws SQLException {
        String firstName = parts[2];
        String lastName = parts[3];
        String middleName = parts[4];

        PreparedStatement saveStatement = dbConnection.prepareStatement(INSERT_QUERY);
        saveStatement.setLong(1, personId);
        saveStatement.setString(2, firstName);
        saveStatement.setString(3, lastName);
        saveStatement.setString(4, middleName);
        saveStatement.setString(5, firstName);
        saveStatement.setString(6, lastName);
        saveStatement.setString(7, middleName);
        saveStatement.executeUpdate();
    }

    private void performDeletion(Long personId, Connection dbConnection) throws SQLException {
        PreparedStatement deleteStatement = dbConnection.prepareStatement(DELETE_QUERY);
        deleteStatement.setLong(1, personId);
        int rowsAffected = deleteStatement.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("Tried to delete person with ID " + personId + " but not found in DB");
        }
    }
}
