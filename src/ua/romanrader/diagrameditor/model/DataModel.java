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
@SuppressWarnings("serial")
public class DataModel extends AbstractTableModel implements List<DataSet> {

	private ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
	
	private ArrayList<Color> currentColorSet = null;
	
	private static DataModel instance = new DataModel();
	private DataModel() {}
	
	public static DataModel getInstance() {
		return instance;
	}
	private int selectedColumn = -1;
	
	public int getSelectedColumn() {
		return selectedColumn;
	}

	public void setSelectedColumn(int selectedColumn) {
		this.selectedColumn = selectedColumn;
	}
	
	public void dataSetUpdated() {
		fireTableStructureChanged();
	}
// =================
// AbstractTableModel

	@Override
	public int getColumnCount() {
		return dataSets.size();
	}

	@Override
	public int getRowCount() {
		if (dataSets.size()>0) {
			return Collections.max(dataSets, new DataSetSizeComparator()).size();
		}
		return 0;
	}

	@Override
	public boolean isCellEditable(int row,int cols) {
		return false;
    }
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (arg1>=dataSets.size()) {
			return new Double(0.);
		}
		DataSet ds = dataSets.get(arg1);
		if (arg0<ds.size()) {
			return ds.get(arg0);
		}
		return new Double(0.);
	}

	@Override
	public String getColumnName(int column) {
		return "Dataset "+(column+1);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Double.class;
	}
	
// =================
// Collection
	
	@Override
	public boolean add(DataSet e) {
		boolean b = dataSets.add(e); 
		fireTableStructureChanged();
		Notificator.getInstance().sendNotify(this, DiagramEditor.COLUMN_ADDED);
		return b;
	}

	@Override
	public boolean addAll(Collection<? extends DataSet> c) {
		boolean b = dataSets.addAll(c);
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
	public boolean contains(Object o) {
		return dataSets.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
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
	public boolean remove(Object o) {
		boolean b = dataSets.remove(o);
		fireTableStructureChanged();
		return b;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean b = dataSets.removeAll(c);
		fireTableStructureChanged();
		return b;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
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
	public <T> T[] toArray(T[] a) {
		return dataSets.toArray(a);
	}

	@Override
	public void add(int arg0, DataSet arg1) {
		dataSets.add(arg0, arg1);
		fireTableStructureChanged();
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends DataSet> arg1) {
		boolean b = dataSets.addAll(arg0, arg1);
		fireTableStructureChanged();
		return b;
	}

	@Override
	public DataSet get(int arg0) {
		return dataSets.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return dataSets.indexOf(arg0);
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return dataSets.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<DataSet> listIterator() {
		return dataSets.listIterator();
	}

	@Override
	public ListIterator<DataSet> listIterator(int arg0) {
		return dataSets.listIterator(arg0);
	}

	@Override
	public DataSet remove(int arg0) {
		return dataSets.remove(arg0);
	}

	@Override
	public DataSet set(int arg0, DataSet arg1) {
		DataSet ds = dataSets.set(arg0,  arg1);
		fireTableDataChanged();
		return ds;
	}

	@Override
	public List<DataSet> subList(int arg0, int arg1) {
		return dataSets.subList(arg0, arg1);
	}

	public ArrayList<Color> getCurrentColorSet() {
		return currentColorSet;
	}

	public void setCurrentColorSet(ArrayList<Color> currentColorSet) {
		this.currentColorSet = currentColorSet;
	}
}
