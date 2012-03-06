package ua.romanrader.diagrameditor.util.observer;

/**
 * Описание сообщения
 * @author romanrader
 *
 */
public class Notification {
	private Object sender;
	private String name;
	private Object userData;
	
	/**
	 * Конструктор сообщения
	 * @param sender источник
	 * @param name идентификатор
	 * @param userData данные
	 */
	public Notification(Object sender, String name, Object userData) {
		super();
		this.sender = sender;
		this.name = name;
		this.userData = userData;
	}
	
	/**
	 * Получить источник
	 * @return источник
	 */
	public Object getSender() {
		return sender;
	}
	/**
	 * Установить источник
	 */
	public void setSender(Object sender) {
		this.sender = sender;
	}
	/**
	 * Идентификатор
	 * @return идентификатор
	 */
	public String getName() {
		return name;
	}
	/**
	 * Идентификатор
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Данные
	 * @return данные
	 */
	public Object getUserData() {
		return userData;
	}
	/**
	 * Данные/
	 */
	public void setUserData(Object userData) {
		this.userData = userData;
	}
}
