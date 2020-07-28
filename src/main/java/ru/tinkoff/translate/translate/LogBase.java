package ru.tinkoff.translate.translate;

import java.sql.*;
import java.util.ArrayList;

/**
 * Класс {@code LogBase} обеспечивает взаимодействие приложения с базой данных данных пользовательских запросов
 */
public class LogBase
{
   /**
    * Путь к базе данных
    */
   private static final String path = "tinkoffTranlate/";

   /**
    * Название базы данных
    */
   private static final String dbName = "log";

   /**
    * Строка установления соединения
    */
   private static final String connectionString = "jdbc:hsqldb:file:" + path + dbName;

   /**
    * Логин
    */
   private static final String login = "admin";

   /**
    * Пароль
    */
   private static final String password = "admin";

   /**
    * Инициализация класса работы с базой
    * Процесс инициализации состоит из следующих этапов:
    * <ul>
    *    <li>Установка соединения с БД</li>
    *    <li>Проверка наличия таблицы с логами {@code LOGTABLE} в БД</li>
    *    <li>Если таблица {@code LOGTABLE} в БД уже существует, она удаляется</li>
    *    <li>Создание таблицы логов {@code LOGTABLE} в БД</li>
    *    <li>Закрытие соединения с БД</li>
    * </ul>
    */
   public LogBase()
   {
      try ( Connection connection = DriverManager.getConnection( connectionString, login, password );
            Statement statement = connection.createStatement() )
      {
         DatabaseMetaData meta = connection.getMetaData();
         ResultSet tables = meta.getTables( null, null, "LOGTABLE", null );

         if ( tables.next() )
         {
            String sql = "DROP TABLE LOGTABLE";
            statement.executeUpdate( sql );
         }

         tables.close();
         String sql = "CREATE TABLE LOGTABLE (id IDENTITY, time VARCHAR(23), ip VARCHAR(39), text VARCHAR(128), fromLang VARCHAR(3), toLang VARCHAR(3))";
         statement.executeUpdate( sql );
      }
      catch ( SQLException exception )
      {
         System.out.println( "ERROR! Cannot initialize database!" );
         exception.printStackTrace();
      }
   }

   /**
    * Добавление в БД записи о пользовательском запросе в формате {@code LogElement}
    *
    * @param logElement Добавляемая в лог запись о пользовательском запросе
    * @see LogElement
    */
   public void writeLogElement( LogElement logElement )
   {
      try ( Connection connection = DriverManager.getConnection( connectionString, login, password );
            Statement statement = connection.createStatement() )
      {
         String sql = "INSERT INTO LOGTABLE (time, ip, text, fromLang, toLang) VALUES(" +
               "'" + logElement.getTime() + "'" + "," +
               "'" + logElement.getIp() + "'" + "," +
               "'" + logElement.getText() + "'" + "," +
               "'" + logElement.getFrom() + "'" + "," +
               "'" + logElement.getTo() + "'" + ")";

         statement.executeUpdate( sql );
      }
      catch ( SQLException exception )
      {
         System.out.println( "ERROR! Cannot write log element to database!" );
         exception.printStackTrace();
      }
   }

   /**
    * Получение всех записей {@code LogElement} из БД в виде {@code ArrayList<LogElement>}
    * Процесс получения состоит из следующих этапов:
    *
    * @return Список всех пользовательских запросов сохраненных в БД в виде {@code ArrayList<LogElement>}
    * @see LogElement
    */
   public ArrayList<LogElement> getLog()
   {
      ArrayList<LogElement> log = new ArrayList<>();

      try ( Connection connection = DriverManager.getConnection( connectionString, login, password );
            Statement statement = connection.createStatement() )
      {
         String sql = "SELECT id, time, ip, text, fromLang, toLang FROM LOGTABLE";
         ResultSet resultSet = statement.executeQuery( sql );

         while ( resultSet.next() )
         {
            log.add( new LogElement( resultSet.getInt( 1 ),
                  resultSet.getString( 2 ),
                  resultSet.getString( 3 ),
                  resultSet.getString( 4 ),
                  resultSet.getString( 5 ),
                  resultSet.getString( 6 ) ) );
         }

         resultSet.close();
      }
      catch ( SQLException exception )
      {
         System.out.println( "ERROR! Cannot get log from database!" );
         exception.printStackTrace();
      }

      return log;
   }
}
