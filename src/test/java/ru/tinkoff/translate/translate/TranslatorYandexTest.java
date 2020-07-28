package ru.tinkoff.translate.translate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирования объекта {@code TranslatorYandex}
 *
 * @see TranslatorYandex
 */
class TranslatorYandexTest
{
   /**
    * Тестирование запроса на перевод
    */
   @org.junit.jupiter.api.Test
   void call()
   {
      String langFrom = "en";
      String langTo = "ru";
      String text = "Test";
      String expected = "Тест";

      TranslatorYandex translatorYandex = new TranslatorYandex( text, langFrom, langTo );

      try
      {
         assertEquals( translatorYandex.call(), expected );
      }
      catch ( IOException exception )
      {
         fail( "TEST ERROR! Exception during translatorYandex.call()!" );
      }
   }
}