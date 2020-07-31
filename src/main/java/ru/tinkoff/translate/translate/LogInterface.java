package ru.tinkoff.translate.translate;

import java.util.List;

/**
 * Интерфейс {@code LogInterface} декларирует методы взаимодействие приложения с логером пользовательских запросов
 * Объекты реализующие интерфейс:
 * @see LogBase - логирование пользовательских запросов в базу данных JDBC
 */
public interface LogInterface {
    /**
     * Инициализация класса для логирования
     */
    void initTable();

    /**
     * Добавление в лог записи о пользовательском запросе в формате {@code LogElement}
     *
     * @param logElement Добавляемая в лог запись о пользовательском запросе
     * @see LogElement
     */
    void writeLogElement(LogElement logElement);

    /**
     * Получение всех записей {@code LogElement} из лога в виде {@code ArrayList<LogElement>}
     *
     * @return Список всех пользовательских запросов сохраненных в логе в виде {@code ArrayList<LogElement>}
     * @see LogElement
     */
    List<LogElement> getLog();
}
