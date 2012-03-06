package ua.romanrader.diagrameditor.model.csv;

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
	private double startAngle = 0;
	
	public DataSet() {
		super();
	}
	
	public DataSet(Double[][] data) {
		super(Arrays.asList(data[0]));
	}

	public double sum() {
		double sum = 0;
		for(double v : this) {
			sum += v;
		}
		return sum;
	}
	
	public double[] generateAngles() {
		double sum = sum();
		double[] angles = new double[this.size()];
		for(int i=0; i<this.size(); i++) {
			angles[i] = (this.get(i) / sum) * 360;
		}
		return angles;
	}
	
	public void setAngle(int num, double nval) {
		double val = nval - startAngle;
		double sum = sum();

		double prev = 0;
		for (int i=0; i<num; i++) {
			prev += (this.get(i) / sum) * 360;
		}

		if (val<0) {
			val += 360;
		}
		if (val<prev) {
			val += 360;
		}

		double newVal = val - prev;
		int second = (num+1 >= this.size())?0:num+1;
		double newSecondVal = this.get(second) - (newVal/360.*sum - this.get(num));
		
		if (newVal < 0 || newSecondVal < 0) {
			return;
		}

		this.set(num, newVal/360.*sum);
		this.set(second, newSecondVal);
		if (second == 0) {
			startAngle = nval;
		}
	}

	public double getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(double startAngle) {
		this.startAngle = startAngle;
	}
	
	public void addValue() {
		this.add(sum()/5);
	}
}
