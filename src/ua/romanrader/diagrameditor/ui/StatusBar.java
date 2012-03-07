package ua.romanrader.diagrameditor.ui;

import java.awt.Dimension;

import javax.swing.JLabel;

/**
 * Строка состояния
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class StatusBar extends JLabel {

    /**
     * Конструктор строки состояния
     */
    public StatusBar() {
        super();
        final int width = 100;
        final int height = 16;
        super.setPreferredSize(new Dimension(width, height));
        setMessage("Ready");
    }

    /**
     * Установить сообщение
     * @param message сообщение
     */
    public final void setMessage(final String message) {
        setText(" " + message);
    }
}
