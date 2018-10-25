package no.myke.parser;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 */
public class ModelObject {
    private String name;
    public Vector[] vectors;
    public short[] polygons;
    public HashMap materialType = new HashMap();
    public float[] textureCoordinates;

    public ModelObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
