/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import no.myke.parser.Vector;
/**
 *
 * @author j.simpson
 */
public class Utils
{
//    public static void pruneMaterials(genericObject object, ArrayList<String> materialsToKeep)
//    {
//	//Form new list of only the materials to keep
//	ArrayList<Face> newMeshList = new ArrayList<>();
//	for (Face face : object.getFaceList())
//	{
//	    if(materialsToKeep.contains(face.Material()))
//	    {
//		newMeshList.add(face);
//	    }
//	}
//	//Set face list to new list that contains core faces only 
//	object.setMeshList(newMeshList);
//    }
    
    //Find the inner dimensions of an opening
    public static void getInnerOpeningSize(genericObject object)
    {
//	ArrayList<Face> newMeshList = new ArrayList<>();
//	for (Face face : object.getFaceList())
//	{
//	    
//	}
	throw new UnsupportedOperationException();
    }
    
    
//    //Remove all vertices that do not match one of the items in the 'verticesToKeep' list
//    //vectorType = X, Y, Z
//    public static void pruneVertices(genericObject object, String vectorType, ArrayList<Float> verticesToKeep)
//    {
//	//Form new list of vectors containing the verticesToKeep
//	ArrayList<Face> newMeshList = new ArrayList<>();
//	for (Face face : object.getFaceList())
//	{
//	    Boolean withinBoundsOfWall = true;
//	    for (Vector vector : face.getVectors())
//	    {
//		switch (vectorType)
//		{
//		    case "X":
//			if (verticesToKeep.contains(vector.X()) == false)
//			{
//			    withinBoundsOfWall = false;
//			}
//			break;
//		    case "Y":
//			if (verticesToKeep.contains(vector.Y()) == false)
//			{
//			    withinBoundsOfWall = false;
//			}
//			break;
//		    case "Z":
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
//	object.setMeshList(newMeshList);
//	//Reset list to reuse
//	newMeshList = new ArrayList<>();
//	
//	//Prune isolated faces. Only keep faces that are connected to 2 other unique faces.
//	for (int m = 0; m < object.getFaceList().size(); m++)
//	{
//	    Vector point1 = object.getFaceList().get(m).Point1();
//	    Vector point2 = object.getFaceList().get(m).Point2();
//	    Vector point3 = object.getFaceList().get(m).Point3();
//	    int connectionCountPoint1 = 0;
//	    int connectionCountPoint2 = 0;
//	    int connectionCountPoint3 = 0;
//	    for (int i = 0; i < object.getFaceList().size(); i++)
//	    {
//		//dont compare with self
//		if (i != m)
//		{
//		    Vector[] vectorsToCompare = object.getFaceList().get(i).getVectors();
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
//		newMeshList.add(object.getFaceList().get(m));
//	    }
//	}
//	
//	//Set face list to new list that contains core faces only 
//	object.setMeshList(newMeshList);
//    }
    
//    public static double distance2D(float x1, float y1, float x2, float y2)
//    {
//	return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
//    }
    public static double distance2D(Vector firstPoint, Vector sectondPoint)
    {
	return Math.sqrt((sectondPoint.Y() - firstPoint.Y()) * (sectondPoint.Y() - firstPoint.Y()) + (sectondPoint.X() - firstPoint.X()) * (sectondPoint.X() - firstPoint.X()));
    }
    
    public static boolean isVectorBetween2D(Vector firstPoint, Vector sectondPoint, Vector pointToCheck)
    {
	// Return true if point is on the line between.
	double distanceBetweenPoints = distance2D(firstPoint, sectondPoint);
	double distanceToPoint = distance2D(firstPoint, pointToCheck) + distance2D(sectondPoint, pointToCheck);
	
	return Convert3DSToIFC.CloseEnough(distanceBetweenPoints, distanceToPoint);
    }
}
