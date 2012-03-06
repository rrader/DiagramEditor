package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

/**
 * Действие закрытия дата-сета
 * @author romanrader
 *
 */
public class CloseDataset implements ActionListener {
	private DiagramEditor de;
	
	/**
	 * Конструктор действия
	 * @param de главное окно
	 */
	public CloseDataset(DiagramEditor de) {
		this.de = de;
	}

	/**
	 * Выполнение действия
	 */
    public void actionPerformed(ActionEvent e) {
    	DataModel model = DataModel.getInstance();
    	
    	if (model.getSelectedColumn() != -1) {
    		int response = JOptionPane.showConfirmDialog(null, "Close current dataset?", "Confirm",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				model.remove(model.getSelectedColumn());
				model.setSelectedColumn(-1);
				model.setCurrentDataSet(null);
				Notificator.getInstance().sendNotify(this, DiagramEditor.COLUMN_ADDED);
				Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
    			de.getStatusBar().setText("dataset closed");
			}
    	}
    }
}
