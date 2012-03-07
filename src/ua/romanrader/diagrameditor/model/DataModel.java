package ua.romanrader.diagrameditor.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.ui.DiagramEditor;
import ua.romanrader.diagrameditor.util.DataSetSizeComparator;
import ua.romanrader.diagrameditor.util.observer.Notificator;

import javax.swing.table.AbstractTableModel;

//TODO Model in MVC, Singleton and Adapter simultaneously - to documentation
/**
 * Модель данных. Синглтон, адаптер списка датасетов к AbstractTableModel
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public final class DataModel extends AbstractTableModel
implements List<DataSet> {

    /**
     * Состояние редактирования диаграммы
     * @author romanrader
     *
     */
    public enum State { StateNormal, StateRemoving };

    /**
     * Способ отображения - один датасет или все одновременно
     * @author romanrader
     *
     */
    public enum ViewState { Single, Simultaneously };

    /**
     * Дата-сеты
     */
    private ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
    /**
     * Множества цветов
     */
    private ArrayList<ArrayList<Color>> colorSets = null;

    /**
     * Список списков цветов для каждого дата-сета
     * @return список списка цветов
     */
    public ArrayList<ArrayList<Color>> getColorSets() {
        return colorSets;
    }

    /**
     * Установка списка списков цветов для каждого дата-сета
     * @param tcolorSets множества цветов
     */
    public void setColorSets(final ArrayList<ArrayList<Color>> tcolorSets) {
        this.colorSets = tcolorSets;
    }

    /** текущее множество данных */
    private DataSet currentDataSet = null;

    /** текущее множество цветов */
    private ArrayList<Color> currentColorSet = null;

    /** состояние редактирования */
    private State state = State.StateNormal;

    /** состояние отображения */
    private ViewState vstate = ViewState.Single;

    /** инстанс синглтона */
    private static DataModel instance = new DataModel();

    /** приватный конструктор */
    private DataModel() { }

    /**
     * Получение объекта модели данных
     * @return инстанс
     */
    public static DataModel getInstance() {
        return instance;
    }
    /** текущий столбец */
    private int selectedColumn = -1;

    /**
     * Текущий столбец
     * @return номер столбца
     */
    public int getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Установка текущего столбца
     * @param tselectedColumn номер столбца
     */
    public void setSelectedColumn(final int tselectedColumn) {
        this.selectedColumn = tselectedColumn;
    }

    /**
     * Сообщить об изменении данных таблице
     */
    public void dataSetUpdated() {
        fireTableStructureChanged();
    }

    /**
     * Список цветов для текущего столбца
     * @return список цветов
     */
    public ArrayList<Color> getCurrentColorSet() {
        return currentColorSet;
    }

    /**
     * Установить список цветов для текущего столбца
     * @param tcurrentColorSet список цветов для текущего столбца
     */
    public void setCurrentColorSet(final ArrayList<Color> tcurrentColorSet) {
        this.currentColorSet = tcurrentColorSet;
    }

    /**
     * Текущий датасет
     * @return Текущий датасет
     */
    public DataSet getCurrentDataSet() {
        return currentDataSet;
    }

    /**
     * Установить текущий датасет
     * @param tcurrentDataSet Новый датасет
     */
    public void setCurrentDataSet(final DataSet tcurrentDataSet) {
        this.currentDataSet = tcurrentDataSet;
        this.makeColors();
    }

    /**
     * Сгенерировать цвета
     */
    public void makeColors() {
        if (vstate == ViewState.Single) {
            if (currentDataSet == null) {
                setCurrentColorSet(null);
                return;
            }
            ArrayList<Color> colorSet;
            colorSet = new ArrayList<Color>();
            //Random numGen = new Random();
            for (int i = 0; i < currentDataSet.size(); i++) {
                colorSet.add(Color.getHSBColor(i / (float) currentDataSet.size()
                        , 1.0f, 1.0f));
            }
            setCurrentColorSet(colorSet);
        }
        if (vstate == ViewState.Simultaneously) {
            int i = 0;
            int count = 0;
            for (DataSet ds : dataSets) {
                count += ds.size();
            }
            colorSets = new ArrayList<ArrayList<Color>>();
            ArrayList<Color> colorSet;
            for (DataSet ds : dataSets) {
                colorSet = new ArrayList<Color>();
                for (int k = 0; k < ds.size(); k++) {
                    colorSet.add(Color.getHSBColor(i / (float) count,
                            1.0f, 1.0f));
                    i++;
                }
                colorSets.add(colorSet);
            }
        }
    }

    /**
     * Получить состояние редактирования
     * @return состояние
     */
    public State getState() {
        return state;
    }

    /**
     * Установить состояние редактирования
     * @param tstate состояние
     */
    public void setState(final State tstate) {
        this.state = tstate;
    }

    /**
     * Способ отображения
     * @return Способ отображения
     */
    public ViewState getVstate() {
        return vstate;
    }

    /**
     * Установить способ отображения
     * @param tvstate Способ отображения
     */
    public void setVstate(final ViewState tvstate) {
        this.vstate = tvstate;
    }

    // =================
    // AbstractTableModel

    @Override
    public int getColumnCount() {
        return dataSets.size();
    }

    @Override
    public int getRowCount() {
        if (dataSets.size() > 0) {
            return Collections.max(dataSets,
                    new DataSetSizeComparator()).size();
        }
        return 0;
    }

    @Override
    public boolean isCellEditable(final int row, final int cols) {
        return false;
    }

    @Override
    public Object getValueAt(final int arg0, final int arg1) {
        if (arg1 >= dataSets.size()) {
            return new Double(0.);
        }
        DataSet ds = dataSets.get(arg1);
        if (arg0 < ds.size()) {
            return ds.get(arg0);
        }
        return new Double(0.);
    }

    @Override
    public String getColumnName(final int column) {
        return "Dataset " + (column + 1);
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Double.class;
    }

    // =================
    // Collection

    @Override
    public boolean add(final DataSet e) {
        boolean b = dataSets.add(e);
        //makeColors();
        fireTableStructureChanged();
        Notificator.getInstance().sendNotify(this, DiagramEditor.COLUMN_ADDED);
        return b;
    }

    @Override
    public boolean addAll(final Collection<? extends DataSet> c) {
        boolean b = dataSets.addAll(c);
        //makeColors();
        fireTableStructureChanged();
        Notificator.getInstance().sendNotify(this, DiagramEditor.COLUMN_ADDED);
        return b;
    }

    @Override
    public void clear() {
        dataSets.clear();
        fireTableStructureChanged();
    }

    @Override
    public boolean contains(final Object o) {
        return dataSets.contains(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return dataSets.containsAll(c);
    }

    @Override
    public boolean isEmpty() {
        return dataSets.isEmpty();
    }

    @Override
    public Iterator<DataSet> iterator() {
        return dataSets.iterator();
    }

    @Override
    public boolean remove(final Object o) {
        boolean b = dataSets.remove(o);
        fireTableStructureChanged();
        return b;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean b = dataSets.removeAll(c);
        fireTableStructureChanged();
        return b;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        boolean b = dataSets.retainAll(c);
        fireTableStructureChanged();
        return b;
    }

    @Override
    public int size() {
        return dataSets.size();
    }

    @Override
    public Object[] toArray() {
        return dataSets.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return dataSets.toArray(a);
    }

    @Override
    public void add(final int arg0, final DataSet arg1) {
        dataSets.add(arg0, arg1);
        fireTableStructureChanged();
    }

    @Override
    public boolean addAll(final int arg0,
            final Collection<? extends DataSet> arg1) {
        boolean b = dataSets.addAll(arg0, arg1);
        fireTableStructureChanged();
        return b;
    }

    @Override
    public DataSet get(final int arg0) {
        return dataSets.get(arg0);
    }

    @Override
    public int indexOf(final Object arg0) {
        return dataSets.indexOf(arg0);
    }

    @Override
    public int lastIndexOf(final Object arg0) {
        return dataSets.lastIndexOf(arg0);
    }

    @Override
    public ListIterator<DataSet> listIterator() {
        return dataSets.listIterator();
    }

    @Override
    public ListIterator<DataSet> listIterator(final int arg0) {
        return dataSets.listIterator(arg0);
    }

    @Override
    public DataSet remove(final int arg0) {
        return dataSets.remove(arg0);
    }

    @Override
    public DataSet set(final int arg0, final DataSet arg1) {
        DataSet ds = dataSets.set(arg0,  arg1);
        fireTableDataChanged();
        return ds;
    }

    @Override
    public List<DataSet> subList(final int arg0, final int arg1) {
        return dataSets.subList(arg0, arg1);
    }
}
