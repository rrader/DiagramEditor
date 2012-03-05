package ua.romanrader.diagrameditor.model.csv;
//Strategy
public interface CSVReceiver {
	public void receivingFailed(String message);
	public void receivingFinished(DataSet data);
}
