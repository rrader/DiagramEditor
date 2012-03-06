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
	public void notificationReceived(Notification notification);
}
