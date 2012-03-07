package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.observer.Notificator;

/**
 * Действие установки режима отображения
 * @author romanrader
 *
 */
public class SetViewState implements ActionListener {
    /**
     * Главное окно
     */
    private DiagramEditor de;
    /**
     * Кнопка
     */
    private JToggleButton button;
    /**
     * Пункт меню
     */
    private JCheckBoxMenuItem item;

    /**
     * Конструктор действия
     * @param tde главное окно
     * @param tbutton кнопка на панели инструментов
     * @param titem пункт меню
     */
    public SetViewState(final DiagramEditor tde,
            final JToggleButton tbutton, final JCheckBoxMenuItem titem) {
        this.de = tde;
        this.button = tbutton;
        this.item = titem;
    }

    /**
     * Выполнение действия
     * @param e действие
     */
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() instanceof JToggleButton) {
            DataModel model = DataModel.getInstance();
            model.setVstate(((JToggleButton) e.getSource()).isSelected()
                    ? DataModel.ViewState.Simultaneously
                    : DataModel.ViewState.Single);
            item.setSelected(((JToggleButton) e.getSource()).isSelected());

            de.getStatusBar().setText(
                    ((JToggleButton) e.getSource()).isSelected()
                    ? "View mode: simultaneously" : "View mode: single");
            Notificator.getInstance().sendNotify(de,
                    DiagramEditor.VIEWSTATE_CHANGED);
        }
        if (e.getSource() instanceof JCheckBoxMenuItem) {
            DataModel model = DataModel.getInstance();
            model.setVstate(
                    ((JCheckBoxMenuItem) e.getSource()).isSelected()
                    ? DataModel.ViewState.Simultaneously
                    : DataModel.ViewState.Single);
            button.setSelected(
                    ((JCheckBoxMenuItem) e.getSource()).isSelected());

            de.getStatusBar().setText(
                    ((JCheckBoxMenuItem) e.getSource()).isSelected()
                    ? "View mode: simultaneously" : "View mode: single");
            Notificator.getInstance().sendNotify(de,
                    
                    DiagramEditor.VIEWSTATE_CHANGED);
        }
    }
}
