package ua.romanrader.diagrameditor.csv;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * CSV Processor
 * @author romanrader
 *
 */
public class CSVProcessor {

	/**
	 * List
	 */
	private ArrayList<String> list;
	
	/**
	 * Getter for list
	 * @return list
	 */
	public ArrayList<String> getList() {
		return list;
	}

	/**
	 * Setter for list
	 * @param list list
	 */
	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	/**
	 * Serializes list to file
	 * @param file file
	 */
	public void serializeTo(File file, long csvDate) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeLong(csvDate);
			out.writeObject(list);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
                if (out != null) {
                	out.flush();
                	out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		}
	}
	
	/**
	 * Read serialized list from file
	 * @param file
	 * @throws CSVSerializedDateMismatch 
	 */
	@SuppressWarnings("unchecked")
	public void deserializeFrom(File file, long csvDate) throws CSVSerializedDateMismatch {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(file));
			long date = in.readLong();
			if (date != csvDate) {
				in.close();
				throw new CSVSerializedDateMismatch();
			}
			list = (ArrayList<String>) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			list = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			list = null;
		} finally {
            try {
                if (in != null) {
                	in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		}
	}

	/**
	 * Read from CSV file
	 * @param file
	 * @throws FileNotFoundException IOException 
	 */
	public void readCSV(File file) throws IOException, FileNotFoundException {
		list = new ArrayList<String>();
		BufferedReader bufReader = null;
		bufReader = new BufferedReader(new FileReader(file));
		String line = null;
		while (null != (line = bufReader.readLine())) {
			list.add(line);
		}
	}
	
	/**
	 * Write to CSV File
	 * @param file
	 */
	public void writeCSV(File file) throws IOException {
		PrintWriter f = null;
		try {
			f = new PrintWriter(new FileWriter(file));
			for (String el : list ) {
				f.println(el);
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (f != null) {
				f.flush();
				f.close();
			}
		}
	}
	
	/**
	 * Parsing
	 * @return parsed array
	 * @throws CSVParseException
	 */
	public DataSet parse() throws CSVParseException {
		Double[][] sl = new Double[1][];
		ArrayList<Double> parsed = new ArrayList<Double>();
		String line;
		for(int i=0; i<list.size(); i++) {
			line = list.get(i);
			try {
				StringTokenizer st = new StringTokenizer(line,",");
				while (st.hasMoreTokens())
				{
					parsed.add(new Double(Double.parseDouble(st.nextToken())));
				}
			} catch (NumberFormatException ex) {
				throw new CSVParseException("Number format wrong");
			}
		}
		System.out.println(parsed.size());
		sl[0] = parsed.toArray(new Double[parsed.size()]);
		return new DataSet(sl);
	}
}
