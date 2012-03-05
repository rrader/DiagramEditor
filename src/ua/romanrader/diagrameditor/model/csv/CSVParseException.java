package ua.romanrader.diagrameditor.model.csv;
import java.io.IOException;

/**
 * Exception during CSV parsing
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class CSVParseException extends IOException {
	/**
	 * Constructor without message
	 */
	public CSVParseException() {
		super();
	}
	
	/**
	 * Constructor without message
	 */
	public CSVParseException(String text) {
		super(text);
	}
}
