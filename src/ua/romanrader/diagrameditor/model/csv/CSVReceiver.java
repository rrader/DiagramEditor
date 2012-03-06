package ua.romanrader.diagrameditor.model.csv;
//TODO: Strategy
/**
 * Интерфейс получателя CSV файла 
 * @author romanrader
 *
 */
public interface CSVReceiver {
	/**
	 * Получение прошло с ошибкой
	 * @param message сообщение об ошибке
	 */
	public void receivingFailed(String message);
	
	/**
	 * Получение завершено успешно
	 * @param data данные
	 */
	public void receivingFinished(DataSet data);
}
