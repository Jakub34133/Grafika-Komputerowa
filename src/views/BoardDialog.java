package views;

import models.RectangleModel;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa okna dialogowego do wprowadzania parametrów prostokąta.
 *
 * <p>Umożliwia użytkownikowi podanie współrzędnych, wielkości oraz wybór koloru prostokąta.
 * Po zatwierdzeniu danych generowany jest obiekt {@link RectangleModel}, w którym przechowywane są informację
 * wykorzystywane do rysowania prostokąta na obrazie.</p>
 *
 * @see RectangleModel
 */
public class BoardDialog extends JDialog {

    private final JTextField spacingField = new JTextField("20");
    private final JTextField thicknessField = new JTextField("2");
    private Color selectedColor = Color.RED;
    private boolean confirmed = false;

    public BoardDialog(JFrame parent) {
        // Powinien on także wywołać metody do tworzenia głównego panelu i panelu akcji.
        super(parent, "Podaj parametry", true);
        setSize(300, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = getMainPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = getActionPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Rozstaw:"));
        panel.add(spacingField);
        panel.add(new JLabel("Grubość:"));
        panel.add(thicknessField);

        // Wybór koloru
        JButton colorButton = new JButton("Wybierz kolor");
        colorButton.setBackground(selectedColor);

        /**
         *  Ustawnie nasłuchu na przycisk Wybierz kolor.
         *  Po kliknięciu otwiera okno dialogowe wyboru koloru ({@link JColorChooser}).
         */
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Wybierz kolor", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                colorButton.setBackground(selectedColor);
            }
        });

        panel.add(new JLabel("Kolor:"));
        panel.add(colorButton);

        return panel;
    }

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

    public Integer getSpacing() {
        if (confirmed) {
            return parseField(spacingField);
        }
        return null;
    }
    public Integer getThickness() {
        if (confirmed) {
            return parseField(thicknessField);
        }
        return null;
    }
    public Color getSelectedColor() {
        if (confirmed) {
            return selectedColor;
        }
        return null;
    }

    private Boolean validateFields() {
        return parseField(spacingField) != null &&
                parseField(thicknessField) != null;
    }

    private Integer parseField(JTextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
