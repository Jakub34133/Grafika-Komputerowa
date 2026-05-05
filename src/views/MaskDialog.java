package views;

import models.CircleModel;
import models.MaskModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Klasa okna dialogowego do wprowadzania parametrów koła.
 *
 * <p>Umożliwia użytkownikowi podanie współrzędnych środka, promienia oraz wybór koloru koła.
 * Po zatwierdzeniu danych generowany jest obiekt {@link CircleModel}, w którym przechowywane są informację
 * wykorzystywane do rysowania koła na obrazie.</p>
 *
 * @see CircleModel
 */
public class MaskDialog extends JDialog {

    private ArrayList<MaskModel> maskList;
    private String[] options;
    private JComboBox<String> comboBox;
    private JPanel hintPanel;

    // Flaga informująca, czy użytkownik zatwierdził dane, przez przycisk OK.
    private Boolean confirmed = false;

    public MaskModel getMaskModel() {
        return maskList.get(comboBox.getSelectedIndex());
    }

    public MaskDialog(JFrame parent, ArrayList<MaskModel> maskList) {

        super(parent, "Podaj parametry", true);
        setSize(300, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Dodanie wszystkich masek do wyboru
        this.maskList = maskList;
        options = new String[maskList.size()];
        for(int i = 0; i < maskList.size(); i++) {
            options[i] = maskList.get(i).getName();
        }
        comboBox = new JComboBox<>(options);

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
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Konfiguracja panelu podglądu
        hintPanel = new JPanel();
        hintPanel.setLayout(new GridLayout(3, 3));
        hintPanel.setSize(new Dimension(150, 150));

        // Inicjacja Panelu podpowiedzią wyglądu maski
        double[][] currentMask = maskList.get(0).getMask();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                JLabel label = new JLabel(String.valueOf(currentMask[i][j])); // Nadanie cyfry pól
                label.setPreferredSize(new Dimension(50, 50));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.black));
                label.setFont(new Font("sans-serif", Font.BOLD, 20));
                hintPanel.add(label);
            }
        }

        panel.add(new JLabel("Maska:"));
        panel.add(comboBox);
        panel.add(hintPanel);
        comboBox.addActionListener(f -> refreshOption(comboBox.getSelectedIndex()));

        refreshOption(0);
        return panel;
    }

    private void refreshOption(int maskIndex) {
        // Podmiana wartości na wybraną maskę
        double[][] currentMask = maskList.get(maskIndex).getMask();
        int n = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                double number = currentMask[i][j];
                if(Math.abs(number - (int)number) == 0) {
                    ((JLabel)hintPanel.getComponent(n)).setText(String.valueOf((int)number));
                } else {
                    ((JLabel)hintPanel.getComponent(n)).setText(String.valueOf(number));
                }
                n++;
            }
        }
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