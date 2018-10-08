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

	    meshList.add(new Face(point1, point2, point3));
	}
	setExtents();
    }
    
    //Remove all vertices that are not in the list
    //vectorType = X, Y, Z
    public void pruneVertices(String vectorType, ArrayList<Float> verticesToKeep)
    {
	//Form new list of vectors containing the verticesToKeep
	ArrayList<Face> newMeshList = new ArrayList<>();
	for (Face face : meshList)
	{
	    Boolean withinBoundsOfWall = true;
	    for (Vector vector : face.getVectors())
	    {
		switch (vectorType)
		{
		    case Vector.TYPE_X:
			if (verticesToKeep.contains(vector.X()))
			{
			    withinBoundsOfWall = true;
			}
			break;
		    case Vector.TYPE_Y:
			if (verticesToKeep.contains(vector.Y()))
			{
			    withinBoundsOfWall = true;
			}
			break;
		    case Vector.TYPE_Z:
			if (verticesToKeep.contains(vector.Z()))
			{
			    withinBoundsOfWall = true;
			}
			break;
		    default:
			break;
		}
		if (withinBoundsOfWall == false) break;
	    }
	    if (withinBoundsOfWall) newMeshList.add(face);
	}
	//Set face list to new list that contains the verticesToKeep
	meshList = newMeshList;
	//Update extents
	setExtents();
    }
    
    private void setExtents()
    {
//	//Find extents
//	for (int v = 0; v < meshList.size(); v++)
//	{   
//	    Face value = meshList.get(v);
//	    if (v == 0)
//	    {
//		minX = value.X();
//		maxX = value.X();
//		minY = value.Y();
//		maxY = value.Y();
//		minZ = value.Z();
//		maxZ = value.Z();
//	    }
//	    else
//	    {
//		if (value.X() < minX) minX = value.X();
//		else if (value.X() > maxX) maxX = value.X();
//
//		if (value.Y() < minY) minY = value.Y();
//		else if (value.Y() > maxY) maxY = value.Y();
//
//		if (value.Z() < minZ) minZ = value.Z();
//		else if (value.Z() > maxZ) maxZ = value.Z();
//	    }
//	}
    }
    

    public float getMinX(){return minX;}
    public float getMaxX(){return maxX;}
    public float getMinY(){return minY;}
    public float getMaxY(){return maxY;}
    public float getMinZ(){return minZ;}
    public float getMaxZ(){return maxZ;}
    
    public Vector[] getVectorList(){return vectors;}
    public ArrayList<Face> getFaceList(){return meshList;}
    //public void setMeshList(ArrayList<Face> meshList){this.meshList = meshList;}
}
