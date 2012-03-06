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
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("Ready");
    }
    
    /**
     * Установить сообщение
     * @param message
     */
    public void setMessage(String message) {
        setText(" "+message);        
    }        
}