package ua.romanrader.diagrameditor.csv;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: Observer, decorator
@SuppressWarnings("serial")
public class DataSet extends ArrayList<Double> {
	//private ArrayList<Double> data;
	
//	public DataSet() {
//		this.data = new ArrayList<Double>();
//	}
//	
	public DataSet(Double[][] data) {
		super(Arrays.asList(data[0]));
	}

	public double[] generateAngles() {
		double sum = 0;
		for(double v : this) {
			sum += v;
		}
		double[] angles = new double[this.size()];
		for(int i=0; i<this.size(); i++) {
			angles[i] = this.get(i) / sum;
		}
		return angles;
	}
//	
//	@Override
//	public boolean add(Double e) {
//		//notifyObservers(e);
//		return this.data.add(e);
//	}
//
//	@Override
//	public boolean addAll(Collection<? extends Double> c) {
//		return this.data.addAll(c);
//	}
//
//	@Override
//	public void clear() {
//		this.data.clear();
//	}
//
//	@Override
//	public boolean contains(Object o) {
//		return this.data.contains(o);
//	}
//
//	@Override
//	public boolean containsAll(Collection<?> c) {
//		return this.data.containsAll(c);
//	}
//
//	@Override
//	public boolean isEmpty() {
//		return this.data.isEmpty();
//	}
//
//	@Override
//	public Iterator<Double> iterator() {
//		return this.data.iterator();
//	}
//
//	@Override
//	public boolean remove(Object o) {
//		return this.data.remove(o);
//	}
//
//	@Override
//	public boolean removeAll(Collection<?> c) {
//		return this.data.removeAll(c);
//	}
//
//	@Override
//	public boolean retainAll(Collection<?> c) {
//		return this.data.retainAll(c);
//	}
//
//	@Override
//	public int size() {
//		return this.data.size();
//	}
//
//	@Override
//	public Object[] toArray() {
//		return this.data.toArray();
//	}
//
//	@Override
//	public <T> T[] toArray(T[] a) {
//		return this.data.toArray(a);
//	}
//
//	@Override
//	public void add(int arg0, Double arg1) {
//		this.data.add(arg0,arg1);
//	}
//
//	@Override
//	public boolean addAll(int arg0, Collection<? extends Double> arg1) {
//		return this.data.addAll(arg0,arg1);
//	}
//
//	@Override
//	public Double get(int arg0) {
//		return this.data.get(arg0);
//	}
//
//	@Override
//	public int indexOf(Object arg0) {
//		return this.data.indexOf(arg0);
//	}
//
//	@Override
//	public int lastIndexOf(Object arg0) {
//		return this.data.lastIndexOf(arg0);
//	}
//
//	@Override
//	public ListIterator<Double> listIterator() {
//		return this.data.listIterator();
//	}
//
//	@Override
//	public ListIterator<Double> listIterator(int arg0) {
//		return this.data.listIterator(arg0);
//	}
//
//	@Override
//	public Double remove(int arg0) {
//		return this.data.remove(arg0);
//	}
//
//	@Override
//	public Double set(int arg0, Double arg1) {
//		return this.data.set(arg0,arg1);
//	}
//
//	@Override
//	public List<Double> subList(int arg0, int arg1) {
//		return this.data.subList(arg0, arg1);
//	}
}
