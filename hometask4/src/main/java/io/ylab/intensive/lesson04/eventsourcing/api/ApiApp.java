package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    PersonApi personApi = new PersonApiImpl(connectionFactory);

    // Delete person with id 1
    personApi.deletePerson(1L);

    // Save person with id 2
    personApi.savePerson(2L, "John", "Doe", "Smith");

    // Find person with id 2
    Person person = personApi.findPerson(2L);
    if (person != null) {
      System.out.println(person.getName() + " " + person.getLastName() + " " + person.getMiddleName());
    } else {
      System.out.println("Person not found");
    }

    // Find all persons
    List<Person> personList = personApi.findAll();
    if (!personList.isEmpty()) {
      for (Person p : personList) {
        System.out.println(p.getId() + " " + p.getName() + " " + p.getLastName() + " " + p.getMiddleName());
      }
    } else {
      System.out.println("No persons found");
    }
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
