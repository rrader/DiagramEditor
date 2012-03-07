package ua.romanrader.diagrameditor.model.csv;

import java.io.IOException;

/**
 * Исключение при парсинге CSV-файла
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class CSVParseException extends IOException {
    /**
     * Конструктор
     */
    public CSVParseException() {
        super();
    }

    /**
     * Конструктор
     * @param text текст
     */
    public CSVParseException(final String text) {
        super(text);
    }
}
