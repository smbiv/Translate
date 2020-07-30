package ru.tinkoff.translate.translate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * Класс {@code Translator} предназначен для обработки пользовательского запроса на выполнение перевода
 * Перевод выполняется с помощью сервиса онлайн-переводов Яндекс.Переводчик (см. класс {@code TranslatorYandex})
 *
 * @see TranslatorYandex
 */
public class Translator {
    /**
     * Результат перевода
     */
    private String output = "";

    /**
     * Массив буквенных обозначений языков по стандарту ISO 631-1
     */
    private static final String[] ISO_LANG_CODES = java.util.Locale.getISOLanguages();

    /**
     * Обработка запроса на перевод
     * <p>
     * Исходный текст разбивается на отдельный слова, на основе которых, с помощью {@code TranslatorYandex} формируется
     * список запросов к серсису Яндекс.Переводчик. Таким образом, одному слову соответствует один запрос.
     * Запросы отправляются на обработку в нескольких потоках (не более 5).
     * <p>
     * Перед формированием списка запросов выполняется проверка параметров {@code langFrom} и {@code langTo}
     * на соотвествие стандарту ISO 631-1. Если данные параметры не соответствуют стандарту, перевод не выполняется,
     * а вместо перевода в {@code output} записывается соответствующее сообщение об ошибке.
     * <p>
     * После перевода отдельных слов, из полученного набора переведенных слов формируется единая строка,
     * значение которой записывается в поле {@code output}.
     * <p>
     * В случае возникновения исключения, записывает сообщение из исключения в поле {@code output}.
     * <p>
     * Результат перевода может быть получен с помощью {@code getOutput()}.
     *
     * @param text     Исходный пользовательский текст
     * @param langFrom Кодовое обозначение исходного языка
     * @param langTo   Кодовое обозначение целевого языка
     * @see TranslatorYandex
     */
    public void translate(String text, String langFrom, String langTo) {
        this.output = "";

        if (isLangCodeIncorrect(langFrom)) {
            this.output = "ERROR! Incorrect input language code!";
            return;
        }

        if (isLangCodeIncorrect(langTo)) {
            this.output = "ERROR! Incorrect target language code!";
            return;
        }

        try {
            List<TranslatorYandex> taskList = new ArrayList<>();

            Consumer<String> consumer = word -> taskList.add(new TranslatorYandex(word, langFrom, langTo));
            Arrays.asList(text.split(" ")).forEach(consumer);

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            List<Future<String>> resultList = executorService.invokeAll(taskList);
            StringBuilder translation = new StringBuilder();

            for (Future<String> future : resultList) {
                translation.append(future.get());
                translation.append(" ");
            }

            this.output = translation.toString().trim();
        } catch (InterruptedException | ExecutionException exception) {
            this.output = exception.getMessage();
        }
    }

    /**
     * Получение результата перевода
     *
     * @return Результат перевода (переведенный текст или сообщение об ошибке)
     */
    public String getOutput() {
        return output;
    }

    /**
     * Проверка кодового обозначения языка {@code lang} на соответствие стандарту ISO 631-1
     *
     * @param lang Кодовое обозначение языка
     * @return {@code true} если код НЕ соответствует стандарту ISO 631-1 или {@code true} если соответствует
     */
    private boolean isLangCodeIncorrect(String lang) {
        boolean result = true;

        for (String strTemp : ISO_LANG_CODES)
            if (lang.equals(strTemp)) {
                result = false;
                break;
            }

        return result;
    }
}
