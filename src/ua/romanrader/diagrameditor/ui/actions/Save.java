package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.CSVProcessor;
import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.ui.DiagramEditor;

/**
 * Действия сохранения в .csv файл
 * @author romanrader
 *
 */
public class Save implements ActionListener {
    /**
     * Главное окно
     */
    private DiagramEditor de;

    /**
     * Конструктор действия
     * @param tde главное окно
     */
    public Save(final DiagramEditor tde) {
        this.de = tde;
    }

    /**
     * Выполнение действия
     * @param e действие
     */
    public final void actionPerformed(final ActionEvent e) {
        DataSet ds = DataModel.getInstance().getCurrentDataSet();
        if (ds != null) {
            de.getStatusBar().setText("Saving dataset...");
            JFileChooser fc = new JFileChooser();

            int returnVal = fc.showSaveDialog(de);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                de.getStatusBar().setText("Save to "
                + fc.getSelectedFile().getName());
                try {
                    CSVProcessor.saveFile(fc.getSelectedFile().getPath(), ds);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(de, "Saving failed", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    de.getStatusBar().setText("I/O error");
                }
            }
        }
    }
}
