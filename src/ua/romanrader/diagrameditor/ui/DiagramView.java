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

/**
 * Компонент отображения и редактирования диаграммы
 * @author romanrader
 *
 */
@SuppressWarnings("serial")
public class DiagramView extends JPanel
implements Observer, MouseListener, MouseMotionListener  {
    /**
     * секторы
     */
    private ArrayList<Arc2D.Double> arcs = null;
    /**
     * Активные секторы
     */
    private ArrayList<Arc2D.Double> activeArcs = null;
    /**
     * отступ
     */
    private float margin;
    /**
     * размеры
     */
    private float size;
    /**
     * текущий сектор
     */
    private int currentMoving = -1;

    /**
     * Конструктор компонента
     */
    public DiagramView() {
        Notificator.getInstance().addObserver(this,
                DiagramEditor.NEW_DATASET_NOTIFICATION);
        Notificator.getInstance().addObserver(this,
                DiagramEditor.DATASET_CHANGED);
        Notificator.getInstance().addObserver(this,
                DiagramEditor.VIEWSTATE_CHANGED);
        Notificator.getInstance().addObserver(this,
                DiagramEditor.COLUMN_ADDED);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Рисование
     * @param g контекст
     */
    public final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        DataModel model = DataModel.getInstance();

        if (DataModel.getInstance().getVstate() == DataModel.ViewState.Single) {
            DataSet dataSet = DataModel.getInstance().getCurrentDataSet();
            paintDataSet(g, dataSet, model.getCurrentColorSet(), 1, 1, true);
        }

        if (model.getVstate() == DataModel.ViewState.Simultaneously) {
            if (model.getColorSets() != null) {
                for (int i = 0; i < model.size(); i++) {
                    DataSet dataSet = model.get(i);
                    paintDataSet(g, dataSet, model.getColorSets().get(i),
                            model.size(), i + 1, false);
                    activeArcs = null;
                }
            }
        }
    }

    /**
     * Рисование конкретного дата-сета в контексте
     * @param g контекст
     * @param dataSet дата-сет
     * @param colorSet список цветов
     * @param count количество дата-сетов
     * @param num номер текущего дата-сета
     * @param makeActiveRegions создавать ли области изменения размеров
     */
    public final void paintDataSet(final Graphics g, final DataSet dataSet,
            final ArrayList<Color> colorSet, final int count, final int num,
            final boolean makeActiveRegions) {
        if (dataSet == null) {
            g.clearRect(0, 0, getWidth(), getHeight());
            return;
        }

        double side = Math.min(getWidth(), getHeight());
        final double margins = 20;
        margin = (float) (margins + ((side / 2) / count) * (num - 1));
        size = Math.min(getWidth(), getHeight()) - margin * 2;
        double[] angles = dataSet.generateAngles();
        double angle = dataSet.getStartAngle();
        arcs = new ArrayList<Arc2D.Double>();
        ArrayList<Color> currentSetColors = colorSet;

        Arc2D.Double arc;
        for (int i = 0; i < dataSet.size(); i++) {
            arc = new Arc2D.Double(margin, margin, size, size,
                    angle, angles[i], Arc2D.PIE);
            if (currentSetColors != null) {
                g.setColor(currentSetColors.get(i));
            }
            ((Graphics2D) g).fill(arc);
            g.setColor(Color.black);
            ((Graphics2D) g).draw(arc);
            arcs.add(arc);
            angle += angles[i];
        }

        if (makeActiveRegions) {
            final double diffAngle = 3;
            angle = dataSet.getStartAngle() - diffAngle;
            activeArcs = new ArrayList<Arc2D.Double>();
            for (int i = 0; i < dataSet.size(); i++) {
                arc = new Arc2D.Double(margin, margin, size, size,
                        angle, 2 * diffAngle, Arc2D.PIE);
                activeArcs.add(arc);
                angle += angles[i];
            }
        }
    }

    /**
     * Проверка попадания мышкой в секцию
     * @param point координаты
     * @return номер секции (или -1 если не попал)
     */
    public final int hitTest(final Point2D point) {
        if (arcs == null) {
            return -1;
        }
        for (int i = 0; i < arcs.size(); i++) {
            Arc2D.Double arc = arcs.get(i);
            if (arc.contains(point)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Проверка попадания в активную область мышкой
     * @param point координаты
     * @return номер секции (или -1 если не попал)
     */
    public final int hitTestActiveRegions(final Point2D point) {
        if (activeArcs == null) {
            return -1;
        }
        for (int i = 0; i < activeArcs.size(); i++) {
            Arc2D.Double arc = activeArcs.get(i);
            if (arc.contains(point)) {
                return i; // between i and i+1
            }
        }
        return -1;
    }

    /**
     * В картинку
     * @return возвращает объект Image
     */
    public final Image toImage() {
        DataSet dataSet = DataModel.getInstance().getCurrentDataSet();

        if (dataSet == null) {
            return null;
        }

        BufferedImage bi = new BufferedImage(Math.round(size + 2 * margin),
                Math.round(size + 2 * margin), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.clearRect(0, 0, Math.round(size + 2 * margin),
                Math.round(size + 2 * margin));
        this.paint(g2);
        g2.dispose();
        return bi;
    }

    /**
     * Получение сообщения
     * @param notification сообщение
     */
    @Override
    public final void notificationReceived(final Notification notification) {
        if ((notification.getName().compareTo(
                DiagramEditor.NEW_DATASET_NOTIFICATION) == 0)
                || (notification.getName().compareTo(
                        DiagramEditor.DATASET_CHANGED) == 0)
                || (notification.getName().compareTo(
                        DiagramEditor.VIEWSTATE_CHANGED) == 0)
                || (notification.getName().compareTo(
                        DiagramEditor.COLUMN_ADDED) == 0)) {
            DataModel.getInstance().makeColors();
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Обработка клика мышкой
     * @param e событие
     */
    @Override
    public final void mouseClicked(final MouseEvent e) {
        DataSet dataSet = DataModel.getInstance().getCurrentDataSet();

        if (DataModel.getInstance().getState() == DataModel.State.StateNormal) {
            // left - increase, right - decrease
            if (arcs != null) {
                Point2D p = e.getPoint();
                int arcNum = hitTest(p);
                if (arcNum != -1) {
                    float increase = 0;
                    final float inc = 0.5f;
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        increase = inc;
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        increase = -inc;
                    }
                    dataSet.set(arcNum, dataSet.get(arcNum) + increase);
                    Notificator.getInstance().sendNotify(this,
                            DiagramEditor.DATASET_CHANGED);
                }
            }
        }

        if (DataModel.getInstance().getState()
                == DataModel.State.StateRemoving) {
            Point2D p = e.getPoint();
            int arcNum = hitTest(p);
            if (arcNum != -1) {
                if (dataSet.size() > 1) {
                    int response = JOptionPane.showConfirmDialog(null,
                            "Remove this section?", "Confirm",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        dataSet.remove(arcNum);
                        Notificator.getInstance().sendNotify(this,
                                DiagramEditor.DATASET_CHANGED);
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
            Notificator.getInstance().sendNotify(this,
                    DiagramEditor.DATASET_CHANGED);
        }

    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }

    /**
     * Обработка нажатия мыши
     * @param e событие
     */
    @Override
    public final void mousePressed(final MouseEvent e) {
        if (arcs != null) {
            Point2D p = e.getPoint();
            currentMoving = hitTestActiveRegions(p);
        }
    }

    /**
     * Обработка отпускания мыши
     * @param e событие
     */
    @Override
    public final void mouseReleased(final MouseEvent e) {
        currentMoving = -1;
    }

    @Override
    public void mouseMoved(final MouseEvent arg0) {

    }

    /**
     * Обработка перетаскивания
     * @param e событие
     */
    @Override
    public final void mouseDragged(final MouseEvent e) {
        if (arcs != null) {
            Point2D p = e.getPoint();

            if (currentMoving != -1) {
                int second = currentMoving;
                double angle = 0;
                double x = p.getX() - (margin + size / 2);
                double y = -p.getY() + (margin + size / 2);
                final int c90 = 90;
                final int c270 = 270;
                final int c180 = 180;
                final int c360 = 360;
                if (x == 0) {
                    if (y > 0) {
                        angle = c90;
                    } else {
                        angle = c270;
                    }
                } else {
                    angle = Math.atan(y / x) * c180 / Math.PI;
                    if (x < 0) {
                        angle = angle + c180;
                    }
                }
                if (angle < 0 && angle > -c90) {
                    angle = c360 + angle;
                }

                DataSet dataSet = DataModel.getInstance().getCurrentDataSet();
                int first;
                if (second - 1 < 0) {
                    first = dataSet.size() - 1;
                } else {
                    first = second - 1;
                }
                dataSet.setAngle(first, angle);
                Notificator.getInstance().sendNotify(this,
                        DiagramEditor.DATASET_CHANGED);
            }
        }
    }

}
