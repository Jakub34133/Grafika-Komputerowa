package views;

import models.CircleModel;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa okna dialogowego do wprowadzania parametrów koła.
 *
 * <p>Umożliwia użytkownikowi podanie współrzędnych środka, promienia oraz wybór koloru koła.
 * Po zatwierdzeniu danych generowany jest obiekt {@link CircleModel}, w którym przechowywane są informację
 * wykorzystywane do rysowania koła na obrazie.</p>
 *
 * @see CircleModel
 */
public class GrayDialog extends JDialog {

    private final String[] options = {"Czerwony","Zielony", "Niebieski"};
    private final JComboBox<String> comboBox = new JComboBox<>(options);

    // Flaga informująca, czy użytkownik zatwierdził dane, przez przycisk OK.
    private Boolean confirmed = false;

    public GrayDialog(JFrame parent) {
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

        panel.add(new JLabel("Odcień:"));
        panel.add(comboBox);

        return panel;
    }

    public int getOption() {
        return comboBox.getSelectedIndex();
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
            confirmed = true;
            dispose();
        });

        // Obsługa przycisku Anuluj przez zamknięcie okna dialogowego.
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
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