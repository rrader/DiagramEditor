package ua.romanrader.diagrameditor.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class DiagramEditor extends JFrame {
	public static final String DEFAULT_CSV_FILE = "data.csv";
	public static final String NEW_DATASET_NOTIFICATION = "DENewDatasetSelected";
	
	private JTable dataTable;
	private DiagramView diagramView;
	
	public DiagramEditor() {
		super("Diagram Editor");
		setSize(400,300);
		setLayout(new GridLayout(1,2));
		
		//Table
		dataTable = new JTable();
		add(dataTable);
		
		//Diagram
		diagramView = new DiagramView();
		add(diagramView);
		
		//Load default data.csv
		CSVTableModel model = CSVTableModel.getInstance();
		//model.add();
	}
}
