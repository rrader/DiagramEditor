package ua.romanrader.diagrameditor.util;

import java.util.Comparator;

import ua.romanrader.diagrameditor.model.csv.DataSet;

public class DataSetSizeComparator implements Comparator<DataSet> {

	@Override
	public int compare(DataSet arg0, DataSet arg1) {
		return (arg0.size() > arg1.size()) ? 1 : -1 ;
	}

}
