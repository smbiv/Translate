package ru.tinkoff.translate.translate;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Класс {@code TranslatorYandex} реализует запрос на перевод исходного текста
 * с помощью онлайн-переводчика Яндекс.Переводчик (http://translate.yandex.net)
 */
public class TranslatorYandex implements Callable<String>
{
   /** Ссылка-токен для доступа к API Яндекс.Переводчика
    * На данный момент токены перестали быть бесплатным, а для получения Trial-версии требуется подтвержденный
    * платежный аккаунт Яндекса.
    * Поэтому, отдельное спасибо zdanevich-vitaly с https://habr.com за выложенный в открытый доступ токен
    */
   private static final String TOKEN = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20150627T071448Z.117dacaac1e63b79.6b1b4bb84635161fcd400dace9fb2220d6f344ef";

   /** Название поля с возвращаемым Яндекс.Переводчиком кодом */
   private static final String CODE_FIELD = "code";

   /** Название поля с возвращаемым Яндекс.Переводчиком переведенным текстом */
   private static final String TEXT_FIELD = "text";

   /** Код, возвращаемый Яндекс.Переводчиком в случае успеха */
   private static final int HTTP_SUCCESS = 200;

   /** Исходный пользовательский текст */
   private final String text;

   /** Кодовое обозначение исходного языка по стандарту ISO 631-1 */
   private final String langFrom;

   /** Кодовое обозначение целевого языка по стандарту ISO 631-1 */
   private final String langTo;

   /**
    * Инициализация запроса на перевод
    *
    * @param text Исходный пользовательский текст
    *
    * @param langFrom Кодовое обозначение исходного языка по стандарту ISO 631-1
    *
    * @param langTo Кодовое обозначение целевого языка по стандарту ISO 631-1
    */
   public TranslatorYandex( String text, String langFrom, String langTo )
   {
      this.text = text;
      this.langFrom = langFrom;
      this.langTo = langTo;
   }

   /**
    * Отправка запроса на перевод текста
    *
    * @return Переведенный текст
    *
    * @throws IOException В случае если не удалось установить соединение с сервисом онлайн-перевода или структура
    *    полученного ответа не соответствует ожидаемой
    */
   @Override
   public String call() throws IOException
   {
      String lang = langFrom + "-" + langTo;
      String translated;
      int returnCode;

      URL url = new URL( TOKEN );
      HttpsURLConnection connection = ( HttpsURLConnection )url.openConnection();

      connection.setRequestMethod( "POST" );
      connection.setDoOutput( true );

      DataOutputStream request = new DataOutputStream( connection.getOutputStream() );
      request.writeBytes( "text=" + URLEncoder.encode( text, "UTF-8" ) + "&lang=" + lang );

      InputStream response = connection.getInputStream();

      JSONObject json = new JSONObject( new java.util.Scanner( response ).nextLine() );

      if ( json.has( CODE_FIELD ) )
      {
         if ( !json.isNull( CODE_FIELD ) )
            returnCode = json.getInt( CODE_FIELD );
         else
            throw new IOException( "ERROR! Return code is null!" );
      } else
         throw new IOException( "ERROR! Cannot find return code in response!" );

      if ( returnCode != HTTP_SUCCESS )
         throw new IOException( "ERROR! The next error code was received from Yandex: " + returnCode );

      if ( json.has( TEXT_FIELD ) )
      {
         if ( !json.isNull( TEXT_FIELD ) )
         {
            JSONArray arr = json.getJSONArray( TEXT_FIELD );
            translated = arr.getString( 0 );
         } else
            throw new IOException( "ERROR! Translated text is null!" );
      } else
         throw new IOException( "ERROR! Cannot find translated text in response!" );

      return translated;
   }
}
