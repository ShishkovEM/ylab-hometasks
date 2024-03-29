package io.ylab.intensive.lesson05.eventsourcing.api;

import javax.sql.DataSource;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.PersonApiImpl;
import io.ylab.intensive.lesson05.eventsourcing.PersonDAOPostgres;
import io.ylab.intensive.lesson05.eventsourcing.PersonMessageProducer;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  
  @Bean
  public DataSource dataSource() {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);
    return dataSource;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setPort(5672);
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setVirtualHost("/");
    return connectionFactory;
  }

  @Bean
  public PersonMessageProducer personMessageProducer(ConnectionFactory connectionFactory) {
    return new PersonMessageProducer(connectionFactory);
  }

  @Bean
  public PersonApi personApi(PersonMessageProducer personMessageProducer, PersonDAOPostgres personDAOPostgres) {
    return new PersonApiImpl(personMessageProducer, personDAOPostgres);
  }

  @Bean
  public PersonDAOPostgres personDAOPostgres(DataSource dataSource) {
    return new PersonDAOPostgres(dataSource);
  }

}
