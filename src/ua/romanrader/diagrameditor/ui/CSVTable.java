package ua.romanrader.diagrameditor.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.util.observer.Notification;
import ua.romanrader.diagrameditor.util.observer.Notificator;
import ua.romanrader.diagrameditor.util.observer.Observer;

/**
 * Класс таблицы с данными
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class CSVTable extends JTable implements Observer {

    /**
     * Конструктор таблицы
     */
    public CSVTable() {
        super();
        setModel(DataModel.getInstance());
        Notificator.getInstance().addObserver(this, DiagramEditor.COLUMN_ADDED);
        Notificator.getInstance().addObserver(this,
                DiagramEditor.DATASET_CHANGED);
        Notificator.getInstance().addObserver(this,
                DiagramEditor.VIEWSTATE_CHANGED);

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                CSVTable table = CSVTable.this;
                table.setRendererForAllColumns();
                int col = table.columnAtPoint(e.getPoint());
                DataModel.getInstance().setSelectedColumn(col);
                DataSet ds = DataModel.getInstance().get(col);
                DataModel.getInstance().setCurrentDataSet(ds);
                Notificator.getInstance().sendNotify(table,
                        DiagramEditor.NEW_DATASET_NOTIFICATION, ds);
                CSVTable.this.revalidate();
                CSVTable.this.repaint();
            }
        });

        this.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(final TableModelEvent e) {
                CSVTable.this.setRendererForAllColumns();
            }
        });
    }

    /**
     * Установить рендерер для всех столбцов
     */
    public final void setRendererForAllColumns() {
        for (int r = 0; r < this.getColumnCount(); r++) {
            TableColumn col = this.getColumnModel().getColumn(r);
            if ((col.getCellRenderer() == null)
                || (col.getCellRenderer().getClass() != CellRenderer.class)) {

                col.setCellRenderer(new CellRenderer());
            }
        }
    }

    /**
     * Получение сообщения
     * @param notification сообщение
     */
    @Override
    public final void notificationReceived(final Notification notification) {
        if (notification.getName().compareTo(DiagramEditor.COLUMN_ADDED) == 0) {
            this.setRendererForAllColumns();
        } else
            if (notification.getName().compareTo(
                    DiagramEditor.DATASET_CHANGED) == 0) {
                DataModel.getInstance().dataSetUpdated();
                this.setRendererForAllColumns();
                CSVTable.this.revalidate();
                CSVTable.this.repaint();
            }
        if (notification.getName().compareTo(
                DiagramEditor.VIEWSTATE_CHANGED) == 0) {
            DataModel.getInstance().dataSetUpdated();
            this.setRendererForAllColumns();
            CSVTable.this.revalidate();
            CSVTable.this.repaint();
        }
    }

}
