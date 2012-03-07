package ua.romanrader.diagrameditor.model.csv;
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
    public final ArrayList<String> getList() {
        return list;
    }

    /**
     * Setter for list
     * @param tlist list
     */
    public final void setList(final ArrayList<String> tlist) {
        this.list = tlist;
    }

    /**
     * Serializes list to file
     * @param file файл
     * @param csvDate дата csv-файла
     */
    public final void serializeTo(final File file, final long csvDate) {
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
     * @param file файл
     * @param csvDate дата csv
     * @throws CSVSerializedDateMismatch разные даты
     * @throws IOException ввод/вывод
     * @throws ClassNotFoundException ошибочные данные класса
     */
    @SuppressWarnings("unchecked")
    public final void deserializeFrom(final File file, final long csvDate)
            throws CSVSerializedDateMismatch, IOException,
            ClassNotFoundException {
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
            list = null;
            throw e;
        } catch (ClassNotFoundException e) {
            list = null;
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Read from CSV file
     * @param file файл
     * @throws IOException ввод/вывод
     */
    public final void readCSV(final File file)
            throws IOException {
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
     * @param file файл
     * @throws IOException ввод/вывод
     */
    public final void writeCSV(final File file) throws IOException {
        PrintWriter f = null;
        try {
            f = new PrintWriter(new FileWriter(file));
            for (String el : list) {
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
     * @throws CSVParseException ошибка формата
     */
    public final DataSet parse() throws CSVParseException {
        Double[][] sl = new Double[1][];
        ArrayList<Double> parsed = new ArrayList<Double>();
        String line;
        for (int i = 0; i < list.size(); i++) {
            line = list.get(i);
            try {
                StringTokenizer st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {
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

    /**
     * Изменить расширение файла
     * @param originalName путь (или имя)
     * @param newExtension новое расширение
     * @return новый путь (имя)
     */
    private static String changeExtension(final String originalName,
            final String newExtension) {
        int lastDot = originalName.lastIndexOf(".");
        if (lastDot != -1) {
            return originalName.substring(0, lastDot) + newExtension;
        } else {
            return originalName + newExtension;
        }
    }

    /**
     * Загрузка CSV из файла в другой нити
     * @param file файл
     * @param receiver получатель
     */
    public static void loadFile(final String file, final CSVReceiver receiver) {
        Thread parsing = new Thread(new Runnable() {
            public void run() {
                File csvFile = new File(file);
                File datFile = new File(changeExtension(file, ".dat"));
                CSVProcessor p = new CSVProcessor();

                boolean read = false;
                if (datFile.exists()) {
                    try {
                        p.deserializeFrom(datFile, csvFile.lastModified());
                        System.out.println("loaded from serialized");
                        read = true;
                    } catch (CSVSerializedDateMismatch e) {
                        read = false;
                        System.out.println("Old .dat file");
                    } catch (IOException e) {
                        read = false;
                        System.out.println("IO exception while reading "
                                    + ".dat file");
                    } catch (ClassNotFoundException e) {
                        read = false;
                        System.out.println("Wrong format of .dat file");
                    }
                }
                if (!read) {
                    try {
                        p.readCSV(csvFile);
                        System.out.println("loaded from csv");
                    } catch (FileNotFoundException e1) {
                        receiver.receivingFailed("File not found");
                        return;
                    } catch (IOException e1) {
                        receiver.receivingFailed("I/O error");
                        return;
                    }
                }

                try {
                    DataSet parsed = p.parse();
                    receiver.receivingFinished(parsed);
                } catch (CSVParseException e) {
                    receiver.receivingFailed("CSV validation failed");
                    return;
                }

                p.serializeTo(datFile, csvFile.lastModified());
            }
        });
        parsing.start();
    }

    /**
     * Сохранение файла
     * @param file путь
     * @param ds дата-сет
     * @throws IOException i/o
     */
    public static void saveFile(final String file, final DataSet ds)
            throws IOException {
        CSVProcessor p = new CSVProcessor();
        String res = "";
        for (int i = 0; i < ds.size() - 1; i++) {
            res = res.concat(ds.get(i).toString());
            res = res.concat(", ");
        }
        res = res.concat(ds.get(ds.size() - 1).toString());
        ArrayList<String> lst = new ArrayList<String>();
        lst.add(res);
        p.setList(lst);
        p.writeCSV(new File(file));
    }
}
