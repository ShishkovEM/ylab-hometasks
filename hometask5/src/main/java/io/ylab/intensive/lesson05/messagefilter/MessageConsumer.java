package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MessageConsumer extends DefaultConsumer {
    private final Channel channel;
    private final String inputQueue;
    private final BadWordsRepository badWordsRepository;
    private final MessageProducer messageProducer;

    @Autowired
    public MessageConsumer(Channel channel, String inputQueue, BadWordsRepository badWordsRepository, MessageProducer messageProducer) {
        super(channel);
        this.channel = channel;
        this.inputQueue = inputQueue;
        this.badWordsRepository = badWordsRepository;
        this.messageProducer = messageProducer;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" + message + "'");
        String filteredMessage = message;
        String[] originalMessage = message.split("[ ,.;!?\\n\\r]");
        String[] messageInLowerCase = new String[originalMessage.length];

        for (int i = 0; i < originalMessage.length; i++) {
            messageInLowerCase[i] = originalMessage[i].toLowerCase();
        }

        for (int i = 0; i < messageInLowerCase.length; i++) {
            if (badWordsRepository.wordExists(messageInLowerCase[i])) {
                filteredMessage = filteredMessage.replace(originalMessage[i], filterWord(originalMessage[i]));
            }
        }

        messageProducer.sendMessage(filteredMessage);
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

    public void listenQueue() throws IOException, InterruptedException {
        this.channel.basicConsume(inputQueue, false, this);
        while (true) {
            Thread.sleep(1000);
        }
    }

    private static String filterWord(String word) {
        return word.charAt(0) +
               "*".repeat(Math.max(0, word.length() - 2)) +
               word.charAt(word.length() - 1);
    }

}