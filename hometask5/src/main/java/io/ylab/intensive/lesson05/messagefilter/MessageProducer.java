package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class MessageProducer {
    private final ConnectionFactory connectionFactory;
    private final String outputQueue;

    public MessageProducer(ConnectionFactory connectionFactory, String outputQueue) {
        this.connectionFactory = connectionFactory;
        this.outputQueue = outputQueue;
    }

    public void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(outputQueue, false, false, false, null);
            channel.basicPublish("", outputQueue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            System.out.println("Error while sending message: " + e.getMessage());
        }
    }
}
