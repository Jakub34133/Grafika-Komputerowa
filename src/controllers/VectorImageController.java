package controllers;

import models.PointModel;
import views.MainFrame;

import java.util.ArrayList;

public class VectorImageController {
    private ArrayList<PointModel> points;
    private ArrayList<PointModel> transformedPoints;
    private double[][] matrix;

    private final MainFrame parent;

    public VectorImageController(MainFrame parent) {
        this.parent = parent;

        this.points = new ArrayList<>();
        this.transformedPoints = new ArrayList<>();
        this.matrix = new double[3][3];

        // Macierz jednostkowa
        for(int i = 0; i < matrix.length; i++) {
            this.matrix[i][i] = 1;
        }
    }

    public ArrayList<PointModel> getPoints() {
        return points;
    }
    public ArrayList<PointModel> getTransformedPoints() {
        return transformedPoints;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    /*
        Metody dla modyfikacji punktów
     */

    public void addPoint(double x, double y) {
        points.add(new PointModel(x, y));
        transformedPoints.add(new PointModel(x, y));
    }

    public void addPoint(int index, double x, double y) {
        points.add(index+1, new PointModel(x, y));
        transformedPoints.add(index+1, new PointModel(x, y));
    }

    public PointModel removePoint(int index) {
        transformedPoints.remove(index);
        return points.remove(index);
    }

    public void clearPoints() {
        transformedPoints.clear();
        points.clear();
    }

    /*
        Metody dla transformacji krzywych
     */
    /// M = M aktualna * M elementarna

    public void move(double x, double y) {
        // macierz 3x3 przesunięcia
        double[][] moveTransformMatrix = {
                {1, 0, 0},
                {0, 1, 0},
                {x, y, 1}
        };

        // zmiana transformowanych punktów
//        int index = 0;
        for(PointModel point : transformedPoints) {
//            double[] pointMatrix = {points.get(index).getX(), points.get(index).getY(), 1.0};
//            double[] newPointMatrix = matrixMultiply(pointMatrix, matrix);

            double[] pointMatrix = {point.getX(), point.getY(), 1.0};
            double[] newPointMatrix = matrixMultiply(pointMatrix, moveTransformMatrix);

            point.setX(newPointMatrix[0]);
            point.setY(newPointMatrix[1]);

//            index++;
        }

        // zmiana głównej macierzy
        matrix = matrixMultiply(matrix, moveTransformMatrix);

    }

    public void rotate(double angleDegress) {
        double angleRadians = Math.toRadians(angleDegress); // podanie kąta w radianach

        // macierz 3x3 skalowania
        double[][] rotateTransformMatrix = {
                {Math.cos(angleRadians), Math.sin(angleRadians), 0},
                {-Math.sin(angleRadians), Math.cos(angleRadians), 0},
                {0, 0, 1}
        };

        // zmiana transformowanych punktów
        for(PointModel point : transformedPoints) {
            double[] pointMatrix = {point.getX(), point.getY(), 1.0};
            double[] newPointMatrix = matrixMultiply(pointMatrix, rotateTransformMatrix);

            point.setX(newPointMatrix[0]);
            point.setY(newPointMatrix[1]);
        }

        // zmiana głównej macierzy
        matrix = matrixMultiply(matrix, rotateTransformMatrix);
    }

    public void scale(double x, double y) {
        // macierz 3x3 skalowania
        double[][] scaleTransformMatrix = {
                {x, 0, 0},
                {0, y, 0},
                {0, 0, 1}
        };

        // zmiana transformowanych punktów
        for(PointModel point : transformedPoints) {
            double[] pointMatrix = {point.getX(), point.getY(), 1.0};
            double[] newPointMatrix = matrixMultiply(pointMatrix, scaleTransformMatrix);

            point.setX(newPointMatrix[0]);
            point.setY(newPointMatrix[1]);
        }

        // zmiana głównej macierzy
        matrix = matrixMultiply(matrix, scaleTransformMatrix);

    }

    public double[] matrixMultiply(double[] matrix1, double[][] matrix2) {
        double[] newMatrix = new double[3];

        for(int i = 0; i < 3; i++) {
                double newValue =
                        matrix1[0] * matrix2[0][i] +
                        matrix1[1] * matrix2[1][i] +
                        matrix1[2] * matrix2[2][i];
                newMatrix[i] = newValue;
        }
        return newMatrix;
    }

    public double[][] matrixMultiply(double[][] matrix1, double[][] matrix2) {
        double[][] newMatrix = new double[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                double newValue = 0;

                for(int k = 0; k < 3; k++) {
                    newValue += matrix1[i][k] * matrix2[k][j];
                }

                newMatrix[i][j] = newValue;
            }

        }
        return newMatrix;
    }

    public void clearMatrix() {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(i == j) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
