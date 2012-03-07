package ua.romanrader.diagrameditor.util;

import java.util.Comparator;

import ua.romanrader.diagrameditor.model.csv.DataSet;

/**
 * Компаратор дата-сетов по размеру
 * @author romanrader
 *
 */
public class DataSetSizeComparator implements Comparator<DataSet> {

    @Override
    public final int compare(final DataSet arg0, final DataSet arg1) {
        if (arg0.size() > arg1.size()) {
            return 1;
        } else {
            return -1;
        }
    }

}
