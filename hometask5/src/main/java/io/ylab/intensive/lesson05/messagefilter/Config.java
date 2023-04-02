package io.ylab.intensive.lesson05.messagefilter;

import javax.sql.DataSource;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {
  
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
  public String tableName() {
    return "forbidden_words";
  }

  @Bean
  public String ddl() {
    return "CREATE TABLE IF NOT EXISTS " + tableName() + "(val VARCHAR PRIMARY KEY)";
  }

  @Bean
  public String sourcePath() {
    return "hometask5/src/main/java/io/ylab/intensive/lesson05/messagefilter/files/blackList.txt";
  }

  @Bean
  public String inputQueue() {
    return "input";
  }

  @Bean
  public String outputQueue() {
    return "output";
  }

  @Bean
  @Qualifier("messageFilter")
  public Channel createChannel() {
    try {
      return connectionFactory().newConnection().createChannel();
    } catch (IOException | TimeoutException e) {
      throw new RuntimeException(e);
    }
  }

}
