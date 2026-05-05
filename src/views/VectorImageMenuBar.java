package views;

import javax.swing.*;

/**
 * Pasek menu aplikacji, zawierający opcje zarządzania plikami, edycji oraz operacji na panelach.
 * Klasa rozszerza {@link JMenuBar} i definiuje strukturę menu dla aplikacji.
 */
public class VectorImageMenuBar extends JMenuBar {

    private final JMenu imageMenu;
    private final JMenu drawMenu;

    private final JMenuItem rasterImageMenuItem;
    private final JMenuItem vectorImageMenuItem;
    private final JMenuItem vector3dImageMenuItem;

    private final JRadioButtonMenuItem straightLineMenuItem;
    private final JRadioButtonMenuItem curvedLineMenuItem;
    private final JMenuItem clearPointsMenuItem;

    public VectorImageMenuBar() {
        // Tworzenie głównych menu
        imageMenu = new JMenu("Obraz");
        drawMenu = new JMenu("Rysuj");

        // Menu obraz
        rasterImageMenuItem = new JMenuItem("Rastrowy");
        vectorImageMenuItem = new JMenuItem("Wektorowy 2D");
        vector3dImageMenuItem = new JMenuItem("Wektorowy 3D");

        // Menu rysuj
        straightLineMenuItem = new JRadioButtonMenuItem("Łamana");
        curvedLineMenuItem = new JRadioButtonMenuItem("Krzywa");
        clearPointsMenuItem = new JMenuItem("Wyczyść");

        // Dodanie elementów do menu Obraz
        imageMenu.add(rasterImageMenuItem);
        imageMenu.add(vectorImageMenuItem);
        imageMenu.add(vector3dImageMenuItem);

        // Dodanie elementów do menu Rysuj
        drawMenu.add(straightLineMenuItem);
        drawMenu.add(curvedLineMenuItem);
        drawMenu.add(new JSeparator());
        drawMenu.add(clearPointsMenuItem);

        // Dodawanie wszystkich menu do paska menu
        add(imageMenu);
        add(drawMenu);
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

    public JMenuItem getStraightLineMenuItem() {
        return straightLineMenuItem;
    }
    public JMenuItem getCurvedLineMenuItem() {
        return curvedLineMenuItem;
    }

    public JMenuItem getClearPointsMenuItem() {
        return clearPointsMenuItem;
    }
}
