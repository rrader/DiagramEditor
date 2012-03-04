package ua.romanrader.diagrameditor.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ua.romanrader.diagrameditor.csv.DataSet;
import ua.romanrader.diagrameditor.util.DataSetSizeComparator;

import javax.swing.table.AbstractTableModel;

//TODO Model in MVC, Singleton and Adapter simultaneously - to documentation
@SuppressWarnings("serial")
public class CSVTableModel extends AbstractTableModel implements List<DataSet> {

	private ArrayList<DataSet> dataSets = new ArrayList<DataSet>();

	private static CSVTableModel instance = new CSVTableModel();
	private CSVTableModel() {}
	
	public static CSVTableModel getInstance() {
		return instance;
	}
	
	public void addDataSet(DataSet ds) {
		dataSets.add(ds);
	}
	
// =================
// AbstractTableModel

	@Override
	public int getColumnCount() {
		return dataSets.size();
	}

	@Override
	public int getRowCount() {
		return Collections.max(dataSets, new DataSetSizeComparator()).size();
	}

	@Override
	public boolean isCellEditable(int row,int cols) {
		return false;                                                                                         
    }
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		DataSet ds = dataSets.get(arg0);
		if (arg1<ds.size()) {
			return ds.get(arg1);
		}
		return 0.;
	}

	@Override
	public String getColumnName(int column) {
		return "Dataset "+column;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Double.class;
	}

// =================
// Collection
	
	@Override
	public boolean add(DataSet e) {
		return dataSets.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends DataSet> c) {
		return dataSets.addAll(c);
	}

	@Override
	public void clear() {
		dataSets.clear();
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
		return dataSets.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return dataSets.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return dataSets.retainAll(c);
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
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends DataSet> arg1) {
		return dataSets.addAll(arg0, arg1);
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
		return dataSets.set(arg0,  arg1);
	}

	@Override
	public List<DataSet> subList(int arg0, int arg1) {
		return dataSets.subList(arg0, arg1);
	}
}
