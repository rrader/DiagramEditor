package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

/**
 * Действие закрытия дата-сета
 * @author romanrader
 *
 */
public class CloseDataset implements ActionListener {
    /**
     * Главное окно
     */
    private DiagramEditor de;

    /**
     * Конструктор действия
     * @param tde главное окно
     */
    public CloseDataset(final DiagramEditor tde) {
        this.de = tde;
    }

    /**
     * Выполнение действия
     * @param e действие
     */
    public final void actionPerformed(final ActionEvent e) {
        DataModel model = DataModel.getInstance();

        if (model.getSelectedColumn() != -1) {
            int response = JOptionPane.showConfirmDialog(null,
                    "Close current dataset?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                model.remove(model.getSelectedColumn());
                model.setSelectedColumn(-1);
                model.setCurrentDataSet(null);
                Notificator.getInstance().sendNotify(this,
                        DiagramEditor.COLUMN_ADDED);
                Notificator.getInstance().sendNotify(this,
                        DiagramEditor.DATASET_CHANGED);
                de.getStatusBar().setText("dataset closed");
            }
        }
    }
}
