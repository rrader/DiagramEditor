package ua.romanrader.diagrameditor.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ua.romanrader.diagrameditor.model.DataModel;

@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		if (DataModel.getInstance().getVstate() == DataModel.ViewState.Single)  {
			ArrayList<Color> colorSet = DataModel.getInstance()
					.getCurrentColorSet();
			if (DataModel.getInstance().getSelectedColumn() == col) {
				if ((colorSet != null) && (row < colorSet.size())) {
					comp.setBackground(colorSet.get(row));
					comp.setForeground(Color.black);
				} else {
					comp.setBackground(Color.LIGHT_GRAY);
					comp.setForeground(Color.LIGHT_GRAY);
				}
			} else {
				if (row < DataModel.getInstance().get(col).size()) {
					comp.setBackground(null);
					comp.setForeground(Color.black);
				} else {
					comp.setBackground(Color.LIGHT_GRAY);
					comp.setForeground(Color.LIGHT_GRAY);
				}
			}
		}
		if (DataModel.getInstance().getVstate() == DataModel.ViewState.Simultaneously)  {
			if (DataModel.getInstance().getColorSets() != null) {
				if (col < DataModel.getInstance().getColorSets().size()) {
					ArrayList<Color> colorSet = DataModel.getInstance().getColorSets().get(col);
					if ((colorSet != null) && (row < colorSet.size())) {
						comp.setBackground(colorSet.get(row));
						comp.setForeground(Color.black);
					} else {
						comp.setBackground(Color.LIGHT_GRAY);
						comp.setForeground(Color.LIGHT_GRAY);
					}
				} else {
					comp.setBackground(Color.LIGHT_GRAY);
					comp.setForeground(Color.LIGHT_GRAY);
				}
			}
		}
		
		return (comp);
	}

}
