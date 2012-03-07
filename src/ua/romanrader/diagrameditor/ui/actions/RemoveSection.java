package ua.romanrader.diagrameditor.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.ui.DiagramEditor;

/**
 * Действие установки режима удаления секции
 * @author romanrader
 *
 */
public class RemoveSection implements ActionListener {
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
    public RemoveSection(final DiagramEditor tde,
            final JToggleButton tbutton,
            final JCheckBoxMenuItem titem) {
        this.de = tde;
        this.button = tbutton;
        this.item = titem;
    }

    /**
     * Выполнение действия
     * @param e действие
     */
    public final void actionPerformed(final ActionEvent e) {
        if (DataModel.getInstance().getVstate()
                == DataModel.ViewState.Simultaneously) {
            return;
        }
        System.out.println("remove section");
        if (e.getSource() instanceof JToggleButton) {
            DataModel model = DataModel.getInstance();
            model.setState(((JToggleButton) e.getSource()).isSelected()
                  ? DataModel.State.StateRemoving
                  : DataModel.State.StateNormal);
            item.setSelected(((JToggleButton) e.getSource()).isSelected());

            de.getStatusBar().setText(
                    ((JToggleButton) e.getSource()).isSelected()
                    ? "Edit mode: removing" : "Edit mode: creating");
        }
        if (e.getSource() instanceof JCheckBoxMenuItem) {
            DataModel model = DataModel.getInstance();
            model.setState(((JCheckBoxMenuItem) e.getSource()).isSelected()
                    ? DataModel.State.StateRemoving : DataModel.State.StateNormal );

            button.setSelected(
                    ((JCheckBoxMenuItem) e.getSource()).isSelected());

            de.getStatusBar().setText(
                    ((JCheckBoxMenuItem) e.getSource()).isSelected()
                    ? "Edit mode: removing" : "Edit mode: creating");
        }
    }
}