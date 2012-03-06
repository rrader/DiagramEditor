package ua.romanrader.diagrameditor.model.csv;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: Observer, decorator
/**
 * Класс множества данных
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class DataSet extends ArrayList<Double> {
	//private ArrayList<Double> data;
	
//	public DataSet() {
//		this.data = new ArrayList<Double>();
//	}
//	
	private double startAngle = 0;
	
	/**
	 * Пустое множество данных
	 */
	public DataSet() {
		super();
	}
	
	/**
	 * Множество данных на основе двумерного массива
	 * @param data данные
	 */
	public DataSet(Double[][] data) {
		super(Arrays.asList(data[0]));
	}

	/**
	 * Сумма элементов
	 * @return
	 */
	public double sum() {
		double sum = 0;
		for(double v : this) {
			sum += v;
		}
		return sum;
	}
	
	/**
	 * Генерация углов
	 * @return массив углов
	 */
	public double[] generateAngles() {
		double sum = sum();
		double[] angles = new double[this.size()];
		for(int i=0; i<this.size(); i++) {
			angles[i] = (this.get(i) / sum) * 360;
		}
		return angles;
	}
	
	/**
	 * Установить угол для значения
	 * @param num номер
	 * @param nval угол
	 */
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

	/**
	 * Угол отсчета
	 * @return Угол отсчета
	 */
	public double getStartAngle() {
		return startAngle;
	}
	
	/**
	 * Установка угла отсчета
	 * @param startAngle угол
	 */
	public void setStartAngle(double startAngle) {
		this.startAngle = startAngle;
	}
	
	/**
	 * Добавление значения
	 */
	public void addValue() {
		this.add(sum()/5);
	}
}
