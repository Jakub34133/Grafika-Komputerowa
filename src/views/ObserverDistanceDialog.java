package views;

import models.CircleModel;

import javax.swing.*;
import java.awt.*;

public class ObserverDistanceDialog extends JDialog {

    private final JTextField distanceField = new JTextField("1000");

    // Flaga informująca, czy użytkownik zatwierdził dane, przez przycisk OK.
    private Boolean confirmed = false;

    public Double getDistance() {
        if(confirmed) {
            return Double.parseDouble(distanceField.getText());
        }
        return null;
    }

    public ObserverDistanceDialog(JFrame parent) {
        super(parent, "Podaj parametry", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Tworzy główny panel zawierający pola do wprowadzania danych oraz przycisk wyboru koloru.
     *
     * @return Panel z polami tekstowymi i przyciskiem do wyboru koloru.
     */
    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Odległość obserwatora od rzutni: "));
        panel.add(distanceField);

        return panel;
    }

    /**
     * Tworzy panel z przyciskami akcji: "OK" i "Anuluj".
     *
     * @return Panel z przyciskami potwierdzenia i anulowania.
     */
    private JPanel getActionPanel() {
        // Przycisk OK powinien sprawdzać poprawność danych i zamykać okno, jeśli są poprawne.
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Anuluj");

        // Obsługa przycisku OK. Sprawdzenie poprawności danych i zamknięcie okna. Jeśli dane są niepoprawne, wyświetlany jest komunikat z błędem.
        okButton.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Obsługa przycisku Anuluj przez zamknięcie okna dialogowego.
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    /**
     * Zwraca obiekt {@link CircleModel} z wprowadzonymi danymi. Obiekt jest zwracany tylko, gdy użytkownik zatwierdził okno przez przycisk OK.
     *
     * @return Obiekt CircleModel z danymi koła lub {@code null}, jeśli użytkownik anulował formularz.
     */

    /**
     * Sprawdza, czy wszystkie pola zawierają poprawne wartości numeryczne.
     *
     * @return {@code true}, jeśli dane są poprawne; {@code false} w przeciwnym razie.
     */
    private Boolean validateFields() {
        return parseField(distanceField) != null;
    }

    /**
     * Konwertuje dane pobrane z pola tekstowego {@link JTextField} na liczbę całkowitą.
     *
     * @param field Pole tekstowe do sparsowania.
     * @return Liczba całkowita lub {@code null}, jeśli nie udało się sparsować wartości.
     */
    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}