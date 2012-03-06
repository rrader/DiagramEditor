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

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ua.romanrader.diagrameditor.model.DataModel;
import ua.romanrader.diagrameditor.model.csv.DataSet;
import ua.romanrader.diagrameditor.util.observer.Notification;
import ua.romanrader.diagrameditor.util.observer.Notificator;
import ua.romanrader.diagrameditor.util.observer.Observer;

@SuppressWarnings("serial")
public class DiagramView extends JPanel implements Observer, MouseListener, MouseMotionListener  {
	ArrayList<Arc2D.Double> arcs = null;
	ArrayList<Arc2D.Double> activeArcs = null;
	private float margin;
	private float size;
	private int currentMoving = -1;
	public DiagramView() {
		Notificator.getInstance().addObserver(this, DiagramEditor.NEW_DATASET_NOTIFICATION);
		Notificator.getInstance().addObserver(this, DiagramEditor.DATASET_CHANGED);
		Notificator.getInstance().addObserver(this, DiagramEditor.VIEWSTATE_CHANGED);
		Notificator.getInstance().addObserver(this, DiagramEditor.COLUMN_ADDED);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DataModel model = DataModel.getInstance();
        
        if (DataModel.getInstance().getVstate() == DataModel.ViewState.Single) {
        	DataSet dataSet = DataModel.getInstance().getCurrentDataSet();
        	paintDataSet(g, dataSet, model.getCurrentColorSet(), 1, 1, true);
        }
        
        if (model.getVstate() == DataModel.ViewState.Simultaneously) {
        	if (model.getColorSets() != null) {
        		for (int i=0;i<model.size();i++) {
            		DataSet dataSet = model.get(i);
            		paintDataSet(g, dataSet, model.getColorSets().get(i), model.size(), i+1, false);
            		activeArcs = null;
            	}
        	}
        }
    }

	public void paintDataSet(Graphics g, DataSet dataSet, ArrayList<Color> colorSet, int count, int num, boolean makeActiveRegions) {
		if (dataSet == null) {
        	g.clearRect(0, 0, getWidth(), getHeight());
        	return;
        }
        
		double side = Math.min(getWidth(), getHeight());
		
        margin = (float) (20 + ((side/2)/count)*(num-1));
        size = Math.min(getWidth(), getHeight()) - margin * 2;
        double[] angles = dataSet.generateAngles();
        double angle = dataSet.getStartAngle();
        arcs = new ArrayList<Arc2D.Double>();
        ArrayList<Color> currentSetColors = colorSet;
        
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

        if (makeActiveRegions) {
        	double diffAngle = 3;
            angle = dataSet.getStartAngle()-diffAngle;
            activeArcs = new ArrayList<Arc2D.Double>();
            for (int i=0; i<dataSet.size(); i++) {
            	arc = new Arc2D.Double(margin, margin, size, size, angle, 2*diffAngle, Arc2D.PIE);
            	activeArcs.add(arc);
            	angle += angles[i];
            }
        }
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
	
    public Image toImage() {
    	DataSet dataSet = DataModel.getInstance().getCurrentDataSet();
    	
    	if (dataSet == null) {
        	return null;
        }
    	
    	BufferedImage bi = new BufferedImage(Math.round(size+2*margin), Math.round(size+2*margin), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.clearRect(0, 0, Math.round(size+2*margin), Math.round(size+2*margin) );
        this.paint(g2);
        g2.dispose();
        return bi;
    }

	@Override
	public void notificationReceived(Notification notification) {
		//TODO merge new and changed notifications
		if (notification.getName().compareTo(DiagramEditor.NEW_DATASET_NOTIFICATION) == 0) {
			DataModel.getInstance().makeColors();
			this.revalidate();
			this.repaint();
		}
		if (notification.getName().compareTo(DiagramEditor.DATASET_CHANGED) == 0) {
			DataModel.getInstance().makeColors();
			this.revalidate();
			this.repaint();
		}
		if (notification.getName().compareTo(DiagramEditor.VIEWSTATE_CHANGED) == 0) {
			DataModel.getInstance().makeColors();
			this.revalidate();
			this.repaint();
		}
		if (notification.getName().compareTo(DiagramEditor.COLUMN_ADDED) == 0) {
			DataModel.getInstance().makeColors();
			this.revalidate();
			this.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DataSet dataSet = DataModel.getInstance().getCurrentDataSet();
		
		if (DataModel.getInstance().getState() == DataModel.State.StateNormal) {
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
		
		if (DataModel.getInstance().getState() == DataModel.State.StateRemoving) {
			Point2D p = e.getPoint();
			int arcNum = hitTest(p);
			if (arcNum != -1) {
				if (dataSet.size()>1) {
					int response = JOptionPane.showConfirmDialog(null, "Remove this section?", "Confirm",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						dataSet.remove(arcNum);
						Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
					}
				}
			}
			
		}

		if (e.getButton() == MouseEvent.BUTTON2) {
			Point2D p = e.getPoint();
			int arcNum = hitTest(p);
			if (arcNum != -1) {
				dataSet.remove(arcNum);
			}
			Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
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

				DataSet dataSet = DataModel.getInstance().getCurrentDataSet();
				int first = (second-1 < 0)?dataSet.size()-1:second-1;
				dataSet.setAngle(first, angle);
				Notificator.getInstance().sendNotify(this, DiagramEditor.DATASET_CHANGED);
			}
		}
	}
    
}
