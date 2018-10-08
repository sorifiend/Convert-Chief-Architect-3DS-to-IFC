package no.myke.parser;

import java.util.ArrayList;
/**
 *
 */
public class ModelObject {
    private String name;
    public Vector[] vectors;
    public short[] polygons;
    public float[] textureCoordinates;

    public ModelObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
