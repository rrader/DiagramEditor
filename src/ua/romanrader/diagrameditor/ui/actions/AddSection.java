package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

/**
 * Действие добавления секции в текущий дата-сет
 * @author romanrader
 *
 */
public class AddSection implements ActionListener {
    /**
     * Главное окно
     */
    private DiagramEditor de;

    /**
     * Конструктор действия
     * @param tde главное окно
     */
    public AddSection(final DiagramEditor tde) {
        this.de = tde;
    }

    /**
     * Выполнение действия
     * @param e действие
     */
    public final void actionPerformed(final ActionEvent e) {
        DataModel model = DataModel.getInstance();
        if (model.getCurrentDataSet() != null) {
            model.getCurrentDataSet().addValue();
            model.makeColors();
            Notificator.getInstance().sendNotify(this,
                    DiagramEditor.DATASET_CHANGED);
            de.getStatusBar().setText("Section added");
        } else {
            de.getStatusBar().setText("No dataset");
        }
    }
}
