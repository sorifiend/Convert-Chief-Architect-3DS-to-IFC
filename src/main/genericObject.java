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
    
//    //Remove all vertices that do not match one of the items in the 'verticesToKeep' list
//    //vectorType = X, Y, Z
//    public void pruneVertices(String vectorType, ArrayList<Float> verticesToKeep)
//    {
//	//Form new list of vectors containing the verticesToKeep
//	ArrayList<Face> newMeshList = new ArrayList<>();
//	for (Face face : meshList)
//	{
//	    Boolean withinBoundsOfWall = true;
//	    for (Vector vector : face.getVectors())
//	    {
//		switch (vectorType)
//		{
//		    case Vector.TYPE_X:
//			if (verticesToKeep.contains(vector.X()) == false)
//			{
//			    withinBoundsOfWall = false;
//			}
//			break;
//		    case Vector.TYPE_Y:
//			if (verticesToKeep.contains(vector.Y()) == false)
//			{
//			    withinBoundsOfWall = false;
//			}
//			break;
//		    case Vector.TYPE_Z:
//			if (verticesToKeep.contains(vector.Z()) == false)
//			{
//			    withinBoundsOfWall = false;
//			}
//			break;
//		    default:
//			break;
//		}
//		if (withinBoundsOfWall == false) break;
//	    }
//	    if (withinBoundsOfWall) newMeshList.add(face);
//	}
//	//Set face list to new list that contains the verticesToKeep
//	meshList = newMeshList;
//	//Reset list to reuse
//	newMeshList = new ArrayList<>();
//	
//	System.out.println(this.name+": "+meshList.size());
//	//Prune isolated faces. Only keep faces that are connected to 2 other unique faces.
//	for (int m = 0; m < meshList.size(); m++)
//	{
//	    Vector point1 = meshList.get(m).Point1();
//	    Vector point2 = meshList.get(m).Point2();
//	    Vector point3 = meshList.get(m).Point3();
//	    int connectionCountPoint1 = 0;
//	    int connectionCountPoint2 = 0;
//	    int connectionCountPoint3 = 0;
//	    for (int i = 0; i < meshList.size(); i++)
//	    {
//		//dont compare with self
//		if (i != m)
//		{
//		    Vector[] vectorsToCompare = meshList.get(i).getVectors();
//		    for (int j = 0; j < vectorsToCompare.length; j++)
//		    {
//			//Compare ID inner points with ID of point1/2/3
//			//Incriment count if match found. 2 or more matches means it is a valid connected point
//			String ID = vectorsToCompare[j].getID();
//			if (ID.equals(point1.getID()))
//			{
//			    connectionCountPoint1++;
//			}
//			if (ID.equals(point2.getID()))
//			{
//			    connectionCountPoint2++;
//			}
//			if (ID.equals(point3.getID()))
//			{
//			    connectionCountPoint3++;
//			}
//		    }
//		}
//	    }
//	    if (connectionCountPoint1 >= 2 &&
//		connectionCountPoint2 >= 2 &&
//		connectionCountPoint3 >= 2)
//	    {
//		newMeshList.add(meshList.get(m));
//	    }
//	}
//	
//	//Set face list to new list that contains core faces only 
//	meshList = newMeshList;
//	
//	//Update extents
//	setExtents();
//    }
    
    public void pruneMaterials(ArrayList<String> materialsToKeep)
    {
	//Form new list of only the materials to keep
	ArrayList<Face> newMeshList = new ArrayList<>();
	for (Face face : meshList)
	{
	    if(materialsToKeep.contains(face.Material()))
	    {
		newMeshList.add(face);
	    }
	}
	
	//Set face list to new list that contains core faces only 
	meshList = newMeshList;
	
	//Update extents
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
