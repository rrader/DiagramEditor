package ua.romanrader.diagrameditor.util.observer;

/**
 * Интерфейс слушателя сообщений
 * @author romanrader
 *
 */
public interface Observer {
    /**
     * Получение сообщения
     * @param notification сообщение
     */
    void notificationReceived(Notification notification);
}
