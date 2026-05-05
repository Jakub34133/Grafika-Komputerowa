package views;

import models.CircleModel;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa okna dialogowego do wprowadzania parametru progowania gradientu
 */
public class ThresholdingGradientDialog extends JDialog {

    // Pole dla tekstowe progu (domyślnie 10).
    private final JTextField thresholdField = new JTextField("30");

    // Pole wyboru wersji zadania
    private final String[] options =
            {
                    "Tło białe, krawędzie bez zmian",
                    "Tło bez zmian, Krawędzie czarne",
                    "Tło białe, krawędzie czarne",
                    "Tło zielone, krawędzie czerwone"
            };
    private final JComboBox<String> comboBox = new JComboBox<>(options);

    // Flaga informująca, czy użytkownik zatwierdził dane, przez przycisk OK.
    private Boolean confirmed = false;

    public Integer getThreshold() {
        if(confirmed) {
            return Integer.parseInt(thresholdField.getText());
        }
        return null;
    }

    public int getOption() {
        return comboBox.getSelectedIndex();
    }

    public ThresholdingGradientDialog(JFrame parent) {
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
     */
    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Próg: "));
        panel.add(thresholdField);
        panel.add(new JLabel("Kolorystyka: "));
        panel.add(comboBox);

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
     * Sprawdza, czy wszystkie pola zawierają poprawne wartości numeryczne.
     *
     * @return {@code true}, jeśli dane są poprawne; {@code false} w przeciwnym razie.
     */
    private Boolean validateFields() {
        return parseField(thresholdField) != null;
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