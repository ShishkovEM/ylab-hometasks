package io.ylab.intensive.lesson04.eventsourcing.api;

import java.util.List;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.PersonDAO;

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
 */
public class PersonApiImpl implements PersonApi {
  private final PersonDAO personDAO;

  public PersonApiImpl(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  @Override
  public void deletePerson(Long personId) {
    personDAO.deletePerson(personId);
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {
    personDAO.savePerson(personId, firstName, lastName, middleName);
  }

  @Override
  public Person findPerson(Long personId) {
    return personDAO.findPerson(personId);
  }

  @Override
  public List<Person> findAll() {
    return personDAO.findAll();
  }
}
