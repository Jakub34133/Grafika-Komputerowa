package views;

import models.Point3dModel;
import models.Wall3dModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Draw3dPanel extends JPanel {
    public static final double RADIUS = 4;
    private final Vector3dImageInterfacePanel parent;

    private boolean pointsVisible;
    private boolean straightLinesVisible;
    private boolean wallsVisible;
    // obserwator (0,0,-d)
    private double[] observer = {0, 0, -1000};

    public void setObserverDistance(double distanceZ) {
        observer[2] = -distanceZ;
    }

    public void setPointsVisible(boolean pointsVisible) {
        this.pointsVisible = pointsVisible;
    }

    public void setStraightLinesVisible(boolean straightLinesVisible) {
        this.straightLinesVisible = straightLinesVisible;
    }

    public void setWallsVisible(boolean wallsVisible) {
        this.wallsVisible = wallsVisible;
    }

    public boolean isPointsVisible() {
        return pointsVisible;
    }

    public boolean isStraightLinesVisible() {
        return straightLinesVisible;
    }

    public boolean isWallsVisible() {
        return wallsVisible;
    }

    public Draw3dPanel(Vector3dImageInterfacePanel parent) {
        this.parent = parent;
        this.pointsVisible = false;
        this.straightLinesVisible = true;
        this.wallsVisible = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);

        ArrayList<Point3dModel> points = parent.getVector3dImageController().getPoints3d();
        ArrayList<Point3dModel> newPoints = new ArrayList<>();
        ArrayList<Wall3dModel> walls = parent.getVector3dImageController().getWalls3d();

        // Przkształcenie przez macierz
        double[][] mainMatrix = this.parent.getVector3dImageController().getMatrix();
        for(Point3dModel point : points) {
            double[] pointVector = {point.getX(), point.getY(), point.getZ(), 1};

            double[] resultMatrix = this.parent.getVector3dImageController().matrixMultiply(pointVector, mainMatrix);

//            point.setX(resultMatrix[0]);
//            point.setY(resultMatrix[1]);
//            point.setZ(resultMatrix[2]);

            Point3dModel newPoint = new Point3dModel(
                    resultMatrix[0],
                    resultMatrix[1],
                    resultMatrix[2]
            );

            newPoints.add(newPoint);
        }

        /// Rzut perspektywiczny
        double[] arrayX = new double[newPoints.size()];
        double[] arrayY = new double[newPoints.size()];

        double d = observer[2];

        int index = 0;
        for(Point3dModel point : newPoints) {
            double x = point.getX() * d / (d + point.getZ());
            double y = point.getY() * d / (d + point.getZ());

            // Przesunięcie na środek ekranu
            x += this.getWidth() / 2.0;
            y += this.getHeight() / 2.0;

            arrayX[index] = x;
            arrayY[index] = y;
            index++;
        }

        /// Rysowanie Punktów
        if(pointsVisible) {
            g.setColor(Color.black);
            for(int i = 0; i < newPoints.size(); i++) {
                // Odczyt punktów z rzutu perspektywicznego
                double x = arrayX[i];
                double y = arrayY[i];

                // Rysowanie punktów
                g.fillOval((int)(x - RADIUS), (int)(y - RADIUS), (int)RADIUS*2,(int) RADIUS*2 );
            }
        } // if rysować punkty

        /// Rysowanie Modelu Drucianego
        if(straightLinesVisible) {
            g.setColor(Color.black);
            for(Wall3dModel wall : walls) {
                int pointAmount = wall.getPointAmount();

                // Rysowanie ściany (drut)
                for(int i = 0; i < pointAmount - 1; i++) {
                    int firstPointIndex = wall.getPointAt(i);

                    double x1 = arrayX[firstPointIndex];
                    double y1 = arrayY[firstPointIndex];

                    int secondPointIndex = wall.getPointAt(i+1);

                    double x2 = arrayX[secondPointIndex];
                    double y2 = arrayY[secondPointIndex];

                    g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                }

                // Połączenie ostatniego i pierwszego punktu
                int firstPointIndex = wall.getPointAt(pointAmount - 1);

                double x1 = arrayX[firstPointIndex];
                double y1 = arrayY[firstPointIndex];

                int secondPointIndex = wall.getPointAt(0);

                double x2 = arrayX[secondPointIndex];
                double y2 = arrayY[secondPointIndex];

                g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);

            }
        } // if rysować model druciany

        /// Rysowanie modelu bryły (ścian)
        if(wallsVisible) {
            /*
            for(Wall3dModel wall : walls) {
                int nPolygon = wall.getPointAmount();

                int[] xPolygon = new int[nPolygon];
                int[] yPolygon = new int[nPolygon];

                Color wallColor = wall.getWallColor();
                g.setColor(wallColor);

                for(int i = 0; i < nPolygon; i++) {
                    int pointIndex = wall.getPointAt(i);

                    double x = arrayX[pointIndex];
                    double y = arrayY[pointIndex];

                    xPolygon[i] = (int)x;
                    yPolygon[i] = (int)y;
                }

                // Odrzucanie ścian
                int indexP1 = wall.getPointAt(0);
                int indexP2 = wall.getPointAt(1);
                int indexP3 = wall.getPointAt(2);

                // Pobranie 3 pierwszych wierzchołków ściany
                Point3dModel p1 = newPoints.get(indexP1);
                Point3dModel p2 = newPoints.get(indexP2);
                Point3dModel p3 = newPoints.get(indexP3);

                // p2 - p1
                Point3dModel vectorA = new Point3dModel(
                p2.getX() - p1.getX(),
                p2.getY() - p1.getY(),
                p2.getZ() - p1.getZ()
                );

                // p3 - p2
                Point3dModel vectorB = new Point3dModel(
                        p3.getX() - p2.getX(),
                        p3.getY() - p2.getY(),
                        p3.getZ() - p2.getZ()
                );

                Point3dModel vectorNormal = new Point3dModel(
                        vectorA.getY()*vectorB.getZ() - vectorA.getZ()*vectorB.getY(),
                        vectorA.getZ()*vectorB.getX() - vectorA.getX()*vectorB.getZ(),
                        vectorA.getX()*vectorB.getY() - vectorA.getY()*vectorB.getX()
                );

                Point3dModel toObserver = new Point3dModel(
                        observer[0] - p1.getX(),
                        observer[1] - p1.getY(),
                        observer[2] - p1.getZ()
                );

                double scalar = (
                        (vectorNormal.getX() * toObserver.getX()) +
                        (vectorNormal.getY() * toObserver.getY()) +
                        (vectorNormal.getZ() * toObserver.getZ())
                );

                if(scalar > 0) {
                    g.fillPolygon(xPolygon, yPolygon, nPolygon);
                }
            }
            */

            // Sortowanie ścian
            ArrayList<Wall3dModel> copyWalls = new ArrayList<>(walls);
            ArrayList<Wall3dModel> sortedWalls = new ArrayList<>();
            ArrayList<Double> wallsAvgZ = new ArrayList<>();

            for(Wall3dModel wall : walls) {
                int pointAmount = wall.getPointAmount();

                // średnia Z
                double avgZ = 0;
                for(int i = 0; i < pointAmount; i++) {
                    int pointIndex = wall.getPointAt(i);

                    double z = newPoints.get(pointIndex).getZ();
                    avgZ += z;
                }
                avgZ /= pointAmount;
                wallsAvgZ.add(avgZ);
            }

            // sortowanie
            for(int i = 0; i < walls.size(); i++) {
                double minValue = wallsAvgZ.get(0);
                int wallIndex = 0;

                for(int j = 0; j < copyWalls.size(); j++) {
                    if(wallsAvgZ.get(j) <= minValue) {
                        minValue = wallsAvgZ.get(j);
                        wallIndex = j;
                    }
                }

                System.out.println(wallsAvgZ.remove(wallIndex));
                Wall3dModel wall = copyWalls.remove(wallIndex);

                sortedWalls.add(wall);
            }

            // rysowanie ścian
            for(Wall3dModel wall : sortedWalls) {
                int nPolygon = wall.getPointAmount();

                int[] xPolygon = new int[nPolygon];
                int[] yPolygon = new int[nPolygon];

                Color wallColor = wall.getWallColor();
                g.setColor(wallColor);

                for(int i = 0; i < nPolygon; i++) {
                    int pointIndex = wall.getPointAt(i);

                    double x = arrayX[pointIndex];
                    double y = arrayY[pointIndex];

                    xPolygon[i] = (int)x;
                    yPolygon[i] = (int)y;
                }

                g.fillPolygon(xPolygon, yPolygon, nPolygon);
            }

        } // if rysować model bryły

    }
}
