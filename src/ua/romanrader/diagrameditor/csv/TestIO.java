package ua.romanrader.diagrameditor.csv;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Test class for CSV parser
 * @author romanrader
 *
 */
public class TestIO {
	private static Double[][] data = null;
	
	/**
	 * Reads CSV file from keyboard
	 * @param p processor
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void readCSV(CSVProcessor p) throws FileNotFoundException, IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter filename:");
		String fileName = in.nextLine();
		
		File f = new File(fileName);
		p.readCSV(f);
	}
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		File f = new File("serialized.dat");
		final CSVProcessor p = new CSVProcessor();
		if (f.exists()) {
			p.deserializeFrom(f);
		} else {
			boolean ok = false;
			Exception ex = null;
			for (int i=0;i<3;i++) {
				try {
					readCSV(p);
					ok = true;
				} catch (FileNotFoundException e) {
					ex = e;
					ok = false;
				} catch (IOException e) {
					ex = e;
					ok = false;
				}
				if (ok) {
					break;
				}
			}
			if (!ok) {
				throw ex;
			}
		}
		p.serializeTo(f);
		
		Thread parsing = new Thread(new Runnable() {
			public void run() {
				try {
					Double[][] parsed = p.parse();
					synchronized(TestIO.class) {
						data = parsed;
					}
					TestIO.parsingDidComplete();
				} catch (CSVParseException e) {
					data = null;
					TestIO.parsingDidFailed();
				}
			}
		});
		parsing.start();
		
		parsing.join();
	}
	
	/**
	 * Callback method, calls when parsing completed successfully
	 */
	public static void parsingDidComplete() {
		System.out.println("complete:");
		for (Double d : data[0]) {
			System.out.print(d + ", ");
		}
	}
	
	/**
	 * Callback method, calls when parsing fails
	 */
	public static void parsingDidFailed() {
		System.out.println("failed");
	}
}
