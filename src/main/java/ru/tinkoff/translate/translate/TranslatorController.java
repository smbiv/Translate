package ru.tinkoff.translate.translate;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер сервиса перевода
 */
@RestController
public class TranslatorController {
    /**
     * База данных с логом перевода
     */
    private LogInterface logBase;

    /**
     * Компонент переводчика
     */
    private Translator translator;

    /**
     * Конструктор контроллера сервиса переводов
     *
     * @param logBase Логер применяемый для фиксирования, хранения и обработки пользовательских запросов
     * @param translator Класс обработки REST-запросов
     */
    @Autowired
    TranslatorController(LogInterface logBase,
                         Translator translator) {
        this.logBase = logBase;
        this.translator = translator;

        this.logBase.initTable();
    }

    /**
     * Запрос на перевод текста
     *
     * @param text     Исходный пользовательский текст
     * @param langFrom Кодовое обозначение исходного языка
     * @param langTo   Кодовое обозначение целевого языка
     * @param request  Пользовательский HTTP-запрос
     * @return Компонент {@code TranslatorOutput} с результатами перевода в формате {@code TranslatorOutput}
     * @see Translator
     * @see TranslatorOutput
     */
    @GetMapping("/translate")
    public TranslatorOutput translate(@RequestParam(value = "text") String text,
                                @RequestParam(value = "from") String langFrom,
                                @RequestParam(value = "to") String langTo,
                                HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        logBase.writeLogElement(new LogElement(timestamp.toString(), ip, text, langFrom, langTo));

        return translator.translate(text, langFrom, langTo);
    }

    /**
     * Запрос на вывод лога работы переводчика из базы данных
     *
     * @return Лог работы в формате {@code ArrayList<LogElement>}
     * @see LogElement
     */
    @GetMapping("/log")
    public List<LogElement> printLog() {
        return logBase.getLog();
    }
}