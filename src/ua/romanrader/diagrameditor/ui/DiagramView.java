package ua.romanrader.diagrameditor.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import ua.romanrader.diagrameditor.csv.DataSet;
import ua.romanrader.diagrameditor.util.observer.Notification;
import ua.romanrader.diagrameditor.util.observer.Notificator;
import ua.romanrader.diagrameditor.util.observer.Observer;

@SuppressWarnings("serial")
public class DiagramView extends JPanel implements Observer {
	private DataSet dataSet = null;
	
	public DiagramView() {
		Notificator.getInstance().addObserver(this, DiagramEditor.NEW_DATASET_NOTIFICATION);		
	}
	
    public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public void paintComponent(Graphics2D g) {
        super.paintComponent(g);
        
        if (dataSet == null) {
        	return;
        }
        
        double[] angles = dataSet.generateAngles();
        double angle = 0;
        ArrayList<Arc2D.Double> arcs = new ArrayList<Arc2D.Double>();
        Arc2D.Double arc;
        for (int i=0; i<dataSet.size(); i++) {
        	arc = new Arc2D.Double(0.0, 0.0, getWidth(), getHeight(), angle, angles[i], Arc2D.PIE);
        	g.draw(arc);
        	arcs.add(arc); 
        	angle += angles[i];
        }
        //g.draw
    }
    
    public Image toImage(String filename) {
    	if (dataSet == null) {
        	return null;
        }
    	
    	BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.clearRect(0, 0, getWidth(), getHeight() );
        this.paint(g2);
        return bi;
//        try
//        {
//        ImageIO.write1(bi, "jpg", new File(filename+".jpg"));
//        }
//        catch(IOException ioe)
//        {
//        System.err.println("write: " + ioe.getMessage());
//        }
//        g2.dispose();
    }

	@Override
	public void notificationReceived(Notification notification) {
		if (notification.getName().compareTo(DiagramEditor.NEW_DATASET_NOTIFICATION) == 0) {
			if ((DataSet)notification.getUserData() != this.dataSet) {
				this.dataSet = (DataSet)notification.getUserData();
				this.invalidate();
			}
		}
	}
    
}
