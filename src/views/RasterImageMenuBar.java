package views;

import javax.swing.*;

/**
 * Pasek menu aplikacji, zawierający opcje zarządzania plikami, edycji oraz operacji na panelach.
 * Klasa rozszerza {@link JMenuBar} i definiuje strukturę menu dla aplikacji.
 */
public class RasterImageMenuBar extends JMenuBar {

    private final JMenu imageMenu;
    private final JMenu fileMenu;
    private final JMenu leftPanelMenu;
    private final JMenu rightPanelMenu;
    private final JMenu editPanelMenu;
    private final JMenu processingPanelMenu;
    private final JMenu filterPanelMenu;

    private final JMenuItem rasterImageMenuItem;
    private final JMenuItem vectorImageMenuItem;
    private final JMenuItem vector3dImageMenuItem;

    private final JMenuItem openFileMenuItem;
    private final JMenuItem saveFileMenuItem;
    private final JMenuItem exitMenuItem;

    private final JMenuItem clearLeftPanelMenuItem;
    private final JMenuItem copyLeftPanelMenuItem;

    private final JMenuItem clearRightPanelMenuItem;
    private final JMenuItem copyRightPanelMenuItem;

    private final JMenuItem drawCircleMenuItem;
    private final JMenuItem drawRectangleMenuItem;
    private final JMenuItem drawHorizontalLinesMenuItem;
    private final JMenuItem drawBoardMenuItem;

    private final JMenuItem grayPanelMenuItem;
    private final JMenuItem grayAvgPanelMenuItem;
    private final JMenuItem grayYUVPanelMenuItem;
    private final JMenuItem saturationMenuItem;
    private final JMenuItem contrastMenuItem;
    private final JMenuItem negativeMenuItem;
    private final JMenuItem saturationRangeMenuItem;

    private final JMenuItem maskMenuItem;
    private final JMenuItem medianMenuItem;
    private final JMenuItem minimumMenuItem;
    private final JMenuItem maximumMenuItem;
    private final JMenuItem simpleGradientMenuItem;
    private final JMenuItem robertsGradientMenuItem;
    private final JMenuItem thresholdingGradientMenuItem;

    public RasterImageMenuBar() {
        // Tworzenie głównych menu
        imageMenu = new JMenu("Obraz");
        fileMenu = new JMenu("Plik");
        leftPanelMenu = new JMenu("Lewy panel");
        rightPanelMenu = new JMenu("Prawy panel");
        editPanelMenu = new JMenu("Edycja");
        processingPanelMenu = new JMenu("Przetwarzanie");
        filterPanelMenu = new JMenu("Filtry");

        // Menu obraz
        rasterImageMenuItem = new JMenuItem("Rastrowy");
        vectorImageMenuItem = new JMenuItem("Wektorowy 2D");
        vector3dImageMenuItem = new JMenuItem("Wektorowy 3D");

        // Menu plik
        openFileMenuItem = new JMenuItem("Otwórz");
        saveFileMenuItem = new JMenuItem("Zapisz");
        exitMenuItem = new JMenuItem("Zakończ");

        // Menu Panel lewy
        clearLeftPanelMenuItem = new JMenuItem("Wyczyść");
        copyLeftPanelMenuItem = new JMenuItem("Kopiuj obraz");

        // Menu Panel prawy
        clearRightPanelMenuItem = new JMenuItem("Wyczyść");
        copyRightPanelMenuItem = new JMenuItem("Kopiuj obraz");

        // Menu Edycja
        drawCircleMenuItem = new JMenuItem("Narysuj koło");
        drawRectangleMenuItem = new JMenuItem("Narysuj prostokąt");
        drawHorizontalLinesMenuItem = new JMenuItem("Narysuj Linie");
        drawBoardMenuItem = new JMenuItem("Narysuj szachownice");

        // Menu Przetwarzanie
        grayPanelMenuItem = new JMenuItem("Szarość wg. 1 składowej");
        grayAvgPanelMenuItem = new JMenuItem("Szarość wg. średniej");
        grayYUVPanelMenuItem = new JMenuItem("Szarość wg. YUV");
        saturationMenuItem = new JMenuItem("Zmiana Jasności");
        contrastMenuItem = new JMenuItem("Zmiana Kontrastu");
        negativeMenuItem = new JMenuItem("Negatyw");
        saturationRangeMenuItem = new JMenuItem("Zmiana zakresu jasności");

        // Menu Filtry
        maskMenuItem = new JMenuItem("Filtr Splotowy");
        medianMenuItem = new JMenuItem("Filtr Medianowy");
        minimumMenuItem = new JMenuItem("Filtr Minimalny");
        maximumMenuItem = new JMenuItem("Filtr Maksymalny");
        simpleGradientMenuItem = new JMenuItem("Gadient prosty");
        robertsGradientMenuItem = new JMenuItem("Gradient Roberts'a");
        thresholdingGradientMenuItem = new JMenuItem("Gradient z progowaniem");

        // Dodanie elementów do menu Obraz
        imageMenu.add(rasterImageMenuItem);
        imageMenu.add(vectorImageMenuItem);
        imageMenu.add(vector3dImageMenuItem);

        // Dodanie elementów do menu Plik
        fileMenu.add(openFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitMenuItem);

        // Dodanie elementów do menu Panel lewy
        leftPanelMenu.add(clearLeftPanelMenuItem);
        leftPanelMenu.add(copyLeftPanelMenuItem);

        // Dodanie elementów do menu Panel prawy
        rightPanelMenu.add(clearRightPanelMenuItem);
        rightPanelMenu.add(copyRightPanelMenuItem);

        // Dodanie elementów do menu Edycja
        editPanelMenu.add(drawCircleMenuItem);
        editPanelMenu.add(drawRectangleMenuItem);
        editPanelMenu.add(drawHorizontalLinesMenuItem);
        editPanelMenu.add(new JSeparator());
        editPanelMenu.add(drawBoardMenuItem);

        // Dodanie elementów do menu Przetwarzanie
        processingPanelMenu.add(grayPanelMenuItem);
        processingPanelMenu.add(grayAvgPanelMenuItem);
        processingPanelMenu.add(grayYUVPanelMenuItem);
        processingPanelMenu.add(new JSeparator());
        processingPanelMenu.add(saturationMenuItem);
        processingPanelMenu.add(contrastMenuItem);
        processingPanelMenu.add(new JSeparator());
        processingPanelMenu.add(negativeMenuItem);
        processingPanelMenu.add(saturationRangeMenuItem);

        // Dodanie elementów do menu Filtry
        filterPanelMenu.add(maskMenuItem);
        filterPanelMenu.add(new JSeparator());
        filterPanelMenu.add(medianMenuItem);
        filterPanelMenu.add(minimumMenuItem);
        filterPanelMenu.add(maximumMenuItem);
        filterPanelMenu.add(new JSeparator());
        filterPanelMenu.add(simpleGradientMenuItem);
        filterPanelMenu.add(robertsGradientMenuItem);
        filterPanelMenu.add(thresholdingGradientMenuItem);

        // Dodawanie wszystkich menu do paska menu
        add(imageMenu);
        add(fileMenu);
        add(leftPanelMenu);
        add(rightPanelMenu);
        add(editPanelMenu);
        add(processingPanelMenu);
        add(filterPanelMenu);
    }

    public JMenuItem getRasterImageMenuItem() {
        return rasterImageMenuItem;
    }
    public JMenuItem getVectorImageMenuItem() {
        return vectorImageMenuItem;
    }
    public JMenuItem getVector3dImageMenuItem() {
        return vector3dImageMenuItem;
    }

    public JMenuItem getOpenFileMenuItem() {
        return openFileMenuItem;
    }
    public JMenuItem getSaveFileMenuItem() {
        return saveFileMenuItem;
    }
    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JMenuItem getClearLeftPanelMenuItem() {
        return clearLeftPanelMenuItem;
    }
    public JMenuItem getCopyLeftPanelMenuItem() {
        return copyLeftPanelMenuItem;
    }

    public JMenuItem getClearRightPanelMenuItem() {
        return clearRightPanelMenuItem;
    }
    public JMenuItem getCopyRightPanelMenuItem() {
        return copyRightPanelMenuItem;
    }

    public JMenuItem getDrawCircleMenuItem() {
        return drawCircleMenuItem;
    }
    public JMenuItem getDrawRectangleMenuItem() {
        return drawRectangleMenuItem;
    }
    public JMenuItem getDrawHorizontalLinesMenuItem() {
        return drawHorizontalLinesMenuItem;
    }
    public JMenuItem getDrawBoardMenuItem() {
        return drawBoardMenuItem;
    }

    public JMenuItem getGrayPanelMenuItem() {
        return grayPanelMenuItem;
    }
    public JMenuItem getGrayAvgPanelMenuItem() {
        return grayAvgPanelMenuItem;
    }
    public JMenuItem getGrayYUVPanelMenuItem() {
        return grayYUVPanelMenuItem;
    }
    public JMenuItem getSaturationMenuItem() {
        return saturationMenuItem;
    }
    public JMenuItem getContrastMenuItem() {
        return contrastMenuItem;
    }
    public JMenuItem getNegativeMenuItem() {
        return negativeMenuItem;
    }
    public JMenuItem getSaturationRangeMenuItem() {
        return saturationRangeMenuItem;
    }

    public JMenuItem getMaskMenuItem() {
        return maskMenuItem;
    }
    public JMenuItem getMedianMenuItem() {
        return medianMenuItem;
    }
    public JMenuItem getMinimumMenuItem() {
        return minimumMenuItem;
    }
    public JMenuItem getMaximumMenuItem() {
        return maximumMenuItem;
    }
    public JMenuItem getSimpleGradientMenuItem() {
        return simpleGradientMenuItem;
    }
    public JMenuItem getRobertsGradientMenuItem() {
        return robertsGradientMenuItem;
    }
    public JMenuItem getThresholdingGradientMenuItem() {
        return thresholdingGradientMenuItem;
    }
}
