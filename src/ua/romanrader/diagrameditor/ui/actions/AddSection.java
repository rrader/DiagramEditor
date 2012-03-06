package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

/**
 * Действие добавления секции в текущий дата-сет
 * @author romanrader
 *
 */
public class AddSection implements ActionListener {
	private DiagramEditor de;
	
	/**
	 * Конструктор действия
	 * @param de главное окно
	 */
	public AddSection(DiagramEditor de) {
		this.de = de;
	}
	
	/**
	 * Выполнение действия
	 */
    public void actionPerformed(ActionEvent e) {
    	DataModel model = DataModel.getInstance();
    	if (model.getCurrentDataSet() != null) {
    		model.getCurrentDataSet().addValue();
			model.makeColors();
			Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
			de.getStatusBar().setText("Section added");
    	} else {
    		de.getStatusBar().setText("No dataset");
    	}
    }
}
