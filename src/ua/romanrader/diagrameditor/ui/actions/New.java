package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.DataSet;

public class New implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	DataSet ds = new DataSet();
    	ds.add(1.);
    	DataModel.getInstance().add(ds);
    }
}