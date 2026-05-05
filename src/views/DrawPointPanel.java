package views;

import controllers.VectorImageController;
import models.PointModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawPointPanel extends JPanel {
    public static final double RADIUS = 4;
    private final VectorImageInterfacePanel parent;

    private boolean pointsVisible;
    private boolean straightLinesVisible;
    private boolean curvedLinesVisible;

    private double curvedLineStep; // Wartość 0 - 1

    public double getCurvedLineStep() {
        return curvedLineStep;
    }

    public void setCurvedLineStep(double curvedLineStep) {
        this.curvedLineStep = curvedLineStep;
    }

    public boolean isStraightLinesVisible() {
        return straightLinesVisible;
    }

    public void setStraightLinesVisible(boolean straightLinesVisible) {
        this.straightLinesVisible = straightLinesVisible;
    }

    public boolean isCurvedLinesVisible() {
        return curvedLinesVisible;
    }

    public void setCurvedLinesVisible(boolean curvedLinesVisible) {
        this.curvedLinesVisible = curvedLinesVisible;
    }

    public DrawPointPanel(VectorImageInterfacePanel parent) {
        this.parent = parent;
        this.pointsVisible = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);

        ArrayList<PointModel> points = parent.getVectorImageController().getTransformedPoints();

        /// Rysowanie Punktów
        if(pointsVisible) {
            for(PointModel point : points) {
                double x = point.getX();
                double y = point.getY();

                g.fillOval((int)(x - RADIUS), (int)(y - RADIUS), (int)RADIUS*2,(int) RADIUS*2 );
            }
        }

        /// Rysowanie Łamanej
        if(straightLinesVisible) {
            for(int i = 0; i < points.size() - 1; i++) {
                double x1 = points.get(i).getX();
                double y1 = points.get(i).getY();

                double x2 = points.get(i+1).getX();
                double y2 = points.get(i+1).getY();

                g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
            }
        }

        /// Rysowanie Krzywych Beziera
        if(curvedLinesVisible && !points.isEmpty() && this.curvedLineStep > 0 && this.curvedLineStep <= 1) {
            g.setColor(Color.red);

            int n = points.size(); // Ilość punktów
            PointModel[] R = new PointModel[n]; // Punkty kontrolne
            double step = this.curvedLineStep; // Wartość [0-1]
//            step = 0.01;

            PointModel prevPoint = null;

            for(double t = 0; t <= 1; t += step) {
                /// Kopiowanie punktów z orginalnej listy
                for(int i = 0; i < n; i++) {
                    R[i] = points.get(i);
                }

                int m = n;
                /// Dopóki więcej niż 1 punkt
                while(m > 0) {
                    /// Punkty pomiędzy punktami kontrolnymi (krzywa)
                    PointModel[] Q = new PointModel[n]; // Punkty pośrednie

                    for(int p = 0; p < m-1; p++) { // n-1 razy
                        double newX = R[p].getX() + t * (R[p+1].getX() - R[p].getX());
                        double newY = R[p].getY() + t * (R[p+1].getY() - R[p].getY());
                        Q[p] = new PointModel(newX, newY);

                        // g.fillOval((int)newX, (int)newY, 1, 1);
                    }

                    m--; // Zmniejszamy ilość punktów
                    for(int p = 0; p < m; p++) {
                        R[p] = Q[p];
                    }
                } // while()
                //P(t) = R[0]

                if(prevPoint == null) {
                    prevPoint = new PointModel(R[0].getX(), R[0].getY());
                } else {
                    double x1 = prevPoint.getX();
                    double y1 = prevPoint.getY();
                    double x2 = R[0].getX();
                    double y2 = R[0].getY();

                    g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
//                    g.fillRect((int)R[0].getX(), (int)R[0].getY(), 5, 5);

                    prevPoint.setX(x2);
                    prevPoint.setY(y2);

                }
//                g.drawLine((int)prevPoint.getX(), (int)prevPoint.getY(), (int)points.get(), );

            }

        } // if(rysować krzywą)
    }
}
