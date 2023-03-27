package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.SQLException;
import javax.sql.DataSource;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.PersonMessageConsumer;

public class DbApp {
  private static final String QUEUE_NAME_FOR_CONSUMING = "person_queue";

  public static void main(String[] args) throws Exception {
    DataSource dataSource = initDb();
    ConnectionFactory connectionFactory = initMQ();

    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {

      // Создаем консьюмер для чтения сообщений из очереди
      PersonMessageConsumer personMessageConsumer = new PersonMessageConsumer(channel, dataSource);

      channel.basicConsume(QUEUE_NAME_FOR_CONSUMING, false, personMessageConsumer);

      // Бесконечный цикл для ожидания сообщений
      while (true) {
        Thread.sleep(1000);
      }
    }
  }
  
  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
  
  private static DataSource initDb() throws SQLException {
    String ddl = "" 
                     + "drop table if exists person;\n"
                     + "create table if not exists person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(ddl, dataSource);
    return dataSource;
  }
}
