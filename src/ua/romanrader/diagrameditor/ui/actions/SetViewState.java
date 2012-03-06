package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

/**
 * Действие установки режима отображения
 * @author romanrader
 *
 */
public class SetViewState implements ActionListener {
	private DiagramEditor de;
	private JToggleButton button;
	private JCheckBoxMenuItem item;
	
	/**
	 * Конструктор действия
	 * @param de главное окно
	 * @param button кнопка на панели инструментов
	 * @param item пункт меню
	 */
	public SetViewState(DiagramEditor de, JToggleButton button, JCheckBoxMenuItem item) {
		this.de = de;
		this.button = button;
		this.item = item;
	}
	
	/**
	 * Выполнение действия
	 */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() instanceof JToggleButton) {
    		DataModel model = DataModel.getInstance();
        	model.setVstate( ((JToggleButton)e.getSource()).isSelected() ? DataModel.ViewState.Simultaneously : DataModel.ViewState.Single );
        	item.setSelected(((JToggleButton)e.getSource()).isSelected());
        	
        	de.getStatusBar().setText(((JToggleButton)e.getSource()).isSelected() ? "View mode: simultaneously" : "View mode: single");
        	Notificator.getInstance().sendNotify(de, DiagramEditor.VIEWSTATE_CHANGED);
    	}
    	if (e.getSource() instanceof JCheckBoxMenuItem) {
    		DataModel model = DataModel.getInstance();
        	model.setVstate( ((JCheckBoxMenuItem)e.getSource()).isSelected() ? DataModel.ViewState.Simultaneously : DataModel.ViewState.Single );
        	button.setSelected(((JCheckBoxMenuItem)e.getSource()).isSelected());
        	
        	de.getStatusBar().setText(((JCheckBoxMenuItem)e.getSource()).isSelected() ? "View mode: simultaneously" : "View mode: single");
        	Notificator.getInstance().sendNotify(de, DiagramEditor.VIEWSTATE_CHANGED);
    	}
    }
}
