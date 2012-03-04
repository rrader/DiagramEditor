package ua.romanrader.diagrameditor.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import ua.romanrader.diagrameditor.csv.DataSet;
import ua.romanrader.diagrameditor.util.observer.Notificator;

@SuppressWarnings("serial")
public class CSVTable extends JTable {

	public CSVTable() {
		super();
		//setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		setModel(CSVTableModel.getInstance());
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CSVTable table = CSVTable.this;
				int col= table.columnAtPoint(e.getPoint());
				DataSet ds = CSVTableModel.getInstance().get(col);
				Notificator.getInstance().sendNotify(table, DiagramEditor.NEW_DATASET_NOTIFICATION, ds);
			}
		} );
	}
}
