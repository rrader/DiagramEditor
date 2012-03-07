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
public final class Notificator {
    /**
     * инстанс синглтона
     */
    private static Notificator instance = new Notificator();
    /**
     * приватный конструктор
     */
    private Notificator() { }

    /**
     * Получить инстанс
     * @return инстанс
     */
    public static Notificator getInstance() {
        return instance;
    }

    /**
     * Все слушатели
     */
    private Map<String, ArrayList<Observer>> observers =
            new HashMap<String, ArrayList<Observer>>();

    /**
     * Зарегистрировать слушателя на сообщение
     * @param observer слушатель
     * @param notification имя сообщения
     */
    public void addObserver(final Observer observer,
            final String notification) {
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
    public void removeObserver(final Observer observer) {
        //removing from all notifications
        for (Entry<String, ArrayList<Observer>> list : observers.entrySet()) {
            list.getValue().remove(observer);
        }
    }

    /**
     * Удалить слушателя с сообщения
     * @param observer слушатель
     * @param notification сообщение
     */
    public void removeObserver(final Observer observer,
            final String notification) {
        //removing from particular notification
        observers.get(notification).remove(observer);
    }

    /**
     * Отправить сообщение
     * @param sender отправитель
     * @param notification сообщение
     */
    public void sendNotify(final Object sender, final String notification) {
        this.sendNotify(sender, notification, null);
    }

    /**
     * Отправить сообщение с данными
     * @param sender отправитель
     * @param notification сообщение
     * @param data данные
     */
    public void sendNotify(final Object sender,
            final String notification, final Object data) {
        ArrayList<Observer> list = observers.get(notification);
        Notification n = new Notification(sender, notification, data);
        for (Observer o : list) {
            o.notificationReceived(n);
        }
    }
}
