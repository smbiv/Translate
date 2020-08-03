package ru.tinkoff.translate.translate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирования объекта {@code Translator}
 *
 * @see Translator
 */
class TranslatorTest {
    private Translator translator;

    /**
     * Тестирование перевода одного слова
     */
    @Test
    void translateOneWord() {
        String langFrom = "en";
        String langTo = "ru";
        String text = "Test";
        String expected = "Тест";

        TranslatorOutput output = translator.translate(text, langFrom, langTo);

        assertEquals(expected, output.getOutput());
    }

    /**
     * Тестирование перевода предложения
     */
    @Test
    void translateSentence() {
        String langFrom = "en";
        String langTo = "ru";
        String text = "Test sentence. And one more.";
        String expected = "Тест приговор. И один больше.";

        TranslatorOutput output = translator.translate(text, langFrom, langTo);

        assertEquals(expected, output.getOutput());
    }

    /**
     * Тестирование перевода с некорректно заданным целевым языком
     */
    @Test
    void incorrectLangTo() {
        String langFrom = "en";
        String langTo = "rus";
        String text = "Test";
        String expected = "ERROR! Incorrect target language code!";

        TranslatorOutput output = translator.translate(text, langFrom, langTo);

        assertEquals(expected, output.getOutput());
    }

    /**
     * Тестирование перевода с некорректно заданным начальным языком
     */
    @Test
    void incorrectLangFrom() {
        String langFrom = "ent";
        String langTo = "ru";
        String text = "Test";
        String expected = "ERROR! Incorrect input language code!";

        TranslatorOutput output = translator.translate(text, langFrom, langTo);

        assertEquals(expected, output.getOutput());
    }

    @BeforeEach
    void setUp() {
        translator = new Translator();
    }
}