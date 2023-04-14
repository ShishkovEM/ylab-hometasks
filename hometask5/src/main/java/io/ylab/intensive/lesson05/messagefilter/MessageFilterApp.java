package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class MessageFilterApp {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    // Инициализируем репозиторий для справочника фильтруемых слов
    BadWordsRepository badWordsRepository = applicationContext.getBean(BadWordsRepository.class);
    badWordsRepository.createTableIfNotExists();
    badWordsRepository.loadWordsFromFile();

    MessageConsumer messageConsumer = applicationContext.getBean(MessageConsumer.class);
    try {
      messageConsumer.listenAndServe();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
}
