package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;

public class RemoveSection implements ActionListener {
	private DiagramEditor de;
	private JToggleButton button;
	private JCheckBoxMenuItem item;
	
	public RemoveSection(DiagramEditor de, JToggleButton button, JCheckBoxMenuItem item) {
		this.de = de;
		this.button = button;
		this.item = item;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (DataModel.getInstance().getVstate() == DataModel.ViewState.Simultaneously) {
			return;
		}
		System.out.println("remove section");
		if (e.getSource().getClass() == JToggleButton.class) {
			DataModel model = DataModel.getInstance();
	    	model.setState( ((JToggleButton)e.getSource()).isSelected() ?
	    			DataModel.State.StateRemoving : DataModel.State.StateNormal );
	    	item.setSelected(((JToggleButton)e.getSource()).isSelected());
	    	
	    	de.getStatusBar().setText(((JToggleButton)e.getSource()).isSelected() ?
	    			"Edit mode: removing" : "Edit mode: creating");
		}
		if (e.getSource().getClass() == JCheckBoxMenuItem.class) {
			DataModel model = DataModel.getInstance();
	    	model.setState( ((JCheckBoxMenuItem)e.getSource()).isSelected() ?
	    			DataModel.State.StateRemoving : DataModel.State.StateNormal );
	    	
	    	button.setSelected(((JCheckBoxMenuItem)e.getSource()).isSelected());
	    	
	    	de.getStatusBar().setText(((JCheckBoxMenuItem)e.getSource()).isSelected() ?
	    			"Edit mode: removing" : "Edit mode: creating");
		}
    }
}