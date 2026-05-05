package views;

import controllers.Vector3dImageController;
import models.Point3dModel;
import models.Wall3dModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Vector3dImageInterfacePanel extends JPanel {
    private final Dimension parentSize;
    private final MainFrame parent;

    private final Draw3dPanel draw3dPanel;

    private final JPanel drawPanel;
    private final JPanel optionPanel;

    private final JPanel point3dListPanel;
    private final JPanel wall3dListPanel;
    private final JPanel transformPanel;
    private final JPanel matrixPanel;

    private final Vector3dImageController vector3dImageController;

    public Vector3dImageController getVector3dImageController() {
        return vector3dImageController;
    }

    public Draw3dPanel getDraw3dPanel() {
        return draw3dPanel;
    }

    public Vector3dImageInterfacePanel(Vector3dImageController vector3dImageController, MainFrame parent) {
        this.parent = parent;

        setLayout(new BorderLayout(5, 5));
        setBackground(Color.darkGray);

        parentSize = new Dimension(parent.getWidth(), parent.getHeight());

        // Inicjalizacja poszczególnych okien
        this.draw3dPanel = new Draw3dPanel(this);

        this.drawPanel = new JPanel(new BorderLayout());
        this.optionPanel = new JPanel(new BorderLayout());

        // pasek opcji z prawej
        this.point3dListPanel = new JPanel(new BorderLayout());
        this.wall3dListPanel = new JPanel(new BorderLayout());
        this.transformPanel = new JPanel(new BorderLayout());
        this.matrixPanel = new JPanel(new BorderLayout());

        // Inicjalizacja kontrolera
        this.vector3dImageController = vector3dImageController;

        optionPanel.setBackground(Color.darkGray);
        optionPanel.setPreferredSize(new Dimension((int)parentSize.getWidth() / 3, 0));

        setupOptionPanel();
        double[][] matrixEx = vector3dImageController.getMatrix(); // default matrix
        updateMatrixPanel(matrixEx);
        updateTransformPanel();

        // Inicjalizacja pustych tabel punktów i ścian
        updatePoint3dListPanel(vector3dImageController.getPoints3d());
        updateWall3dListPanel(vector3dImageController.getWalls3d());

        updateDrawPanel();

        add(drawPanel, BorderLayout.CENTER);
        add(optionPanel, BorderLayout.EAST);
    }

    private void setupOptionPanel() {
        // tło okien
        point3dListPanel.setBackground(Color.lightGray);
        wall3dListPanel.setBackground(Color.lightGray);
        transformPanel.setBackground(Color.lightGray);
        matrixPanel.setBackground(Color.lightGray);

        // Wysokości okien
        point3dListPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 6));
        wall3dListPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 6));
        transformPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 3));
        matrixPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 4));

        optionPanel.setLayout(new BorderLayout(5,5));

        // dodanie okien
        JPanel tables = new JPanel(new BorderLayout());
        tables.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 3));

        tables.add(point3dListPanel, BorderLayout.NORTH);
        tables.add(wall3dListPanel, BorderLayout.SOUTH);

        optionPanel.add(tables, BorderLayout.NORTH);
        optionPanel.add(transformPanel, BorderLayout.CENTER);
        optionPanel.add(matrixPanel, BorderLayout.SOUTH);
    }

    public void updateMatrixPanel(double[][] matrix) {
        matrixPanel.removeAll();

        JPanel matrixTable = new JPanel(new GridLayout(4, 4));
        matrixTable.setBackground(Color.lightGray);
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                JLabel numberLabel = new JLabel(String.valueOf(matrix[i][j]));
                numberLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                numberLabel.setHorizontalAlignment(JLabel.CENTER);
                matrixTable.add(numberLabel);
            }
        }

        matrixPanel.add(new JLabel("Macierz"), BorderLayout.NORTH);
        matrixPanel.add(matrixTable, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void updateTransformPanel() {
        transformPanel.removeAll();

        // Przesuwanie
        AxisTextField xMoveField = new AxisTextField("0");
        AxisTextField yMoveField = new AxisTextField("0");
        AxisTextField zMoveField = new AxisTextField("0");
        JButton moveButton = new JButton("Przesuń");

        JPanel movePanel = new JPanel();
        movePanel.setBackground(Color.lightGray);

        movePanel.add(new JLabel("x:"));
        movePanel.add(xMoveField);
        movePanel.add(new JLabel("y:"));
        movePanel.add(yMoveField);
        movePanel.add(new JLabel("z:"));
        movePanel.add(zMoveField);
        movePanel.add(moveButton);

        // Obracanie
        AxisTextField xAngleRotateField = new AxisTextField("0");
        JButton xRotateButton = new JButton("Obróć OX");
        AxisTextField yAngleRotateField = new AxisTextField("0");
        JButton yRotateButton = new JButton("Obróć OY");
        AxisTextField zAngleRotateField = new AxisTextField("0");
        JButton zRotateButton = new JButton("Obróć OZ");

        JPanel xRotatePanel = new JPanel();
        xRotatePanel.add(new JLabel("kąt ox:"));
        xRotatePanel.add(xAngleRotateField);
        xRotatePanel.add(xRotateButton);

        JPanel yRotatePanel = new JPanel();
        yRotatePanel.add(new JLabel("kąt oy:"));
        yRotatePanel.add(yAngleRotateField);
        yRotatePanel.add(yRotateButton);

        JPanel zRotatePanel = new JPanel();
        zRotatePanel.add(new JLabel("kąt oz:"));
        zRotatePanel.add(zAngleRotateField);
        zRotatePanel.add(zRotateButton);

        // Kolor
        xRotatePanel.setBackground(Color.lightGray);
        yRotatePanel.setBackground(Color.lightGray);
        zRotatePanel.setBackground(Color.lightGray);

        // Skalowanie
        AxisTextField xScaleField = new AxisTextField("1");
        AxisTextField yScaleField = new AxisTextField("1");
        AxisTextField zScaleField = new AxisTextField("1");
        JButton scaleButton = new JButton("Skaluj");

        JPanel scalePanel = new JPanel();
        scalePanel.setBackground(Color.lightGray);

        scalePanel.add(new JLabel("x:"));
        scalePanel.add(xScaleField);
        scalePanel.add(new JLabel("y:"));
        scalePanel.add(yScaleField);
        scalePanel.add(new JLabel("z:"));
        scalePanel.add(zScaleField);
        scalePanel.add(scaleButton);

        // Dodanie nasłuchów
        // Przesunięcie
        moveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(AxisTextField.validateField(xMoveField.getText()) &&
                        AxisTextField.validateField(yMoveField.getText()) &&
                    AxisTextField.validateField(zMoveField.getText())) {
                    double x = Double.parseDouble(xMoveField.getText());
                    double y = Double.parseDouble(yMoveField.getText());
                    double z = Double.parseDouble(zMoveField.getText());

                    vector3dImageController.move(x, y, z);

                    updateMatrixPanel(vector3dImageController.getMatrix());
                    updateDrawPanel();

                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });

        // Obroty
        xRotateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(AxisTextField.validateField(xAngleRotateField.getText())) {
                    double angleDegree = Double.parseDouble(xAngleRotateField.getText());

                    vector3dImageController.rotateOX(angleDegree);

                    updateMatrixPanel(vector3dImageController.getMatrix());
                    updateDrawPanel();
                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });
        yRotateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(AxisTextField.validateField(yAngleRotateField.getText())) {
                    double angleDegree = Double.parseDouble(yAngleRotateField.getText());

                    vector3dImageController.rotateOY(angleDegree);

                    updateMatrixPanel(vector3dImageController.getMatrix());
                    updateDrawPanel();
                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });
        zRotateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(AxisTextField.validateField(zAngleRotateField.getText())) {
                    double angleDegree = Double.parseDouble(zAngleRotateField.getText());

                    vector3dImageController.rotateOZ(angleDegree);

                    updateMatrixPanel(vector3dImageController.getMatrix());
                    updateDrawPanel();
                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });

        // Skalowanie
        scaleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(AxisTextField.validateField(xScaleField.getText()) &&
                        AxisTextField.validateField(yScaleField.getText()) &&
                    AxisTextField.validateField(zScaleField.getText())) {
                    double x = Double.parseDouble(xScaleField.getText());
                    double y = Double.parseDouble(yScaleField.getText());
                    double z = Double.parseDouble(zScaleField.getText());

                    vector3dImageController.scale(x, y, z);

                    updateMatrixPanel(vector3dImageController.getMatrix());
                    updateDrawPanel();
                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });

        // Dodanie pól edycyjnych
        JPanel transformationPanel = new JPanel(new GridLayout(5, 1));
        transformationPanel.setBackground(Color.lightGray);

        transformationPanel.add(movePanel);
        transformationPanel.add(xRotatePanel);
        transformationPanel.add(yRotatePanel);
        transformationPanel.add(zRotatePanel);
        transformationPanel.add(scalePanel);

        transformPanel.add(new JLabel("Transformacja"), BorderLayout.NORTH);
        transformPanel.add(transformationPanel, BorderLayout.CENTER);

        revalidate();
        repaint();

    }

    public void updatePoint3dListPanel(ArrayList<Point3dModel> points3d) {
        point3dListPanel.removeAll();

        // ustawienie wartości
        String[] columnNames = {"numer", "x", "y", "z"};
        Object[][] data = new Object[points3d.size()][4];
        int i = 0;
        for(Point3dModel point : points3d) {
            data[i][0] = i;
            data[i][1] = point.getX();
            data[i][2] = point.getY();
            data[i][3] = point.getZ();
            i++;
        }

        JTable pointTable = new JTable(data, columnNames);
        // Wyśrodkowanie zawartości komórek
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int col = 0; col < pointTable.getColumnCount(); col++) {
            pointTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(pointTable);
        scrollPane.setBackground(Color.lightGray);

        point3dListPanel.add(new JLabel("Punkty"), BorderLayout.NORTH);
        point3dListPanel.add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void updateWall3dListPanel(ArrayList<Wall3dModel> walls3d) {
        wall3dListPanel.removeAll();

        int cols = 3; // default
        if(!walls3d.isEmpty()) {
            cols = walls3d.get(0).getPointAmount() + 1;
        }

        String[] columnNames = new String[cols];

        int i;
        for(i = 0; i < cols - 1; i++) {
            columnNames[i] = "p" + i;
        }
        columnNames[i] = "Kolor";

        // ustawienie wartości
        Object[][] data = new Object[walls3d.size()][cols];
        int j = 0;
        for(Wall3dModel wall : walls3d) {
            for(int c = 0; c < wall.getPointAmount(); c++) {
                data[j][c] = wall.getPointAt(c);
            }
            Color color = wall.getWallColor();
            data[j][cols - 1] = "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
            j++;
        }

        JTable wallTable = new JTable(data, columnNames);
        // Wyśrodkowanie zawartości komórek
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int col = 0; col < wallTable.getColumnCount(); col++) {
            wallTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(wallTable);
        scrollPane.setBackground(Color.lightGray);

        wall3dListPanel.add(new JLabel("Ściany"), BorderLayout.NORTH);
        wall3dListPanel.add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void updateDrawPanel() {
        drawPanel.removeAll();

        Draw3dPanel drawingPanel = draw3dPanel;
        drawingPanel.setBackground(Color.white);

        drawPanel.add(drawingPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    class AxisTextField extends JTextField{

        public AxisTextField() {
            setPreferredSize(new Dimension(30, 20));
            setText("0");
            setHorizontalAlignment(JTextField.CENTER);
        }

        public AxisTextField(String text) {
            setPreferredSize(new Dimension(30, 20));
            setText(text);
            setHorizontalAlignment(JTextField.CENTER);
        }

        public static boolean validateField(String text) {
            try {
                Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

    }

}
