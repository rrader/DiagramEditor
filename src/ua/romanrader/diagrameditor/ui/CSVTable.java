package ua.romanrader.diagrameditor.ui;

import javax.swing.JTable;

import ua.romanrader.diagrameditor.csv.DataSet;
import ua.romanrader.diagrameditor.util.Observer.Notificator;

@SuppressWarnings("serial")
public class CSVTable extends JTable {

	//move := model here
	public CSVTable() {
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		setModel(CSVTableModel.getInstance());
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				JTable table = CSVTable.this;
				int col= table.columnAtPoint(e.getPoint());
				DataSet ds = CSVTableModel.getInstance().get(col);
				Notificator.getInstance().sendNotify(table, DiagramEditor.NEW_DATASET_NOTIFICATION, ds);
			}
		} );
	}
}
