package models;

/*
    Model przechowujący informacje o maskach splotowych
 */
public class MaskModel {
    String name;
    int width, height;
    double[][] mask;

    public MaskModel(String name, double[][] mask) {
        this.name = name;
        this.mask = mask;
        this.width = mask[0].length;
        this.height = mask.length;

    }

    public String getName() {
        return name;
    }

    public double[][] getMask() {
        return this.mask;
    }
}
