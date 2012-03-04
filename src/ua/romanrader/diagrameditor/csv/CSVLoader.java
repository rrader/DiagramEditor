package ua.romanrader.diagrameditor.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVLoader {
	private static String changeExtension(String originalName, String newExtension) {
	    int lastDot = originalName.lastIndexOf(".");
	    if (lastDot != -1) {
	        return originalName.substring(0, lastDot) + newExtension;
	    } else {
	        return originalName + newExtension;
	    }
	}
	
	// test is there .dat file, if not then create
	//TODO to dat file datetime of .csv
	public void LoadFile(final String file, final CSVReceiver receiver) {
		Thread parsing = new Thread(new Runnable() {
			public void run() {
				File csvFile = new File(file);
				File datFile = new File(changeExtension(file, ".dat"));
				CSVProcessor p = new CSVProcessor();
				
				if (datFile.exists()) {
					try {
						p.deserializeFrom(datFile, csvFile.lastModified());
					} catch (CSVSerializedDateMismatch e) {
						try {
							p.readCSV(csvFile);
						} catch (FileNotFoundException e1) {
							receiver.receivingFailed("File not found");
						} catch (IOException e1) {
							receiver.receivingFailed("I/O error");
						}
					}
				}
				
				try {
					DataSet parsed = p.parse();
					receiver.receivingFinished(parsed);
				} catch (CSVParseException e) {
					receiver.receivingFailed("CSV validation failed");
				}
			}
		});
		parsing.start();
	}
}
