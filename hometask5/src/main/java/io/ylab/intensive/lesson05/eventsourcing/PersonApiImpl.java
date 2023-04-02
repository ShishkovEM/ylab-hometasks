package io.ylab.intensive.lesson05.eventsourcing;

import io.ylab.intensive.lesson05.eventsourcing.api.PersonApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("personApi")
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
