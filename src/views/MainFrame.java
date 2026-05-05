package views;

import controllers.*;
import models.CircleModel;
import models.MaskModel;
import models.RectangleModel;
import models.Solid3dModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    // Domyślne wymiary okna aplikacji
    private final static Integer DEFAULT_WIDTH = 800;
    private final static Integer DEFAULT_HEIGHT = 600;

    // Panele do wyświetlania obrazów
    private final ImagePanel leftPanel;
    private final ImagePanel rightPanel;

    // Paski menu aplikacji
    private final RasterImageMenuBar rasterImageMenuBar;
    private final VectorImageMenuBar vectorImageMenuBar;
    private final Vector3dImageMenuBar vector3dImageMenuBar;

    // Kontrolery obsługujące obrazy oraz pliki
    private final ImageController imageController;
    private final FileController fileController;
    private final VectorImageController vectorImageController;
    private final Vector3dImageController vector3dImageController;

    // Panel z główną zawartością
    private final JPanel mainContentPanel;

    // Interfejsy
    private final VectorImageInterfacePanel vectorImageInterfacePanel;
    private final Vector3dImageInterfacePanel vector3dImageInterfacePanel;

    public MainFrame() {
        super("Grafika komputerowa");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicjalizacja komponentów
        leftPanel = new ImagePanel("Obraz wczytany");
        rightPanel = new ImagePanel("Obraz zmodyfikowany");
        rasterImageMenuBar = new RasterImageMenuBar();
        vectorImageMenuBar = new VectorImageMenuBar();
        vector3dImageMenuBar = new Vector3dImageMenuBar();

        // Inicjalizacja kontrolerów
        imageController = new ImageController(this);
        fileController = new FileController(this);
        vectorImageController = new VectorImageController(this);
        vector3dImageController = new Vector3dImageController(this);

        vectorImageInterfacePanel = new VectorImageInterfacePanel(vectorImageController, this);
        vector3dImageInterfacePanel = new Vector3dImageInterfacePanel(vector3dImageController, this);


        // Utworzenie panelu z głowną zawartością
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        add(mainContentPanel);

        // Nasłuch na menu
        setRasterImageMenuBarListeners(); // Ustawienie nasłuchu na zdarzenia wywołania opcji z RasterImgageMenubar
        setVectorImageMenuBarListeners(); // Ustawienie nasłuchu na zdarzenia wywołania opcji z VectorImageMenubar
        setVector3dImageMenuBarListeners(); // Ustawienie nasłuchu na zdarzenia wywołania opcji z Vector3dImageMenubar

        // Ustaw menu i interfejs domyślny
        setInterfaceToVector3dImage();

        setLocationRelativeTo(null); // Centrowanie okna na ekranie
        setVisible(true); // Ustawienie widoczności okna

    }

    public ImagePanel getRightPanel() {
        return rightPanel;
    }

    public ImagePanel getLeftPanel() {
        return leftPanel;
    }

    /**
     * Dostosowuje rozmiar okna do załadowanego obrazu z lewego panelu.
     *
     * <p>
     * Metoda pobiera obraz z lewego panelu i sprawdza jego rozmiary.
     * Szerokość i wysokość okna są zwiększane w zależności od wymiarów obrazu
     * </p>
     *
     * @see javax.swing.JFrame#setSize(int, int)
     * @see javax.swing.JFrame#setLocationRelativeTo(java.awt.Component)
     */
    public void adjustWindowSize() {
        var image = leftPanel.getModel().getImage();
        if (image == null) {
            return;
        }

        // Obliczenie nowej szerokości okna.
        // Okno powinno mieć co najmniej dwukrotność szerokości obrazu.
        // + 100 - dodaje dodatkową przestrzeń dla marginesów.
        int newWidth = Math.max(getWidth(), image.getWidth() * 2 + 100);

        // Obliczenie nowej wysokości okna.
        // + 100 - dodaje dodatkową przestrzeń dla marginesów.
        int newHeight = Math.max(getHeight(), image.getHeight() + 100);
        setSize(newWidth, newHeight);
        setLocationRelativeTo(null);
    }

    /**
     * <p>Metoda inicjalizację obsługę zdarzeń dla elementów menu w pasku narzędzi.
     * Wykorzystuje wyrażenia lambda jako implementację interfejsu {@code java.awt.event.ActionListener}.<p>
     *
     * <p>Każde wywołanie {@code addActionListener(...)} wymaga przekazania obiektu implementującego interfejs {@code ActionListener}.
     * Zamiast tworzenia anonimowych klas wewnętrznych, wykorzystujemy wyrażenia lambda {@code (_ -> metoda())}.
     * _ jest tutaj symbolem oznaczającym, że argument (obiekt zdarzenia {@code ActionEvent}) nie jest wykorzystywany.
     * Po prawej stronie operatora {@code ->} znajduje się wywołanie metody, które zostanie wykonane po kliknięciu elementu menu.</p>
     *
     * <p>Jako argument {@code addActionListener(...)} można przekazać również anonimową klasę wewnętrzną.</p>
     *
     * <pre>
     * {@code
     *      menuBar.getOpenFileMenuItem().addActionListener(new ActionListener() {
     *          @Override
     *          public void actionPerformed(ActionEvent e) {
     *              showFileChooserDialog();
     *          }
     *      });
     * }
     * </pre>
     *
     * @see java.awt.event.ActionListener
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     * @see javax.swing.AbstractButton#addActionListener(ActionListener)
     */
    private void setRasterImageMenuBarListeners() {
        rasterImageMenuBar.getVectorImageMenuItem().addActionListener(f -> setInterfaceToVectorImage());
        rasterImageMenuBar.getVector3dImageMenuItem().addActionListener(f -> setInterfaceToVector3dImage());

        rasterImageMenuBar.getOpenFileMenuItem().addActionListener(f -> showFileChooserDialog());

        rasterImageMenuBar.getSaveFileMenuItem().addActionListener(f -> showSaveFileDialog());
        rasterImageMenuBar.getExitMenuItem().addActionListener(f -> System.exit(0));

        rasterImageMenuBar.getCopyLeftPanelMenuItem().addActionListener(f -> imageController.copyLeftPanel());
        rasterImageMenuBar.getClearLeftPanelMenuItem().addActionListener(f -> imageController.clearLeftPanel());

        rasterImageMenuBar.getClearRightPanelMenuItem().addActionListener(f -> imageController.clearRightPanel());
        rasterImageMenuBar.getCopyRightPanelMenuItem().addActionListener(f -> imageController.copyRightPanel());

        rasterImageMenuBar.getDrawCircleMenuItem().addActionListener(f -> showCircleDialog());
        rasterImageMenuBar.getDrawRectangleMenuItem().addActionListener(f -> showRectangleDialog());
        rasterImageMenuBar.getDrawHorizontalLinesMenuItem().addActionListener(f -> showLinesDialog());
        rasterImageMenuBar.getDrawBoardMenuItem().addActionListener(f -> showBoard());

        rasterImageMenuBar.getGrayPanelMenuItem().addActionListener(f -> changeGrayByComponent());
        rasterImageMenuBar.getGrayAvgPanelMenuItem().addActionListener(f -> changeAvgGray());
        rasterImageMenuBar.getGrayYUVPanelMenuItem().addActionListener(f -> changeYUVGray());
        rasterImageMenuBar.getSaturationMenuItem().addActionListener(f -> showSaturationDialog());
        rasterImageMenuBar.getContrastMenuItem().addActionListener(f -> showContrastDialog());
        rasterImageMenuBar.getNegativeMenuItem().addActionListener(f -> changeNegative());
        rasterImageMenuBar.getSaturationRangeMenuItem().addActionListener(f -> chageSaturationRange());

        rasterImageMenuBar.getMaskMenuItem().addActionListener(f -> mixWithMask());
        rasterImageMenuBar.getMedianMenuItem().addActionListener(f -> medianFilter());
        rasterImageMenuBar.getMinimumMenuItem().addActionListener(f -> minimumFilter());
        rasterImageMenuBar.getMaximumMenuItem().addActionListener(f -> maximumFilter());
        rasterImageMenuBar.getSimpleGradientMenuItem().addActionListener(f -> simpleGradientFilter());
        rasterImageMenuBar.getRobertsGradientMenuItem().addActionListener(f -> robertsGradientFilter());
        rasterImageMenuBar.getThresholdingGradientMenuItem().addActionListener(f -> thresholdingGradientFilter());

    }

//    MenuBar dla obrazów wektorowych
    private void setVectorImageMenuBarListeners() {
        vectorImageMenuBar.getRasterImageMenuItem().addActionListener(f -> setInterfaceToRasterImage());
        vectorImageMenuBar.getVector3dImageMenuItem().addActionListener(f -> setInterfaceToVector3dImage());

        vectorImageMenuBar.getStraightLineMenuItem().addActionListener(f -> drawStraightLine());
        vectorImageMenuBar.getCurvedLineMenuItem().addActionListener(f -> drawCurvedLine());
        vectorImageMenuBar.getClearPointsMenuItem().addActionListener(f -> showClearPointsDialog());
    }

    private void setVector3dImageMenuBarListeners() {
        vector3dImageMenuBar.getRasterImageMenuItem().addActionListener(f -> setInterfaceToRasterImage());
        vector3dImageMenuBar.getVectorImageMenuItem().addActionListener(f -> setInterfaceToVectorImage());

        vector3dImageMenuBar.getCubeMenuItem().addActionListener(f -> drawSolid("src/szescian.txt"));
        vector3dImageMenuBar.getPyramidMenuItem().addActionListener(f -> drawSolid("src/ostroslup.txt"));
        vector3dImageMenuBar.getClearMenuItem().addActionListener(f -> showClear3dDialog());

        vector3dImageMenuBar.getPointRadioMenuItem().addActionListener(f -> swapPointVisibility());
        vector3dImageMenuBar.getWireRadioMenuItem().addActionListener(f -> swapLineVisibility());
        vector3dImageMenuBar.getSolidRadioMenuItem().addActionListener(f -> swapWallVisibility());

        vector3dImageMenuBar.getObserverDistanceMenuItem().addActionListener(f -> showObserverDistanceDialog());
    }

//    Metody zmieniające MenuBar dla obrazów rastrowych / wektorowych
    private void setInterfaceToRasterImage() {
        setJMenuBar(rasterImageMenuBar); // Ustawienie menu na metody dla obrazów rastrowych

        mainContentPanel.removeAll();
        // Utworzenie kontenera do organizacji komponentów interfejsu użytkownika, ustawiamy siatkę 1x2 dla 2 paneli z obrazami
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);

        // przewalidowanie i przerysowanie interfejsu
        revalidate();
        repaint();
    }

    private void setInterfaceToVectorImage() {
        setJMenuBar(vectorImageMenuBar); // Ustawienie menu na metody dla obrazów wektorowych

        mainContentPanel.removeAll();

//        JPanel contentPanel = new VectorImageInterfacePanel(vectorImageController, mainContentPanel);
        mainContentPanel.add(vectorImageInterfacePanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Centrowanie okna na ekranie
        setVisible(true); // Ustawienie widoczności okna

        // przewalidowanie i przerysowanie interfejsu
        revalidate();
        repaint();
    }

    private void setInterfaceToVector3dImage() {
        setJMenuBar(vector3dImageMenuBar); // Ustawienie menu na metody dla obrazów wektorowych

        mainContentPanel.removeAll();

//        JPanel contentPanel = new VectorImageInterfacePanel(vectorImageController, mainContentPanel);
        mainContentPanel.add(vector3dImageInterfacePanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Centrowanie okna na ekranie
        setVisible(true); // Ustawienie widoczności okna

        // przewalidowanie i przerysowanie interfejsu
        revalidate();
        repaint();
    }

    /*
        Metody dla obrazów wektorowych 3D
     */
    private void showObserverDistanceDialog() {
        ObserverDistanceDialog dialog = new ObserverDistanceDialog(this);
        dialog.setVisible(true);

        double distance = dialog.getDistance();
        vector3dImageInterfacePanel.getDraw3dPanel().setObserverDistance(distance);
        vector3dImageInterfacePanel.updateDrawPanel();
    }

    private void swapPointVisibility() {
        boolean pointVisible = vector3dImageInterfacePanel.getDraw3dPanel().isPointsVisible();
        vector3dImageInterfacePanel.getDraw3dPanel().setPointsVisible(!pointVisible);
        vector3dImageInterfacePanel.updateDrawPanel();
    }

    private void swapLineVisibility() {
        boolean linesVisible = vector3dImageInterfacePanel.getDraw3dPanel().isStraightLinesVisible();
        vector3dImageInterfacePanel.getDraw3dPanel().setStraightLinesVisible(!linesVisible);
        vector3dImageInterfacePanel.updateDrawPanel();
    }

    private void swapWallVisibility() {
        boolean wallVisible = vector3dImageInterfacePanel.getDraw3dPanel().isWallsVisible();
        vector3dImageInterfacePanel.getDraw3dPanel().setWallsVisible(!wallVisible);
        vector3dImageInterfacePanel.updateDrawPanel();
    }


    private void showClear3dDialog() {
        Object[] choices = {"Tak", "Nie"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "Wyczyścić panel?",
                "Wyczyść panel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice
        );

        if(choice == 0) {
            // Tak
            vector3dImageController.clearMatrix();
            vector3dImageController.clearPoints3d();
            vector3dImageController.clearWalls3d();

            vector3dImageInterfacePanel.updateMatrixPanel(vector3dImageController.getMatrix());
            vector3dImageInterfacePanel.updateDrawPanel();
            vector3dImageInterfacePanel.updatePoint3dListPanel(vector3dImageController.getPoints3d());
            vector3dImageInterfacePanel.updateWall3dListPanel(vector3dImageController.getWalls3d());
        }
    }

    public void drawSolid(String filepath) {
        Solid3dModel solid3d = vector3dImageController.readSolidFromFile(filepath);

        vector3dImageInterfacePanel.updatePoint3dListPanel(solid3d.getPoints3d());
        vector3dImageInterfacePanel.updateWall3dListPanel(solid3d.getWalls3d());
    }

    /*
        Metody dla obrazów wektorowych 2D
     */
    private void showClearPointsDialog() {
        Object[] choices = {"Tak", "Nie"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "Wyczyścić panel?",
                "Wyczyść panel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice
        );

        if(choice == 0) {
            // Tak
            vectorImageController.clearPoints();
            vectorImageController.clearMatrix();

            vectorImageInterfacePanel.updatePointListPanel(vectorImageController.getPoints());
            vectorImageInterfacePanel.updateMatrixPanel(vectorImageController.getMatrix());
        }
    }

    private void drawStraightLine() {
        boolean isSelected = vectorImageMenuBar.getStraightLineMenuItem().isSelected();

        vectorImageInterfacePanel.getDrawPointPanel().setStraightLinesVisible(isSelected);

        revalidate();
        repaint();
    }

    private void drawCurvedLine() {
        boolean isSelected = vectorImageMenuBar.getCurvedLineMenuItem().isSelected();

        if(isSelected) {
            CurveAccuracyDialog dialog = new CurveAccuracyDialog(this);
            dialog.setVisible(true);

            if(dialog.getConfirmed()) {
                double accuracy = dialog.getCurveAccuracy();
                vectorImageInterfacePanel.getDrawPointPanel().setCurvedLineStep(accuracy);

                vectorImageInterfacePanel.getDrawPointPanel().setCurvedLinesVisible(true);
                vectorImageMenuBar.getCurvedLineMenuItem().setSelected(true);
            } else {
                vectorImageInterfacePanel.getDrawPointPanel().setCurvedLinesVisible(false);
                vectorImageMenuBar.getCurvedLineMenuItem().setSelected(false);
            }
        } else {
            vectorImageInterfacePanel.getDrawPointPanel().setCurvedLinesVisible(false);
            vectorImageMenuBar.getCurvedLineMenuItem().setSelected(false);
        }


        revalidate();
        repaint();
    }

    /*
        Metody dla obrazów rastrowych
     */

    /**
     * Metoda otwiera okno dialogowe umożliwiające użytkownikowi wprowadzenie parametrów prostokąta, który zostanie narysowany na wczytanym obrazie.
     */
    private void showRectangleDialog() {
        RectangleDialog dialog = new RectangleDialog(this);
        dialog.setVisible(true);
        RectangleModel rect = dialog.getRectangle();

        if (rect != null) {
            imageController.drawRectangle(rect);
        }
    }

    private void showLinesDialog() {
        LinesDialog dialog = new LinesDialog(this);
        dialog.setVisible(true);
        int positionY = dialog.getPositionY();
        int lineWidth = dialog.getLineWidth();
        int spacing = dialog.getSpacing();
        Color selectedColor = dialog.getSelectedColor();

        imageController.drawHorizontalLines(positionY, lineWidth, spacing, selectedColor);
    }

    private void showBoard() {
        BoardDialog dialog = new BoardDialog(this);
        dialog.setVisible(true);
        int spacing = dialog.getSpacing();
        int thickness = dialog.getThickness();
        Color selectedColor = dialog.getSelectedColor();

        imageController.drawBoard(spacing, thickness, selectedColor);
    }

    private void changeGrayByComponent() {
        GrayDialog dialog = new GrayDialog(this);
        dialog.setVisible(true);
        int colorComponent = dialog.getOption();

        imageController.drawGray(colorComponent);

    }

    private void changeAvgGray() {
        imageController.drawAvgGray();
    }

    private void changeYUVGray() {
        imageController.drawYUVGray();
    }

    private void showSaturationDialog() {
        SaturationDialog dialog = new SaturationDialog(this);
        dialog.setVisible(true);

        imageController.changeSaturation(dialog.getSaturation());

    }

    private void showContrastDialog() {
        ContrastDialog dialog = new ContrastDialog(this);
        dialog.setVisible(true);

        imageController.changeContrast(dialog.getContrast());

    }

    private void changeNegative() {
        imageController.changeNegative();
    }

    private void chageSaturationRange() {
        imageController.changeSaturationRange();
    }
/*
    Filtry
 */
    private void mixWithMask() {
        // Pobranie masek z pliku
        FileReaderController fileReader = new FileReaderController();
        ArrayList<MaskModel> maskList = fileReader.maskReader();

        // Wyświetlenie dialogu do wybrania maski z pliku
        MaskDialog dialog = new MaskDialog(this, maskList);
        dialog.setVisible(true);

        imageController.mixWithMask(dialog.getMaskModel());
    }

    private void medianFilter() {
        imageController.medianFilter();
    }

    private void minimumFilter() {
        imageController.minimumFilter();
    }

    private void maximumFilter() {
        imageController.maximumFilter();
    }

    private void simpleGradientFilter() {
        imageController.simpleGradientFilter();
    }

    private void robertsGradientFilter() {
        imageController.robertsGradientFilter();
    }

    private void thresholdingGradientFilter() {
        // Wyświetlenie dialogu do wprowadzania wartości
        ThresholdingGradientDialog dialog = new ThresholdingGradientDialog(this);
        dialog.setVisible(true);

        imageController.thresholdingGradientFilter(dialog.getThreshold(), dialog.getOption());
    }

    /**
     * Metoda otwiera okno dialogowe umożliwiające użytkownikowi wprowadzenie parametrów koła, który zostanie narysowane na wczytanym obrazie.
     *
     * <p>
     * Tworzy instancję okna dialogowego {@code CircleDialog}, który wyświetla formularz do wprowadzania parametrów koła.
     * Po utworzeniu instancji okna dialogowego należy pokazać komponent przez wywołanie funkcji {@code dialog.setVisible(true)}
     * </p>
     *
     * @see CircleDialog
     * @see CircleModel
     * @see ImageController#drawCircle(CircleModel)
     */
    private void showCircleDialog() {
        CircleDialog dialog = new CircleDialog(this);
        dialog.setVisible(true);
        CircleModel circle = dialog.getCircle();

        if (circle != null) {
            imageController.drawCircle(circle);
        }
    }

    /**
     * Metoda otwiera okno dialogowe wyboru pliku graficznego.
     *
     * <p>Tworzy instancję {@code JFileChooser}, umożliwiając użytkownikowi wybór pliku.
     * Przekazuje plik do kontrolera {@code ImageController} w celu załadowania zdjęcia do lewego panelu graficznego.</p>
     *
     * @see JFileChooser
     * @see ImageController#loadImage(File)
     */
    private void showFileChooserDialog() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\kubaj\\Desktop\\Studia\\Semestr 4\\Grafika Komputerowa\\obrazy");
        fileChooser.setDialogTitle("Wybierz plik graficzny");
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            imageController.loadImage(file);
        }
    }

    /**
     * Metoda otwiera okno dialogowe zapisu pliku graficznego.
     *
     * <p>
     * Tworzy instancję {@code JFileChooser}, ustawiając filtr plików obsługujący formaty BMP i PNG.
     * Po wybraniu miejscu zapisu przekazuje plik do kontrolera {@code FileController} w celu zapisania obrazu z prawego panelu graficznego.
     * </p>
     *
     * @see JFileChooser
     * @see FileController#saveFile(File)
     */
    private void showSaveFileDialog() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\kubaj\\Desktop\\Studia\\Semestr 4\\Grafika Komputerowa\\obrazy");
        fileChooser.setDialogTitle("Zapisz obraz");
        fileChooser.setFileFilter(new FileNameExtensionFilter("BMP & PNG Images", "bmp", "png"));
        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileController.saveFile(file);
        }
    }
}