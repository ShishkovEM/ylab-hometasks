package io.ylab.intensive.lesson05.eventsourcing.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import javax.sql.DataSource;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.eventsourcing.PersonMessageConsumer;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  @Qualifier("personChannel")
  public DataSource dataSource() throws SQLException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);

    String ddl = ""
                     + "drop table if exists person;"
                     + "create table if not exists person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DbUtil.applyDdl(ddl, dataSource);
    
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
  public PersonMessageConsumer personMessageConsumer(DataSource dataSource) {
    return new PersonMessageConsumer(createChannel(), dataSource, queue());
  }

  @Bean
  @Qualifier("personChannel")
  public Channel createChannel() {
    try {
      return connectionFactory().newConnection().createChannel();
    } catch (IOException | TimeoutException e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  @Qualifier("personChannel")
  public String queue() {
    return "person_queue";
  }
  
}
