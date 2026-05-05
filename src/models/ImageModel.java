package models;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Model reprezentujący obraz, umożliwiający jego modyfikację oraz kopiowanie.
 *
 * <p>
 *     Przechowuje obraz w postaci obiektu {@link BufferedImage} i udostępnia metody do jego modyfikacji.
 *     Tylko ta klasa powinna udostępniać metody do modyfikacji obrazu.
 * </p>
 *
 */
public class ImageModel {

    private BufferedImage image;

    public ImageModel(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Tworzy i zwraca kopię obrazu. Nowy obraz jest kopiowany piksel po pikselu
     *
     * @return Kopia obrazu jako {@link BufferedImage}.
     */
    public BufferedImage getCopyImage() {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return copy;
    }

    /**
     * Rysuje koło na obrazie na podstawie modelu {@link CircleModel}. Koło jest rysowane w miejscu określonym przez współrzędne środka i promień.
     *
     * <p>Iteracyjna implementacja metody:</p>
     * <pre>
     * {@code
     *      int imgWidth = image.getWidth();
     *      int imgHeight = image.getHeight();
     *      int centerX = circle.getCenterX();
     *      int centerY = circle.getCenterY();
     *      int radius = circle.getRadius();
     *      int radiusSquared = radius * radius;
     *      int colorRGB = circle.getColor().getRGB();
     *
     *      for (int y = Math.max(centerY - radius, 0); y < Math.min(centerY + radius, imgHeight); y++) {
     *          for (int x = Math.max(centerX - radius, 0); x < Math.min(centerX + radius, imgWidth); x++) {
     *              int dx = x - centerX;
     *              int dy = y - centerY;
     *              if (dx * dx + dy * dy <= radiusSquared) { // Sprawdzamy, czy punkt leży w kole
     *                  image.setRGB(x, y, colorRGB);
     *              }
     *          }
     *      }
     * }
     * </pre>
     *
     * @param circle Obiekt klasy {@link CircleModel}, określający parametry koła.
     */
    public void drawCircle(CircleModel circle) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
            g2d.dispose();
        }
    }

    /**
     * Rysuje prostokąt na obrazie na podstawie modelu {@link RectangleModel}.
     *
     * @param rectangle Obiekt klasy {@link RectangleModel}, określający parametry prostokąta.
     */
    public void drawRectangle(RectangleModel rectangle) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(rectangle.getColor());
            g2d.fillRect(rectangle.getStartX(), rectangle.getStartY(), rectangle.getWidth(), rectangle.getHeight());
            g2d.dispose();
        }
    }

    public void drawHorizontalLines(int positionY, int lineWidth, int spacing, Color selectedColor) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(selectedColor);
            for (int i = 0; i < image.getWidth(); i += lineWidth + spacing) {
                g2d.fillRect(i, positionY, lineWidth, 1);
            }
            g2d.dispose();
        }
    }

    public void drawBoard(int space, int thickness, Color selectedColor) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(selectedColor);
            for (int i = 0; i <= image.getWidth(); i += space) {
                g2d.fillRect(i, 0, thickness, 500);
            }
            for (int i = 0; i <= image.getHeight(); i += space) {
                g2d.fillRect(0, i, 500, thickness);
            }
            g2d.dispose();
        }
    }

    public void drawGray(int colorComponent) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();

                    if (colorComponent == 0) {
                        g2d.setColor(new Color(r, r, r));
                    } else if (colorComponent == 1) {
                        g2d.setColor(new Color(g, g, g));
                    } else {
                        g2d.setColor(new Color(b, b, b));
                    }
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void drawAvgGray() {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            int avg;
            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    avg = (r + g + b) / 3;

                    g2d.setColor(new Color(avg, avg, avg));
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void drawYUVGray() {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            int avg;
            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    avg = (int) (r * 0.299 + g * 0.587 + b * 0.114);

                    g2d.setColor(new Color(avg, avg, avg));
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void changeSaturation(int k) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            int newR, newG, newB;

            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    newR = r + k;
                    newG = g + k;
                    newB = b + k;
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;

                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;

                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;


                    g2d.setColor(new Color(newR, newG, newB));
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void changeContrast(double k) {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            int newR, newG, newB;

            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    newR = (int) (r * k);
                    newG = (int) (g * k);
                    newB = (int) (b * k);
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;

                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;

                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;


                    g2d.setColor(new Color(newR, newG, newB));
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void changeNegative() {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            int rMax = 0, gMax = 0, bMax = 0;
            int newR, newG, newB;

            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    if (r > rMax) rMax = r;
                    if (g > gMax) gMax = g;
                    if (b > bMax) bMax = b;
                }
            }

            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    newR = rMax - r;
                    newG = gMax - g;
                    newB = bMax - b;
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;

                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;

                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;


                    g2d.setColor(new Color(newR, newG, newB));
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void changeSaturationRange() {
        if (image != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.setColor(Color.red);
            Color color;
            int r, g, b;
            int rMax = 0, gMax = 0, bMax = 0;
            int rMin = 255, gMin = 255, bMin = 255;
            int newR, newG, newB;

            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    if (r > rMax) rMax = r;
                    if (g > gMax) gMax = g;
                    if (b > bMax) bMax = b;

                    if (r < rMin) rMin = r;
                    if (g < gMin) gMin = g;
                    if (b < bMin) bMin = b;
                }
            }

            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    color = new Color(image.getRGB(w, h));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    newR = (255 * (r - rMin)) / (rMax - rMin);
                    newG = (255 * (g - gMin)) / (gMax - gMin);
                    ;
                    newB = (255 * (b - bMin)) / (bMax - bMin);
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;

                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;

                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;


                    g2d.setColor(new Color(newR, newG, newB));
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
        }
    }

    public void mixWithMask(MaskModel maskModel) {
        if (image != null) {
//            Graphics2D g2d = image.createGraphics();
            BufferedImage imageCopy = this.getCopyImage();
            Graphics2D g2d = imageCopy.createGraphics();
            g2d.drawImage(imageCopy, 0, 0, null);

            double[][] mask = maskModel.getMask();

            for (int w = 1; w < imageCopy.getWidth() - 1; w++) {
                for (int h = 1; h < imageCopy.getHeight() - 1; h++) {

                    double newR = 0;
                    double newG = 0;
                    double newB = 0;
                    double wage = 0;
//                    double wage = 0;
//                    int offset = mask.length/2;
//                    for(int i = 0; i < mask.length; i++){
//                        for(int j = 0; j < mask[0].length; j++){
//                            newR += (int)mask[i][j]*new Color(image.getRGB(w+i-offset,h+j-offset)).getRed();
//                            newG += (int)mask[i][j]*new Color(image.getRGB(w+i-offset,h+j-offset)).getGreen();
//                            newB += (int)mask[i][j]*new Color(image.getRGB(w+i-offset,h+j-offset)).getBlue();
//                            wage += mask[i][j];
//                        }
//                    }
                    for (int i = 0; i <= 2; i++) {
                        for (int j = 0; j <= 2; j++) {
                            int x = w + i - 1;
                            int y = h + j - 1;
                            Color colorNeighbour = new Color(image.getRGB(x, y));
                            newR += (int) mask[i][j] * colorNeighbour.getRed();
                            newG += (int) mask[i][j] * colorNeighbour.getGreen();
                            newB += (int) mask[i][j] * colorNeighbour.getBlue();
                            wage += mask[i][j];
                        }
                    }
                    if (wage != 0) {
                        newR /= (int) wage;
                        newG /= (int) wage;
                        newB /= (int) wage;
                    }
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;
                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;
                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;

                    int r = (int) Math.round(newR);
                    int g = (int) Math.round(newG);
                    int b = (int) Math.round(newB);

                    imageCopy.setRGB(w, h, new Color(r, g, b).getRGB());
                }
            }

//            g2d.dispose();
            this.setImage(imageCopy);
        }
    }

    public void statisticFilter(int option) {
        if (image != null) {
//            Graphics2D g2d = image.createGraphics();
            BufferedImage imageCopy = this.getCopyImage();
            Graphics2D g2d = imageCopy.createGraphics();
            g2d.drawImage(imageCopy, 0, 0, null);
            g2d.setColor(Color.red);

            for (int w = 1; w < imageCopy.getWidth() - 1; w++) {
                for (int h = 1; h < imageCopy.getHeight() - 1; h++) {

                    int[] medR = new int[9];
                    int[] medG = new int[9];
                    int[] medB = new int[9];
                    int index = 0;

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            medR[index] = new Color(image.getRGB(w + i, h + j)).getRed();
                            medG[index] = new Color(image.getRGB(w + i, h + j)).getGreen();
                            medB[index] = new Color(image.getRGB(w + i, h + j)).getBlue();
                            index++;
                        }
                    }
                    Arrays.sort(medR);
                    Arrays.sort(medG);
                    Arrays.sort(medB);

                    if (option == 1) {
                        // Filtr medianowy
                        g2d.setColor(new Color(medR[4], medG[4], medB[4]));
                    } else if (option == 2) {
                        // Filtr minimalny
                        g2d.setColor(new Color(medR[0], medG[0], medB[0]));
                    } else {
                        // Filtr maksymalny
                        g2d.setColor(new Color(medR[8], medG[8], medB[8]));
                    }
                    g2d.fillRect(w, h, 1, 1);
                }
            }
            g2d.dispose();
            this.setImage(imageCopy);
        }
    }

    public void medianFilter() {
        statisticFilter(1);
    }

    public void minimumFilter() {
        statisticFilter(2);
    }

    public void maximumFilter() {
        statisticFilter(3);
    }

    public void simpleGradientFilter() {
        if (image != null) {
            BufferedImage imageCopy = this.getCopyImage(); // Kopia obrazu

            for (int w = 0; w < imageCopy.getWidth() - 1; w++) {
                for (int h = 0; h < imageCopy.getHeight() - 1; h++) {

                    // Resetowanie wartości
                    double newR = 0;
                    double newG = 0;
                    double newB = 0;

                    /*
                    Kolory pixeli sąsiednich do A:
                        A B
                        C D
                     */
                    Color colorA = new Color(image.getRGB(w, h)); // bieżący kolor
                    Color colorB = new Color(image.getRGB(w + 1, h)); // kolor z prawej
                    Color colorC = new Color(image.getRGB(w, h + 1)); // kolor z dołu

                    double horizontal = 0; // Gradient B - A
                    double vertical = 0; // Gradient C - A
                    // Red
                    horizontal = colorB.getRed() - colorA.getRed();
                    vertical = colorC.getRed() - colorA.getRed();
                    newR += Math.sqrt((horizontal * horizontal) + (vertical + vertical));
                    // Green
                    horizontal = colorB.getGreen() - colorA.getGreen();
                    vertical = colorC.getGreen() - colorA.getGreen();
                    newG += Math.sqrt((horizontal * horizontal) + (vertical + vertical));
                    // Blue
                    horizontal = colorB.getBlue() - colorA.getBlue();
                    vertical = colorC.getBlue() - colorA.getBlue();
                    newB += Math.sqrt((horizontal * horizontal) + (vertical + vertical));

                    // wykroczenie poza przedział [0,255]
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;
                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;
                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;

                    // zaokrąglenie wartości składowych
                    int r = (int) Math.round(newR);
                    int g = (int) Math.round(newG);
                    int b = (int) Math.round(newB);

                    imageCopy.setRGB(w, h, new Color(r, g, b).getRGB());
                }
            }
            this.setImage(imageCopy);
        }
    }

    public void robertsGradientFilter() {
        if (image != null) {
            BufferedImage imageCopy = this.getCopyImage(); // Kopia obrazu

            for (int w = 0; w < imageCopy.getWidth() - 1; w++) {
                for (int h = 0; h < imageCopy.getHeight() - 1; h++) {

                    // Resetowanie wartości
                    double newR = 0;
                    double newG = 0;
                    double newB = 0;

                    /*
                    Kolory pixeli sąsiednich do A:
                        A B
                        C D
                     */
                    Color colorA = new Color(image.getRGB(w, h)); // bieżący kolor
                    Color colorB = new Color(image.getRGB(w + 1, h)); // kolor z prawej
                    Color colorC = new Color(image.getRGB(w, h + 1)); // kolor z dołu
                    Color colorD = new Color(image.getRGB(w + 1, h + 1)); // kolor z dołu i prawej

                    double g1 = 0; // Gradient A - D
                    double g2 = 0; // Gradient B - C
                    // Red
                    g1 = colorA.getRed() - colorD.getRed();
                    g2 = colorB.getRed() - colorC.getRed();
                    newR += Math.sqrt((g1 * g1) + (g2 + g2));
                    // Green
                    g1 = colorA.getGreen() - colorD.getGreen();
                    g2 = colorB.getGreen() - colorC.getGreen();
                    newG += Math.sqrt((g1 * g1) + (g2 + g2));
                    // Blue
                    g1 = colorA.getBlue() - colorD.getBlue();
                    g2 = colorB.getBlue() - colorC.getBlue();
                    newB += Math.sqrt((g1 * g1) + (g2 + g2));

                    // wykroczenie poza przedział [0,255]
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;
                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;
                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;

                    // zaokrąglenie wartości składowych
                    int r = (int) Math.round(newR);
                    int g = (int) Math.round(newG);
                    int b = (int) Math.round(newB);

                    imageCopy.setRGB(w, h, new Color(r, g, b).getRGB());
                }
            }
            this.setImage(imageCopy);
        }
    }

    public void thresholdingGradientFilter(int threshold, int option) {
        if (image != null) {
            BufferedImage imageCopy = this.getCopyImage(); // Kopia obrazu

            for (int w = 0; w < imageCopy.getWidth() - 1; w++) {
                for (int h = 0; h < imageCopy.getHeight() - 1; h++) {
                    Color oldColor = new Color(image.getRGB(w, h));
                    int oldR = oldColor.getRed();
                    int oldG = oldColor.getGreen();
                    int oldB = oldColor.getBlue();

                    // Resetowanie wartości
                    double newR = 0;
                    double newG = 0;
                    double newB = 0;

                    /*
                    Kolory pixeli sąsiednich do A:
                        A B
                        C D
                     */
                    Color colorA = new Color(image.getRGB(w, h)); // bieżący kolor
                    Color colorB = new Color(image.getRGB(w + 1, h)); // kolor z prawej
                    Color colorC = new Color(image.getRGB(w, h + 1)); // kolor z dołu

                    double horizontal = 0; // Gradient B - A
                    double vertical = 0; // Gradient C - A
                    // Red
                    horizontal = colorB.getRed() - colorA.getRed();
                    vertical = colorC.getRed() - colorA.getRed();
                    newR += Math.sqrt((horizontal * horizontal) + (vertical + vertical));
                    // Green
                    horizontal = colorB.getGreen() - colorA.getGreen();
                    vertical = colorC.getGreen() - colorA.getGreen();
                    newG += Math.sqrt((horizontal * horizontal) + (vertical + vertical));
                    // Blue
                    horizontal = colorB.getBlue() - colorA.getBlue();
                    vertical = colorC.getBlue() - colorA.getBlue();
                    newB += Math.sqrt((horizontal * horizontal) + (vertical + vertical));

                    // wykroczenie poza przedział [0,255]
                    if (newR < 0) newR = 0;
                    if (newR > 255) newR = 255;
                    if (newG < 0) newG = 0;
                    if (newG > 255) newG = 255;
                    if (newB < 0) newB = 0;
                    if (newB > 255) newB = 255;

                    // zaokrąglenie wartości składowych
                    int r = (int) Math.round(newR);
                    int g = (int) Math.round(newG);
                    int b = (int) Math.round(newB);

                    int gradientResult = (r + g + b)/3;

                    if(option == 0) {
                        // Tło białe, krawędzie nieprzetworzone
                        if(gradientResult >= threshold) {
                            // krawędzie obrazu
                            imageCopy.setRGB(w, h, new Color(oldR, oldG, oldB).getRGB());
                        } else {
                            // tło obrazu
                            imageCopy.setRGB(w, h, new Color(255, 255, 255).getRGB());
                        }
                    } else if (option == 1) {
                        // Tło nieprzetworzone, krawędzie czarne
                        if(gradientResult >= threshold) {
                            // krawędzie obrazu
                            imageCopy.setRGB(w, h, new Color(0, 0, 0).getRGB());
                        } else {
                            // tło obrazu
                            imageCopy.setRGB(w, h, new Color(oldR, oldG, oldB).getRGB());
                        }
                    } else if (option == 2) {
                        // Tło białe, krawędzie czarne
                        if(gradientResult >= threshold) {
                            // krawędzie obrazu
                            imageCopy.setRGB(w, h, new Color(0, 0, 0).getRGB());
                        } else {
                            // tło obrazu
                            imageCopy.setRGB(w, h, new Color(255, 255, 255).getRGB());
                        }
                    } else if (option == 3) {
                        // Tło zielone, krawędzie czerwone
                        if(gradientResult >= threshold) {
                            // krawędzie obrazu
                            imageCopy.setRGB(w, h, new Color(0, 255, 0).getRGB());
                        } else {
                            // tło obrazu
                            imageCopy.setRGB(w, h, new Color(255, 0, 0).getRGB());
                        }
                    }

                }
            }
            this.setImage(imageCopy);
        }
    }
}
