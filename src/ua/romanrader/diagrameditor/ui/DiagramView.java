package ua.romanrader.diagrameditor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.util.observer.Notification;
import ua.romanrader.diagrameditor.util.observer.Notificator;
import ua.romanrader.diagrameditor.util.observer.Observer;

@SuppressWarnings("serial")
public class DiagramView extends JPanel implements Observer, MouseListener, MouseMotionListener  {
	private DataSet dataSet = null;
	
	ArrayList<Arc2D.Double> arcs = null;
	ArrayList<Arc2D.Double> activeArcs = null;
	private float margin;
	private float size;
	private int currentMoving = -1;
	public DiagramView() {
		Notificator.getInstance().addObserver(this, DiagramEditor.NEW_DATASET_NOTIFICATION);
		Notificator.getInstance().addObserver(this, DiagramEditor.DATASET_CHANGED);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
    public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
		this.makeColors();
	}
	
	public void makeColors() {
		DataModel model = DataModel.getInstance();
		if (dataSet == null) {
			model.setCurrentColorSet(null);
			return;
		}
		ArrayList<Color> colorSet;
		colorSet = new ArrayList<Color>();
		Random numGen = new Random();
		for (int i=0;i<dataSet.size();i++) {
			colorSet.add(new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256)));
		}
		model.setCurrentColorSet(colorSet);
		Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (dataSet == null) {
        	return;
        }
        
        margin = 20;
        size = Math.min(getWidth(), getHeight()) - margin * 2;
        double[] angles = dataSet.generateAngles();
        double angle = dataSet.getStartAngle();
        arcs = new ArrayList<Arc2D.Double>();
        ArrayList<Color> currentSetColors = DataModel.getInstance().getCurrentColorSet();
        
        Arc2D.Double arc;
        for (int i=0; i<dataSet.size(); i++) {
        	arc = new Arc2D.Double(margin, margin, size, size, angle, angles[i], Arc2D.PIE);
        	if (currentSetColors != null) {
        		g.setColor(currentSetColors.get(i));
        	}
        	((Graphics2D)g).fill(arc);
        	g.setColor(Color.black);
        	((Graphics2D)g).draw(arc);
        	arcs.add(arc); 
        	angle += angles[i];
        }
        
        double diffAngle = 3;
        angle = dataSet.getStartAngle()-diffAngle;
        activeArcs = new ArrayList<Arc2D.Double>();
        for (int i=0; i<dataSet.size(); i++) {
        	arc = new Arc2D.Double(margin, margin, size, size, angle, 2*diffAngle, Arc2D.PIE);
        	activeArcs.add(arc);
        	angle += angles[i];
        }
        //g.draw
    }
	
	public int hitTest(Point2D point) {
		if (arcs == null)
			return -1;
		for (int i=0; i<arcs.size(); i++) {
			Arc2D.Double arc = arcs.get(i);
			if (arc.contains(point)) {
				return i;
			}
		}
		return -1;
	}
    
	public int hitTestActiveRegions(Point2D point) {
		if (activeArcs == null)
			return -1;
		for (int i=0; i<activeArcs.size(); i++) {
			Arc2D.Double arc = activeArcs.get(i);
			if (arc.contains(point)) {
				return i; // between i and i+1
			}
		}
		return -1;
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
				this.setDataSet((DataSet)notification.getUserData());
				this.revalidate();
				this.repaint();
			}
		}
		if (notification.getName().compareTo(DiagramEditor.DATASET_CHANGED) == 0) {
			this.revalidate();
			this.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// left - increase, right - decrease
		if (arcs != null) {
			Point2D p = e.getPoint();
			int arcNum = hitTest(p);
			if (arcNum != -1) {
				float increase = 0;
				if (e.getButton() == MouseEvent.BUTTON1) {
					increase = 0.5f;
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					increase = -0.5f;
				}
				dataSet.set(arcNum, dataSet.get(arcNum)+increase);
				Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (arcs != null) {
			Point2D p = e.getPoint();
			currentMoving = hitTestActiveRegions(p);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentMoving = -1;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (arcs != null) {
			Point2D p = e.getPoint();
			
			if (currentMoving != -1) {
				int second = currentMoving;
				double angle = 0;
				double x = p.getX() - (margin + size/2);
				double y = -p.getY() + (margin + size/2);
				if (x==0) {
					angle = (y>0) ? 90 : 270;
				} else {
					angle = Math.atan(y/x)*180/Math.PI;
					angle = (x > 0) ? angle : angle+180;
				}
				if (angle < 0 && angle > -90) {
					angle = 360 + angle;
				}
				//angle = (angle<0)?360-angle:angle;
				int first = (second-1 < 0)?dataSet.size()-1:second-1;
				this.dataSet.setAngle(first, angle);
				System.out.println(angle);
				Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
			}
		}
	}
    
}
