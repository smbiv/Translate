package ru.tinkoff.translate.translate;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер сервиса перевода
 */
@RestController
public class TranslatorController
{
   /** База данных с логом перевода */
   final LogBase logBase = new LogBase();

   /** Компонент переводчика */
   final Translator translator = new Translator();

   /**
    * Запрос на перевод текста
    *
    * @param text Исходный пользовательский текст
    *
    * @param langFrom Кодовое обозначение исходного языка
    *
    * @param langTo Кодовое обозначение целевого языка
    *
    * @param request Пользовательский HTTP-запрос
    *
    * @return Компонент {@code Translator} с результатами перевода
    *
    * @see Translator
    */
   @GetMapping( "/translate" )
   public Translator translate( @RequestParam( value = "text" ) String text, @RequestParam( value = "from" ) String langFrom,
                                @RequestParam( value = "to" ) String langTo, HttpServletRequest request )
   {
      String ip = request.getRemoteAddr();
      Timestamp timestamp = new Timestamp( System.currentTimeMillis() );

      logBase.writeLogElement( new LogElement( timestamp.toString(), ip, text, langFrom, langTo ) );
      translator.translate( text, langFrom, langTo );

      return translator;
   }

   /**
    * Запрос на вывод лога работы переводчика из базы данных
    *
    * @return Лог работы в формате {@code ArrayList<LogElement>}
    *
    * @see LogElement
    */
   @GetMapping( "/log" )
   public ArrayList<LogElement> printLog()
   {
      return logBase.getLog();
   }
}