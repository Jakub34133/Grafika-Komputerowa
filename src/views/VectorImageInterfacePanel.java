package views;

import controllers.VectorImageController;
import models.PointModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VectorImageInterfacePanel extends JPanel {
    private final Dimension parentSize;
    private final MainFrame parent;

    private final DrawPointPanel drawPointPanel;

    private final JPanel drawPanel;
    private final JPanel optionPanel;

    private final JPanel pointListPanel;
    private final JPanel transformPanel;
    private final JPanel matrixPanel;

    private final VectorImageController vectorImageController;

    private boolean changingRoute;
    private int changingRouteIndexPoint;

    public VectorImageController getVectorImageController() {
        return vectorImageController;
    }

    public DrawPointPanel getDrawPointPanel() {
        return drawPointPanel;
    }

    public VectorImageInterfacePanel(VectorImageController vectorImageController, MainFrame parent) {
        this.parent = parent;
        this.vectorImageController = vectorImageController;

        setLayout(new BorderLayout(5, 5));
        setBackground(Color.darkGray);

        parentSize = new Dimension(parent.getWidth(), parent.getHeight());

        // Inicjalizacja poszczególnych okien
        this.drawPointPanel = new DrawPointPanel(this);

        this.drawPanel = new JPanel(new BorderLayout());
        this.optionPanel = new JPanel(new BorderLayout());

        this.pointListPanel = new JPanel(new BorderLayout());
        this.transformPanel = new JPanel(new BorderLayout());
        this.matrixPanel = new JPanel(new BorderLayout());

        // Inicjalizacja kontrolera
//        this.vectorImageController = new VectorImageController(this);

        optionPanel.setBackground(Color.darkGray);
        optionPanel.setPreferredSize(new Dimension((int)parentSize.getWidth() / 4, 0));

        setupOptionPanel();
        double[][] matrixEx = vectorImageController.getMatrix(); // default matrix
        updateMatrixPanel(matrixEx);
        updateTransformPanel();

        ArrayList<PointModel> list = vectorImageController.getPoints(); // default points
        updatePointListPanel(list);

        updateDrawPanel();

        add(drawPanel, BorderLayout.CENTER);
        add(optionPanel, BorderLayout.EAST);
    }

    private void setupOptionPanel() {
        // tło okien
        pointListPanel.setBackground(Color.lightGray);
        transformPanel.setBackground(Color.lightGray);
        matrixPanel.setBackground(Color.lightGray);

        // Wysokości okien
        pointListPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 3));
        transformPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 3));
        matrixPanel.setPreferredSize(new Dimension(0, (int)parentSize.getHeight() / 3));

        optionPanel.setLayout(new BorderLayout(5,5));

        // dodanie okien
        optionPanel.add(pointListPanel, BorderLayout.NORTH);
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

        JPanel transformationPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        transformationPanel.setBackground(Color.lightGray);

        // Przesuwanie
        AxisTextField xMoveField = new AxisTextField("50");
        AxisTextField yMoveField = new AxisTextField("50");
        JButton moveButton = new JButton("Przesuń");

        JPanel movePanel = new JPanel();
        movePanel.setBackground(Color.lightGray);

        movePanel.add(new JLabel("x:"));
        movePanel.add(xMoveField);
        movePanel.add(new JLabel("y:"));
        movePanel.add(yMoveField);
        movePanel.add(moveButton);

        // Obracanie
        AxisTextField angleRotateField = new AxisTextField("90");
        JButton rotateButton = new JButton("Obróć");

        JPanel rotatePanel = new JPanel();
        rotatePanel.setBackground(Color.lightGray);

        rotatePanel.add(new JLabel("kąt:"));
        rotatePanel.add(angleRotateField);
        rotatePanel.add(rotateButton);

        // Skalowanie
        AxisTextField xScaleField = new AxisTextField("1");
        AxisTextField yScaleField = new AxisTextField("1");
        JButton scaleButton = new JButton("Skaluj");

        JPanel scalePanel = new JPanel();
        scalePanel.setBackground(Color.lightGray);

        scalePanel.add(new JLabel("x:"));
        scalePanel.add(xScaleField);
        scalePanel.add(new JLabel("y:"));
        scalePanel.add(yScaleField);
        scalePanel.add(scaleButton);

        // Dodanie nasłuchów
        moveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(AxisTextField.validateField(xMoveField.getText()) &&
                        AxisTextField.validateField(yMoveField.getText())) {
                    double x = Double.valueOf(xMoveField.getText());
                    double y = Double.valueOf(yMoveField.getText());

                    vectorImageController.move(x, y);

                    updateMatrixPanel(vectorImageController.getMatrix());

                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });
        rotateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(AxisTextField.validateField(angleRotateField.getText())) {
                    double angleDegree = Double.valueOf(angleRotateField.getText());

                    vectorImageController.rotate(angleDegree);

                    updateMatrixPanel(vectorImageController.getMatrix());

                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }


            }
        });
        scaleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(AxisTextField.validateField(xScaleField.getText()) &&
                        AxisTextField.validateField(yScaleField.getText())) {
                    double x = Double.valueOf(xScaleField.getText());
                    double y = Double.valueOf(yScaleField.getText());

                    vectorImageController.scale(x, y);

                    updateMatrixPanel(vectorImageController.getMatrix());

                } else {
                    JOptionPane.showMessageDialog(parent, "Niepoprawne dane!");
                }
            }
        });

        // Dodanie pól edycyjnych
        transformationPanel.add(movePanel);
        transformationPanel.add(rotatePanel);
        transformationPanel.add(scalePanel);

        transformPanel.add(new JLabel("Transformacja"), BorderLayout.NORTH);
        transformPanel.add(transformationPanel, BorderLayout.CENTER);

        revalidate();
        repaint();

    }

    public void updatePointListPanel(ArrayList<PointModel> points) {
        pointListPanel.removeAll();

        // ustawienie wartości
        String[] columnNames = {"x", "y"};
        Object[][] data = new Object[points.size()][2];
        int i = 0;
        for(PointModel point : points) {
            data[i][0] = point.getX();
            data[i][1] = point.getY();
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

        pointListPanel.add(new JLabel("Punkty"), BorderLayout.NORTH);
        pointListPanel.add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void updateDrawPanel() {
        drawPanel.removeAll();

        DrawPointPanel drawingPanel = drawPointPanel;
        drawingPanel.setBackground(Color.white);
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                double x = e.getX();
                double y = e.getY();

                if(e.getButton() == 1) {

                    /*
                       if changing route:
                            add point in middle
                       else:
                            search if clicked on point,
                            if clicked:
                                change route = true
                            else:
                                add point at end
                    */
                    if(getVectorImageController().getTransformedPoints().isEmpty()) {
                        changingRoute = false;
                    }
                    if(changingRoute) {
                        // Przycisk lewy - dodawanie punktu w środku
                        vectorImageController.addPoint(changingRouteIndexPoint, x, y);
                        updatePointListPanel(vectorImageController.getPoints());
                        drawingPanel.repaint();

//                        System.out.println("+środek");
                        changingRoute = false;

                    } else {
                        // Przycisk lewy - wykrycie kliknięcia na istniejący punkt
                        boolean pressedPoint = false;
                        int i = 0;
                        for(PointModel point : getVectorImageController().getTransformedPoints()) {
                            double px = point.getX();
                            double py = point.getY();

                            double distance = Math.sqrt((px-x)*(px-x) + (py-y)*(py-y));
                            if(distance <= DrawPointPanel.RADIUS*2) {
                                pressedPoint = true;
                                changingRouteIndexPoint = i;
                                break;
                            }
                            i++;
                        }

                        if(pressedPoint) {
                            // zmień trase
//                            System.out.println("+zmień trase");
                            changingRoute = true;

                        } else {
                            // Dodawanie punktu na końcu
                            vectorImageController.addPoint(x, y);
                            updatePointListPanel(vectorImageController.getPoints());
                            drawingPanel.repaint();

//                            System.out.println("+koniec");
                            changingRoute = false;
                        }
                    }

                } else if(e.getButton() == 3) {
                    // Przycisk prawy - usuwanie
//                    System.out.println("punkt usunięty!");
                    int i = 0;
                    for(PointModel point : getVectorImageController().getTransformedPoints()) {
                        double px = point.getX();
                        double py = point.getY();

                        double distance = Math.sqrt((px-x)*(px-x) + (py-y)*(py-y));
                        if(distance <= DrawPointPanel.RADIUS*2) {
                            vectorImageController.removePoint(i);
                            break;
                        }
                        i++;
                    }
                    drawingPanel.repaint();
                }
                updatePointListPanel(vectorImageController.getPoints());
            } // MouseClicked()
        });

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
