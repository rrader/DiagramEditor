package ua.romanrader.diagrameditor.model.csv;

import java.util.ArrayList;
import java.util.Arrays;

//Observer, decorator
/**
 * Класс множества данных
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class DataSet extends ArrayList<Double> {
    /**
     * Начальный угол
     */
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
    public DataSet(final Double[][] data) {
        super(Arrays.asList(data[0]));
    }

    /**
     * Сумма элементов
     * @return сумма
     */
    public final double sum() {
        double sum = 0;
        for (double v : this) {
            sum += v;
        }
        return sum;
    }

    /**
     * Генерация углов
     * @return массив углов
     */
    public final double[] generateAngles() {
        double sum = sum();
        double[] angles = new double[this.size()];
        final double fullCircle = 360.;
        for (int i = 0; i < this.size(); i++) {
            angles[i] = (this.get(i) / sum) * fullCircle;
        }
        return angles;
    }

    /**
     * Установить угол для значения
     * @param num номер
     * @param nval угол
     */
    public final void setAngle(final int num, final double nval) {
        double val = nval - startAngle;
        double sum = sum();

        double prev = 0;

        final double fullCircle = 360.;
        for (int i = 0; i < num; i++) {
            prev += (this.get(i) / sum) * fullCircle;
        }

        if (val < 0) {
            val += fullCircle;
        }
        if (val < prev) {
            val += fullCircle;
        }

        double newVal = val - prev;
        int second;
        if (num + 1 >= this.size()) {
            second = 0;
        } else {
            second = num + 1;
        }

        double newSecondVal = this.get(second)
                - (newVal / fullCircle * sum - this.get(num));

        if (newVal < 0 || newSecondVal < 0) {
            return;
        }

        this.set(num, newVal / fullCircle * sum);
        this.set(second, newSecondVal);
        if (second == 0) {
            startAngle = nval;
        }
    }

    /**
     * Угол отсчета
     * @return Угол отсчета
     */
    public final double getStartAngle() {
        return startAngle;
    }

    /**
     * Установка угла отсчета
     * @param tstartAngle угол
     */
    public final void setStartAngle(final double tstartAngle) {
        this.startAngle = tstartAngle;
    }

    /**
     * Добавление значения
     */
    public final void addValue() {
        final float part = 10.f;
        this.add(sum() / part);
    }
}
