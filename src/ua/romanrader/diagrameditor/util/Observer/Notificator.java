package ua.romanrader.diagrameditor.util.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Класс управления сообщениями. Синглтон
 * @author romanrader
 *
 */
public class Notificator {
	private static Notificator instance = new Notificator();
	private Notificator() {}
	
	/**
	 * Получить инстанс
	 */
	public static Notificator getInstance() {
		return instance;
	}
	
	private Map<String, ArrayList<Observer>> observers = new HashMap<String, ArrayList<Observer>>();
	
	/**
	 * Зарегистрировать слушателя на сообщение
	 * @param observer слушатель
	 * @param notification имя сообщения
	 */
	public void addObserver(Observer observer, String notification) {
		if (!observers.containsKey(notification)) {
			observers.put(notification, new ArrayList<Observer>());
		}
		ArrayList<Observer> list = observers.get(notification);
		list.add(observer);
	}
	
	/**
	 * Удалить слушателя со всех сообщений
	 * @param observer слушатель
	 */
	public void removeObserver(Observer observer) {
		//removing from all notifications
		for(Entry<String, ArrayList<Observer>> list : observers.entrySet()) {
			list.getValue().remove(observer);
		}
	}
	
	/**
	 * Удалить слушателя с сообщения
	 * @param observer слушатель
	 * @param notification сообщение
	 */
	public void removeObserver(Observer observer, String notification) {
		//removing from particular notification
		observers.get(notification).remove(observer);
	}
	
	/**
	 * Отправить сообщение
	 * @param sender отправитель
	 * @param notification сообщение
	 */
	public void sendNotify(Object sender, String notification) {
		this.sendNotify(sender, notification, null);
	}
	
	/**
	 * Отправить сообщение с данными
	 * @param sender отправитель
	 * @param notification сообщение
	 * @param data данные
	 */
	public void sendNotify(Object sender, String notification, Object data) {
		ArrayList<Observer> list = observers.get(notification);
		Notification n = new Notification(sender, notification, data);
		for(Observer o : list) {
			o.notificationReceived(n);
		}		
	}
}
