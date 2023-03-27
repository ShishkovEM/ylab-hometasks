package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.PersonDAOPostgres;
import io.ylab.intensive.lesson04.eventsourcing.PersonMessageProducer;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    DataSource dataSource = initDb();

    PersonMessageProducer personMessageProducer = new PersonMessageProducer(connectionFactory);
    PersonDAOPostgres personDAOPostgres = new PersonDAOPostgres(dataSource);

    PersonApi personApi = new PersonApiImpl(personMessageProducer, personDAOPostgres);

    // Save person with id 1
    personApi.savePerson(1L, "John", "Doe", "Smith"); // ApiApp <-- [x] Sent 'SAVE_PERSON,1,John,Doe,Smith'

    // Save person with id 2
    personApi.savePerson(2L, "Evgeny", "Shishkov", "Mikhailovitch"); // ApiApp <-- [x] Sent 'SAVE_PERSON,2,Evgeny,Shishkov,Mikhailovitch'

    // Save person with id 3
    personApi.savePerson(3L, "Ivan", "Ivanov", "Ivanovitch"); // ApiApp <-- [x] Sent 'SAVE_PERSON,3,Ivan,Ivanov,Ivanovitch'

    // Delete person with id 1
    personApi.deletePerson(1L); // ApiApp <-- [x] Sent 'DELETE_PERSON,1'

    // Delete person with id 1
    personApi.deletePerson(1L); // ApiApp <-- [x] Sent 'DELETE_PERSON,1'
                                        // DbApp <-- Tried to delete person with ID 1 but not found in DB

    // Find person with id 1
    System.out.println(personApi.findPerson(2L)); // null

    // Find person with id 2
    System.out.println(personApi.findPerson(2L)); // Person{id=2, name='Evgeny', lastName='Shishkov', middleName='Mikhailovitch'}

    // Find person with id 3
    System.out.println(personApi.findPerson(3L)); // Person{id=3, name='Ivan', lastName='Ivanov', middleName='Ivanovitch'}

    // Find all
    System.out.println(personApi.findAll().toString()); // [Person{id=2, name='Evgeny', lastName='Shishkov', middleName='Mikhailovitch'}, Person{id=3, name='Ivan', lastName='Ivanov', middleName='Ivanovitch'}]

    // Save person with id 3
    personApi.savePerson(3L, "Petrov", "Petr", "Pertrovich"); // ApiApp <-- [x] Sent 'SAVE_PERSON,3,Petrov,Petr,Petrovich'

    Thread.sleep(100);

    // Find person with id 3
    System.out.println(personApi.findPerson(3L)); // Person{id=3, name='Petrov', lastName='Petr', middleName='Pertrovich'}
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }

  private static DataSource initDb() throws SQLException {
    return DbUtil.buildDataSource();
  }
}
