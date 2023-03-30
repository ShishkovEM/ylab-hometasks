package io.ylab.intensive.lesson04.eventsourcing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PersonMessageProducer {
    private static final String SAVE_PERSON = "SAVE_PERSON";
    private static final String DELETE_PERSON = "DELETE_PERSON";
    private static final String QUEUE_NAME = "person_queue";
    private final ConnectionFactory connectionFactory;

    public PersonMessageProducer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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

}
