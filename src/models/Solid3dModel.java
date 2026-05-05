package models;

import java.util.ArrayList;

public class Solid3dModel {
    private ArrayList<Point3dModel> points3d;
    private ArrayList<Wall3dModel> walls3d;

    public Solid3dModel(ArrayList<Point3dModel> points3d, ArrayList<Wall3dModel> walls3d) {
        this.points3d = points3d;
        this.walls3d = walls3d;
    }

    public ArrayList<Point3dModel> getPoints3d() {
        return points3d;
    }

    public ArrayList<Wall3dModel> getWalls3d() {
        return walls3d;
    }
}
