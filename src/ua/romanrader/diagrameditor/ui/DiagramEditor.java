package ua.romanrader.diagrameditor.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.CSVProcessor;
import ua.romanrader.diagrameditor.model.csv.CSVReceiver;
import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.ui.actions.AddSection;
import ua.romanrader.diagrameditor.ui.actions.CloseDataset;
import ua.romanrader.diagrameditor.ui.actions.ExportToJPEG;
import ua.romanrader.diagrameditor.ui.actions.New;
import ua.romanrader.diagrameditor.ui.actions.Open;
import ua.romanrader.diagrameditor.ui.actions.RemoveSection;
import ua.romanrader.diagrameditor.ui.actions.Save;
import ua.romanrader.diagrameditor.ui.actions.SetViewState;

/**
 * Главное окно программы
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class DiagramEditor extends JFrame {
	/**
	 * Имя файла, который открывается при запуске приложения
	 */
	public static final String DEFAULT_CSV_FILE = "file.csv";
	
	/**
	 * Идентификатор сообщения о изменении текущего множества данных
	 */
	public static final String NEW_DATASET_NOTIFICATION = "DENewDatasetSelected";
	
	/**
	 * Идентификатор сообщения о добавления столбца в таблицу
	 */
	public static final String COLUMN_ADDED = "DEColumnAdded";
	
	/**
	 * Идентификатор сообщения о изменениях в множестве данных
	 */
	public static final String DATASET_CHANGED = "DEDataSetChanged";
	
	/**
	 * Идентификатор сообщения о изменении вида отображения
	 */
	public static final String VIEWSTATE_CHANGED = "DEViewStateChanged";
	
	private JTable dataTable;
	private DiagramView diagramView;
	private StatusBar statusBar;
	
	/**
	 * Компонент диаграммы
	 * @return Компонент диаграммы
	 */
	public DiagramView getDiagramView() {
		return diagramView;
	}

	/**
	 * Строка состояния
	 * @return Строка состояния
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * Конструктор окна
	 */
	public DiagramEditor() {
		super("Diagram Editor");
		setTitle("Diagram Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);

		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
        this.makeToolbarAndMenu();

		//Table
		dataTable = new CSVTable();
		dataTable.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(new JScrollPane(dataTable));
		
		//Diagram
		diagramView = new DiagramView();
		diagramView.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(diagramView);
		
		getContentPane().add(panel, BorderLayout.CENTER);

		statusBar = new StatusBar();
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);

		//Load default file.csv
		CSVProcessor.LoadFile(DEFAULT_CSV_FILE, new CSVReceiver(){

			@Override
			public void receivingFailed(String message) {
				statusBar.setText("opening failed");
			}

			@Override
			public void receivingFinished(DataSet data) {
				DataModel.getInstance().add(data);
				statusBar.setText("opened successfully");
			}
			
		});
		//model.add();
	}
	
	/**
	 * Создание строки меню и панели инструментов
	 */
	public void makeToolbarAndMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu dataSet = new JMenu("Data set");
        JMenu diaView = new JMenu("Diagram view");
        menubar.add(file);
        menubar.add(dataSet);
        menubar.add(diaView);
        setJMenuBar(menubar);
        
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        
        JButton button = new JButton("New");
        toolbar.add(button);
        ActionListener al = new New();
        button.addActionListener(al);
        
        JMenuItem item = new JMenuItem("New");
        item.addActionListener(al);
        file.add(item);
        
        button = new JButton("Save");
        toolbar.add(button);
        al = new Save(this);
        button.addActionListener(al);
        item = new JMenuItem("Save");
        item.addActionListener(al);
        file.add(item);
        
        
        button = new JButton("Open");
        toolbar.add(button);
        al = new Open(this);
        button.addActionListener(al);
        item = new JMenuItem("Open");
        item.addActionListener(al);
        file.add(item);
        
        button = new JButton("Close dataset");
        toolbar.add(button);
        al = new CloseDataset(this);
        button.addActionListener(al);
        item = new JMenuItem("Close dataset");
        item.addActionListener(al);
        file.add(item);
        
        file.addSeparator();
        item = new JMenuItem("Quit");
        item.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
        	
        });
        file.add(item);
        
        toolbar.addSeparator();
        button = new JButton("Add section");
        toolbar.add(button);
        al = new AddSection(this);
        button.addActionListener(al);
        item = new JMenuItem("Add section");
        item.addActionListener(al);
        dataSet.add(item);
        
        JToggleButton tbutton = new JToggleButton("Remove section");
        JCheckBoxMenuItem citem = new JCheckBoxMenuItem("Remove section");
        toolbar.add(tbutton);
        al = new RemoveSection(this, tbutton, citem);
        tbutton.addActionListener(al);
        citem.addActionListener(al);
        dataSet.add(citem);
        
        toolbar.addSeparator();
        
        button = new JButton("Export to JPEG");
        toolbar.add(button);
        al = new ExportToJPEG(this);
        button.addActionListener(al);
        item = new JMenuItem("Export to JPEG");
        item.addActionListener(al);
        diaView.add(item);
        
        toolbar.addSeparator();
        
        tbutton = new JToggleButton("Single/Simultaneously");
        citem = new JCheckBoxMenuItem("Single/Simultaneously");
        toolbar.add(tbutton);
        al = new SetViewState(this, tbutton, citem);
        tbutton.addActionListener(al);
        citem.addActionListener(al);
        diaView.add(citem);
        
        add(toolbar,BorderLayout.PAGE_START);
	}

	/**
	 * Точка входа
	 * @param args аргументы командной строки
	 */
    public static void main(String[] args) {
    	DiagramEditor frame = new DiagramEditor();
        frame.setVisible(true);
    }
}
