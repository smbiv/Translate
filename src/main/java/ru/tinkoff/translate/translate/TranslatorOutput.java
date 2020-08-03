package ru.tinkoff.translate.translate;

/**
 * Объект для передачи выходных данных переводчика
 */
public class TranslatorOutput {

    /**
     * Переведенный текст
     */
    private String output;

    /**
     * Конструктор
     * @param output Переведенный текст
     */
    public TranslatorOutput(String output) {
        this.output = output;
    }

    /**
     * Получение переведенного текста
     */
    public String getOutput() {
        return output;
    }

    /**
     * Установка переведенного текста
     */
    public void setOutput(String output) {
        this.output = output;
    }
}
