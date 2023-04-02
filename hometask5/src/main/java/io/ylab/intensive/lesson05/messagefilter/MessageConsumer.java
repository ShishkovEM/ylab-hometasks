package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public MessageConsumer(@Qualifier("messageFilter") Channel channel, String inputQueue, BadWordsRepository badWordsRepository, MessageProducer messageProducer) {
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

        for (String word : message.split("[ ,.;!?\\n\\r]")) {
            if (badWordsRepository.wordExists(word.toLowerCase())) {
                filteredMessage = filteredMessage.replace(word, filterWord(word));
            }
        }

        messageProducer.sendMessage(filteredMessage);
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

    public void listenAndServe() throws IOException, InterruptedException {
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