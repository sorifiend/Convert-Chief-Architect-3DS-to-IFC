/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import no.myke.parser.ModelObject;
import no.myke.parser.Vector;
/**
 *
 * @author j.simpson
 */
public class genericObject
{
    private float minX = 0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
    private final Vector[] vectors;
    private ArrayList<Face> meshList = new ArrayList<>();
    String name = "noName";
    
    genericObject(ModelObject model)
    {
	this.name = model.getName();
	this.vectors = model.vectors;
	
	//Create mesh from vectors
	//3DS uses a triangular mesh, so every one is made up of 3 vectors
	for (int i = 0; i < model.polygons.length; i = i + 3)
	{
	    //get the 3 vectors
	    Vector point1 = model.vectors[model.polygons[i]];
	    Vector point2 = model.vectors[model.polygons[i + 1]];
	    Vector point3 = model.vectors[model.polygons[i + 2]];
	    //Get material name
	    String material = model.materialType.get((short)(i/3));

	    meshList.add(new Face(point1, point2, point3, material));
	}
	setExtents();
    }
    
    private void setExtents()
    {
	//Find extents
	for (int m = 0; m < meshList.size(); m++)
	{   
	    
	    Face value = meshList.get(m);
	    if (m == 0)
	    {
		minX = value.Point1().X();
		maxX = value.Point1().X();
		minY = value.Point1().Y();
		maxY = value.Point1().Y();
		minZ = value.Point1().Z();
		maxZ = value.Point1().Z();
	    }
	    else
	    {
		if (value.Point1().X() < minX) minX = value.Point1().X();
		else if (value.Point1().X() > maxX) maxX = value.Point1().X();
		if (value.Point2().X() < minX) minX = value.Point2().X();
		else if (value.Point2().X() > maxX) maxX = value.Point2().X();
		if (value.Point3().X() < minX) minX = value.Point3().X();
		else if (value.Point3().X() > maxX) maxX = value.Point3().X();

		if (value.Point1().Y() < minY) minY = value.Point1().Y();
		else if (value.Point1().Y() > maxY) maxY = value.Point1().Y();
		if (value.Point2().Y() < minY) minY = value.Point2().Y();
		else if (value.Point2().Y() > maxY) maxY = value.Point2().Y();
		if (value.Point3().Y() < minY) minY = value.Point3().Y();
		else if (value.Point3().Y() > maxY) maxY = value.Point3().Y();		

		if (value.Point1().Z() < minZ) minZ = value.Point1().Z();
		else if (value.Point1().Z() > maxZ) maxZ = value.Point1().Z();
		if (value.Point2().Z() < minZ) minZ = value.Point2().Z();
		else if (value.Point2().Z() > maxZ) maxZ = value.Point2().Z();
		if (value.Point3().Z() < minZ) minZ = value.Point3().Z();
		else if (value.Point3().Z() > maxZ) maxZ = value.Point3().Z();
	    }
	}
    }

    public float getMinX(){return minX;}
    public float getMaxX(){return maxX;}
    public float getMinY(){return minY;}
    public float getMaxY(){return maxY;}
    public float getMinZ(){return minZ;}
    public float getMaxZ(){return maxZ;}
    
    public Vector[] getVectorList(){return vectors;}
    public ArrayList<Face> getFaceList(){return meshList;}
    public void setMeshList(ArrayList<Face> meshList)
    {
	this.meshList = meshList;
	//Update extents
	setExtents();
    }
}
