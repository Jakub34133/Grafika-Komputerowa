package models;

import java.awt.*;
import java.util.ArrayList;

public class Wall3dModel {
    private int pointAmount;
    private int[] points3d;
    private Color wallColor;

    public Wall3dModel(int pointAmount, int[] points3d, Color wallColor) {
        this.pointAmount = pointAmount;
        this.points3d = points3d;
        this.wallColor = wallColor;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Color getWallColor() {
        return wallColor;
    }

    public void setWallColor(Color wallColor) {
        this.wallColor = wallColor;
    }

    public int getPointAt(int index) {
        return this.points3d[index];
    }
}
