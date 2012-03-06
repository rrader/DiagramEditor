package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.CSVProcessor;
import ua.romanrader.diagrameditor.model.csv.CSVReceiver;
import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.ui.DiagramEditor;

/**
 * Действие открытия .csv файла
 * @author romanrader
 *
 */
public class Open implements ActionListener {
	private DiagramEditor de;
	
	/**
	 * Конструктор действия
	 * @param de главное окно
	 */
	public Open(DiagramEditor de) {
		this.de = de;
	}
	
	/**
	 * Выполнение действия
	 */
    public void actionPerformed(ActionEvent e) {
    	de.getStatusBar().setText("Opening dataset...");
    	JFileChooser fc = new JFileChooser();
    	
    	int returnVal = fc.showOpenDialog(de);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	de.getStatusBar().setText("Opening "+fc.getSelectedFile().getName());
    		CSVProcessor.LoadFile(fc.getSelectedFile().getPath(), new CSVReceiver(){

    			@Override
    			public void receivingFailed(String message) {
    				JOptionPane.showMessageDialog(de, "Opening failed: "+message, "Error",
    				        JOptionPane.ERROR_MESSAGE);
    				de.getStatusBar().setText("opening failed");
    			}

    			@Override
    			public void receivingFinished(DataSet data) {
    				DataModel.getInstance().add(data);
    				de.getStatusBar().setText("opened successfully");
    			}
    			
    		});
        } else {
        	de.getStatusBar().setText("Ready");
        }
    }
}
