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
		ArrayList<Color> colorSet = DataModel.getInstance()
				.getCurrentColorSet();
		if (DataModel.getInstance().getSelectedColumn() == col) {
			if (colorSet != null) {
				comp.setBackground(colorSet.get(row));
			} else {
				comp.setBackground(Color.LIGHT_GRAY);
			}
		} else {
			comp.setBackground(null);
		}
		return (comp);
	}

}
