package ua.romanrader.diagrameditor.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.CSVProcessor;
import ua.romanrader.diagrameditor.model.csv.CSVReceiver;
import ua.romanrader.diagrameditor.model.csv.DataSet;

@SuppressWarnings("serial")
public class DiagramEditor extends JFrame {
	public static final String DEFAULT_CSV_FILE = "file.csv";
	public static final String NEW_DATASET_NOTIFICATION = "DENewDatasetSelected";
	public static final String COLUMN_ADDED = "DEColumnAdded";
	public static final String DATASET_CHANGED = "DEDataSetChanged";
	
	private JTable dataTable;
	private DiagramView diagramView;
	
	public DiagramEditor() {
		super("Diagram Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setLayout(new GridLayout(1,2));
		
		//Table
		dataTable = new CSVTable();
		add(new JScrollPane(dataTable));
		
		//Diagram
		diagramView = new DiagramView();
		getContentPane().add(diagramView);

		//Load default file.csv
		CSVProcessor.LoadFile(DEFAULT_CSV_FILE, new CSVReceiver(){

			@Override
			public void receivingFailed(String message) {
				//TODO Create new column with one cell, contents 1.0
				System.out.println("receiving failed");
			}

			@Override
			public void receivingFinished(DataSet data) {
				DataModel.getInstance().add(data);
				System.out.println("received successfully");
			}
			
		});
//		CSVProcessor.LoadFile(DEFAULT_CSV_FILE, new CSVReceiver(){
//
//			@Override
//			public void receivingFailed(String message) {
//				JOptionPane.showMessageDialog(DiagramEditor.this, "Loading file failed: "+message,
//						"Error", JOptionPane.ERROR_MESSAGE);
//			}
//
//			@Override
//			public void receivingFinished(DataSet data) {
//				CSVTableModel.getInstance().add(data);
//			}
//			
//		});
		//model.add();
	}

    public static void main(String[] args) {
    	DiagramEditor frame = new DiagramEditor();
        frame.setVisible(true);
    }
}
