package ua.romanrader.diagrameditor.util.observer;

/**
 * Описание сообщения
 * @author romanrader
 *
 */
public class Notification {
    /**
     * отправитель
     */
    private Object sender;
    /**
     * идентификатор
     */
    private String name;
    /**
     * данные
     */
    private Object userData;

    /**
     * Конструктор сообщения
     * @param theSender источник
     * @param theName идентификатор
     * @param theUserData данные
     */
    public Notification(final Object theSender,
            final String theName, final Object theUserData) {
        super();
        this.sender = theSender;
        this.name = theName;
        this.userData = theUserData;
    }

    /**
     * Получить источник
     * @return источник
     */
    public final Object getSender() {
        return sender;
    }
    /**
     * Установить источник
     * @param theSender источник
     */
    public final void setSender(final Object theSender) {
        this.sender = theSender;
    }
    /**
     * Идентификатор
     * @return идентификатор
     */
    public final String getName() {
        return name;
    }
    /**
     * Идентификатор
     * @param theName идентификатор
     */
    public final void setName(final String theName) {
        this.name = theName;
    }
    /**
     * Данные
     * @return данные
     */
    public final Object getUserData() {
        return userData;
    }
    /**
     * Данные
     * @param theUserData данные
     */
    public final void setUserData(final Object theUserData) {
        this.userData = theUserData;
    }
}
