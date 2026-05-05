package controllers;

import models.Point3dModel;
import models.PointModel;
import models.Solid3dModel;
import models.Wall3dModel;
import views.MainFrame;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Vector3dImageController {
    private ArrayList<Point3dModel> points3d;
    private ArrayList<Wall3dModel> walls3d;

    private double[][] matrix;

    private final MainFrame parent;

    public Vector3dImageController(MainFrame parent) {
        this.parent = parent;

        this.points3d = new ArrayList<>();
        this.walls3d = new ArrayList<>();
        this.matrix = new double[4][4];

        // Macierz jednostkowa
        for(int i = 0; i < matrix.length; i++) {
            this.matrix[i][i] = 1;
        }
    }

    public ArrayList<Point3dModel> getPoints3d() {
        return points3d;
    }

    public ArrayList<Wall3dModel> getWalls3d() {
        return walls3d;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void clearPoints3d() {
        this.points3d.clear();
    }

    public void clearWalls3d() {
        this.walls3d.clear();
    }

    /*
        Metody do transformacji Bryły
     */
    /// M = M aktualna * M elementarna

    public void move(double x, double y, double z) {
        // macierz 4x4 przesunięcia
        double[][] moveTransformMatrix = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {x, y, z, 1}
        };

        // zmiana głównej macierzy
        this.matrix = matrixMultiply(this.matrix, moveTransformMatrix);
    }

    public void rotateOZ(double angleDegress) {
        double angleRadians = Math.toRadians(angleDegress); // podanie kąta w radianach

        // macierz 4x4 skalowania
        double[][] rotateTransformMatrix = {
                {Math.cos(angleRadians), Math.sin(angleRadians), 0, 0},
                {-Math.sin(angleRadians), Math.cos(angleRadians), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        // zmiana głównej macierzy
        matrix = matrixMultiply(matrix, rotateTransformMatrix);
    }

    public void rotateOY(double angleDegress) {
        double angleRadians = Math.toRadians(angleDegress); // podanie kąta w radianach

        // macierz 4x4 skalowania
        double[][] rotateTransformMatrix = {
                {Math.cos(angleRadians), 0, -Math.sin(angleRadians), 0},
                {0, 1, 0, 0},
                {Math.sin(angleRadians), 0, Math.cos(angleRadians), 0},
                {0, 0, 0, 1}
        };

        // zmiana głównej macierzy
        matrix = matrixMultiply(matrix, rotateTransformMatrix);
    }

    public void rotateOX(double angleDegress) {
        double angleRadians = Math.toRadians(angleDegress); // podanie kąta w radianach

        // macierz 4x4 skalowania
        double[][] rotateTransformMatrix = {
                {1, 0, 0, 0},
                {0, Math.cos(angleRadians), Math.sin(angleRadians), 0},
                {0, -Math.sin(angleRadians), Math.cos(angleRadians), 0},
                {0, 0, 0, 1}
        };

        // zmiana głównej macierzy
        matrix = matrixMultiply(matrix, rotateTransformMatrix);
    }


    public void scale(double x, double y, double z) {
        // macierz 4x4 skalowania
        double[][] scaleTransformMatrix = {
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        };

        // zmiana głównej macierzy
        this.matrix = matrixMultiply(this.matrix, scaleTransformMatrix);

    }

    public double[] matrixMultiply(double[] matrix1, double[][] matrix2) {
        double[] newMatrix = new double[4];

        for(int i = 0; i < 4; i++) {
                double newValue =
                        matrix1[0] * matrix2[0][i] +
                        matrix1[1] * matrix2[1][i] +
                        matrix1[2] * matrix2[2][i] +
                        matrix1[3] * matrix2[3][i];
                newMatrix[i] = newValue;
        }
        return newMatrix;
    }

    public double[][] matrixMultiply(double[][] matrix1, double[][] matrix2) {
        double[][] newMatrix = new double[4][4];

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                double newValue = 0;

                for(int k = 0; k < 4; k++) {
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

    /*
        Odczyt brył z plików
     */
    public Solid3dModel readSolidFromFile(String filepath) {
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);

            this.clearPoints3d();
            this.clearWalls3d();

            int pointAmount = scanner.nextInt();
            for(int i = 0; i < pointAmount; i++) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                double z = scanner.nextDouble();

                this.points3d.add(new Point3dModel(x, y, z));
            }

            // Example:
            // 4    1 2 3 4     255 0 0
            // wallPointCount   wallPoints  color
            int wallAmount = scanner.nextInt();
            for(int i = 0; i < wallAmount; i++) {
                int wallPointCount = scanner.nextInt();
                int[] wallPoints = new int[wallPointCount];

                for(int j = 0; j < wallPointCount; j++) {
                    wallPoints[j] = scanner.nextInt();
                }
                int colorRed = scanner.nextInt();
                int colorGreen = scanner.nextInt();
                int colorBlue = scanner.nextInt();
                Color color = new Color(colorRed, colorGreen, colorBlue);

                this.walls3d.add(new Wall3dModel(wallPointCount, wallPoints, color));
            }

            Solid3dModel solid3d = new Solid3dModel(this.points3d, this.walls3d);
            return  solid3d;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
