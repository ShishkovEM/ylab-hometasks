package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.PersonMessageConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    PersonMessageConsumer personMessageConsumer = applicationContext.getBean(PersonMessageConsumer.class);

    personMessageConsumer.listenAndServe();

  }
}
