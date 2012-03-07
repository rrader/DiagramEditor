package ua.romanrader.diagrameditor.model.csv;

//Strategy

/**
 * Интерфейс получателя CSV файла
 * @author romanrader
 *
 */
public interface CSVReceiver {
    /**
     * Получение прошло с ошибкой
     * @param message сообщение об ошибке
     */
    void receivingFailed(String message);

    /**
     * Получение завершено успешно
     * @param data данные
     */
    void receivingFinished(DataSet data);
}
