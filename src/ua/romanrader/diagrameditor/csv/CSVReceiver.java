package ua.romanrader.diagrameditor.csv;
//Strategy
public interface CSVReceiver {
	public void receivingFailed(String message);
	public void receivingFinished(DataSet data);
}
