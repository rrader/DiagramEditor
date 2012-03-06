package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

public class AddSection implements ActionListener {
	private DiagramEditor de;
	
	public AddSection(DiagramEditor de) {
		this.de = de;
	}
	
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
