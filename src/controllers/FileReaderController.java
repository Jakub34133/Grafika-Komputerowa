package controllers;

import models.MaskModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Kontroler odczytujący z plików tekstowych
*/
public class FileReaderController {
    private final String MASKI_TXT = "src/maski.txt";

    public FileReaderController() {}

    /*
        Metoda odczytująca maski wszystkie 3x3 z pliku
    */
    public ArrayList<MaskModel> maskReader() {
        try {
            File file = new File(MASKI_TXT);
            Scanner scanner = new Scanner(file);

            ArrayList<MaskModel> maskList = new ArrayList<>();

            // Pobranie wszystkich masek 3x3 z pliku z nazwą
            while(scanner.hasNextLine()) {
                // Pomiń puste linie przed nazwą
                String name = "";
                while (scanner.hasNextLine()) {
                    name = scanner.nextLine().trim(); // Próba pobrania nazwy
                    if (!name.isEmpty()) break;
                }

                if (name.isEmpty()) break; // Koniec pliku

                double[][] maskResult = new double[3][3];

//                name = scanner.nextLine(); // Pobranie nazwy

                for(int i = 0; i < 3; i++) { // Pobranie wartości masek 3x3
                    for(int j = 0; j < 3; j++) {
                        maskResult[i][j] = scanner.nextDouble();
                    }
                }
                if(scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                maskList.add(new MaskModel(name, maskResult));
            }

            return maskList;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
