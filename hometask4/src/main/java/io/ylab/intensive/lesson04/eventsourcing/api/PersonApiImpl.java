package io.ylab.intensive.lesson04.eventsourcing.api;

import java.util.List;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.PersonDAOPostgres;
import io.ylab.intensive.lesson04.eventsourcing.PersonMessageProducer;

/**
 * Формат сообщений для брокера (методы: deletePerson(Long personId) и
 * savePerson(Long personId, String firstName, String lastName, String middleName)):
 *
 * Для сохранения данных о персоне:
 * Тип сообщения: "SAVE_PERSON"
 * Содержимое сообщения: personId, firstName, lastName, middleName
 *
 * Для удаления данных о персоне:
 * Тип сообщения: "DELETE_PERSON"
 * Содержимое сообщения: personId
 */
public class PersonApiImpl implements PersonApi {
  private final PersonMessageProducer personMessageProducer;
  private final PersonDAOPostgres personDAOPostgres;

  public PersonApiImpl(PersonMessageProducer personMessageProducer, PersonDAOPostgres personDAOPostgres) {
    this.personMessageProducer = personMessageProducer;
    this.personDAOPostgres = personDAOPostgres;
  }

  @Override
  public void deletePerson(Long personId) {
    personMessageProducer.deletePerson(personId);
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {
    personMessageProducer.savePerson(personId, firstName, lastName, middleName);
  }

  @Override
  public Person findPerson(Long personId) {
    return personDAOPostgres.findPerson(personId);
  }

  @Override
  public List<Person> findAll() {
    return personDAOPostgres.findAll();
  }
}
