package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.eventsourcing.Person;

/**
 * Формат сообщений:
 *
 * Для сохранения данных о персоне:
 * Тип сообщения: "SAVE_PERSON"
 * Содержимое сообщения: personId, firstName, lastName, middleName
 *
 * Для удаления данных о персоне:
 * Тип сообщения: "DELETE_PERSON"
 * Содержимое сообщения: personId
 *
 * Для поиска данных о персоне:
 * Тип сообщения: "FIND_PERSON"
 * Содержимое сообщения: personId
 * В ответ на это сообщение обработчик должен отправить сообщение с найденными данными или сообщение "NOT_FOUND", если персона с таким personId не найдена.
 *
 * Для поиска всех сохраненных в базе персон:
 * Тип сообщения: "FIND_ALL"
 * Содержимое сообщения: нет
 * В ответ на это сообщение обработчик должен отправить сообщение с найденными данными или сообщение "NOT_FOUND", если ни одна персона не была сохранена в базе.
 */
public class PersonApiImpl implements PersonApi {
  private static final String SAVE_PERSON = "SAVE_PERSON";
  private static final String DELETE_PERSON = "DELETE_PERSON";
  private static final String FIND_PERSON = "FIND_PERSON";
  private static final String FIND_ALL = "FIND_ALL";
  private static final String QUEUE_NAME = "person_queue";

  private final ConnectionFactory connectionFactory;

  public PersonApiImpl(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
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

  @Override
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

  @Override
  public Person findPerson(Long personId) {
    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      String message = FIND_PERSON + "," + personId.toString();
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
      System.out.println(" [x] Sent '" + message + "'");

      GetResponse response = channel.basicGet(QUEUE_NAME, true);
      if (response == null) {
        return null;
      }

      String responseBody = new String(response.getBody(), "UTF-8");
      if (responseBody.equals("NOT_FOUND")) {
        return null;
      }

      String[] parts = responseBody.split(",");
      Long id = Long.parseLong(parts[0]);
      String firstName = parts[1];
      String lastName = parts[2];
      String middleName = parts[3];

      return new Person(id, firstName, lastName, middleName);
    } catch (IOException | TimeoutException e) {
      System.out.println("Error while sending find person message: " + e.getMessage());
      return null;
    }
  }

  @Override
  public List<Person> findAll() {
    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      String message = FIND_ALL;
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
      System.out.println(" [x] Sent '" + message + "'");
      GetResponse response = channel.basicGet(QUEUE_NAME, true);
      if (response == null) {
        System.out.println("No response received");
        return Collections.emptyList();
      } else {
        String responseBody = new String(response.getBody(), "UTF-8");
        System.out.println("Received response: " + responseBody);
        if (responseBody.equals("NOT_FOUND")) {
          return Collections.emptyList();
        } else {
          List<Person> people = new ArrayList<>();
          String[] personData = responseBody.split(",");
          for (int i = 0; i < personData.length; i += 4) {
            Long id = Long.valueOf(personData[i]);
            String firstName = personData[i + 1];
            String lastName = personData[i + 2];
            String middleName = personData[i + 3];
            people.add(new Person(id, firstName, lastName, middleName));
          }
          return people;
        }
      }
    } catch (IOException | TimeoutException e) {
      System.out.println("Error while sending find all message: " + e.getMessage());
      return Collections.emptyList();
    }
  }
}
