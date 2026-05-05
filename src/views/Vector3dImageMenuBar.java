package views;

import javax.swing.*;

/**
 * Pasek menu aplikacji, zawierający opcje zarządzania plikami, edycji oraz operacji na panelach.
 * Klasa rozszerza {@link JMenuBar} i definiuje strukturę menu dla aplikacji.
 */
public class Vector3dImageMenuBar extends JMenuBar {

    private final JMenu imageMenu;
    private final JMenu drawMenu;
    private final JMenu viewMenu;
    private final JMenu observerMenu;

    private final JMenuItem rasterImageMenuItem;
    private final JMenuItem vectorImageMenuItem;
    private final JMenuItem vector3dImageMenuItem;

    private final JMenuItem cubeMenuItem;
    private final JMenuItem pyramidMenuItem;
    private final JMenuItem clearMenuItem;

    private final JRadioButtonMenuItem pointRadioMenuItem;
    private final JRadioButtonMenuItem wireRadioMenuItem;
    private final JRadioButtonMenuItem solidRadioMenuItem;

    private final JMenuItem observerDistanceMenuItem;

    // Rysuj: Sześcian... / Wyczyść
    // Widok: Druciany / Brzegowy

    public Vector3dImageMenuBar() {
        // Tworzenie głównych menu
        imageMenu = new JMenu("Obraz");
        drawMenu = new JMenu("Rysuj");
        viewMenu = new JMenu("Widok");
        observerMenu = new JMenu("Obserwator");

        // Menu obraz
        rasterImageMenuItem = new JMenuItem("Rastrowy");
        vectorImageMenuItem = new JMenuItem("Wektorowy 2D");
        vector3dImageMenuItem = new JMenuItem("Wektorowy 3D");

        // Menu rysuj
        cubeMenuItem = new JMenuItem("Sześcian");
        pyramidMenuItem = new JMenuItem("Ostrosłup");
        clearMenuItem = new JMenuItem("Wyczyść");

        // Menu Widok
        pointRadioMenuItem = new JRadioButtonMenuItem("Wierzchołki");
        wireRadioMenuItem = new JRadioButtonMenuItem("Krawędzie");
        solidRadioMenuItem = new JRadioButtonMenuItem("Ściany");

        wireRadioMenuItem.setSelected(true); // Domyślnie włączone krawędzie

        // Menu obserwator
        observerDistanceMenuItem = new JMenuItem("Odległość");

        // Dodanie elementów do menu Obraz
        imageMenu.add(rasterImageMenuItem);
        imageMenu.add(vectorImageMenuItem);
        imageMenu.add(vector3dImageMenuItem);

        // Dodanie elementów do menu Rysuj
        drawMenu.add(cubeMenuItem);
        drawMenu.add(pyramidMenuItem);
        drawMenu.add(new JSeparator());
        drawMenu.add(clearMenuItem);

        // Dodanie elementów do menu Widok
        viewMenu.add(pointRadioMenuItem);
        viewMenu.add(wireRadioMenuItem);
        viewMenu.add(solidRadioMenuItem);

        // Dodanie elementów do menu Obserwator
        observerMenu.add(observerDistanceMenuItem);

        // Dodawanie wszystkich menu do paska menu
        add(imageMenu);
        add(drawMenu);
        add(viewMenu);
        add(observerMenu);
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

    public JMenuItem getCubeMenuItem() {
        return cubeMenuItem;
    }
    public JMenuItem getPyramidMenuItem() {
        return pyramidMenuItem;
    }
    public JMenuItem getClearMenuItem() {
        return clearMenuItem;
    }

    public JRadioButtonMenuItem getPointRadioMenuItem() {
        return pointRadioMenuItem;
    }
    public JRadioButtonMenuItem getWireRadioMenuItem() {
        return wireRadioMenuItem;
    }
    public JRadioButtonMenuItem getSolidRadioMenuItem() {
        return solidRadioMenuItem;
    }

    public JMenuItem getObserverDistanceMenuItem() {
        return observerDistanceMenuItem;
    }
}
