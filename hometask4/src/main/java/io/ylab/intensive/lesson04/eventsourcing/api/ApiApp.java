package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.PersonDAO;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    PersonDAO personDAO = new PersonDAO(connectionFactory, null);
    PersonApi personApi = new PersonApiImpl(personDAO);

    // Save person with id 1
    personApi.savePerson(1L, "John", "Doe", "Smith");

    // Save person with id 2
    personApi.savePerson(2L, "Evgeny", "Shishkov", "Mikhailovitch");

    // Save person with id 3
    personApi.savePerson(3L, "Ivan", "Ivanov", "Ivanovitch");

    // Delete person with id 1
    personApi.deletePerson(1L);

  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
