package ru.tinkoff.translate.translate;

/**
 * Класс {@code LogElement} используется для представления элементов базы данных пользовательских запросов
 * в виде единой структуры
 *
 * @see LogBase
 */
public class LogElement
{
   /** ID запроса */
   private final int id;

   /** Время получения запроса */
   private final String time;

   /** IP-адрес пользователя в формате IPv6 */
   private final String ip;

   /** Исходный пользовательский текст */
   private final String text;

   /** Исходный язык пользовательского текста */
   private final String from;

   /** Желаемый язык переведенного текста */
   private final String to;

   /**
    * Инициализация новой записи лога - объекта {@code LogElement} без указания ID запроса
    *
    * @param  time
    *         Время получения запроса
    *
    * @param  ip
    *         IP-адрес пользователя в формате IPv6
    *
    * @param  text
    *         Исходный пользовательский текст
    *
    * @param  from
    *         Исходный язык пользовательского текста
    *
    * @param  to
    *         Желаемый язык переведенного текста
    */
   public LogElement( String time, String ip, String text, String from, String to )
   {
      this.id = 0;
      this.time = time;
      this.ip = ip;
      this.text = text;
      this.from = from;
      this.to = to;
   }

   /**
    * Инициализация новой записи лога - объекта {@code LogElement} с указанием ID запроса
    *
    * @param  id
    *         ID запроса
    *
    * @param  time
    *         Время получения запроса
    *
    * @param  ip
    *         IP-адрес пользователя в формате IPv6
    *
    * @param  text
    *         Исходный пользовательский текст
    *
    * @param  from
    *         Исходный язык пользовательского текста
    *
    * @param  to
    *         Желаемый язык переведенного текста
    */
   public LogElement( int id, String time, String ip, String text, String from, String to )
   {
      this.id = id;
      this.time = time;
      this.ip = ip;
      this.text = text;
      this.from = from;
      this.to = to;
   }

   /**
    * Получение ID запроса
    *
    * @return Уникальный идентификатор запроса
    */
   public int getId()
   {
      return id;
   }

   /**
    * Получение даты и времени запроса
    *
    * @return Дата и время получения запроса от пользователя
    */
   public String getTime()
   {
      return time;
   }

   /**
    * Получение IP-адреса пользователя в формате IPv6
    *
    * @return IP-адрес пользователя в формате IPv6
    */
   public String getIp()
   {
      return ip;
   }

   /**
    * Получение исходного пользовательского текста
    *
    * @return Исходный пользовательский текст
    */
   public String getText()
   {
      return text;
   }

   /**
    * Получение исходного языка пользовательского текста
    *
    * @return Буквенный код исходного языка пользовательского текста
    */
   public String getFrom()
   {
      return from;
   }

   /**
    * Получение желаемого языка переведенного текста
    *
    * @return Буквенный код языка переведенного текста
    */
   public String getTo()
   {
      return to;
   }
}
