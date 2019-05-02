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
    
    //Check if values are equal or within tolerance
    public static boolean CloseEnough(double value1, double value2)
    {
	return Math.abs(value1 - value2) <= Convert3DSToIFC.tolerance;
    }
    
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
	
	return CloseEnough(distanceBetweenPoints, distanceToPoint);
    }
    
    //Create list of lines from a list of mesh faces
    public static ArrayList<Line> convertMeshToLines(ArrayList<Face> meshList)
    {

	ArrayList<Line> lineListAll = new ArrayList<>();
	for (int f = 0; f < meshList.size(); f++)
	{
	    Face face = meshList.get(f);
	    Vector vector1 = new Vector(face.Point1().X(), face.Point1().Y(), face.Point1().Z());
	    Vector vector2 = new Vector(face.Point2().X(), face.Point2().Y(), face.Point2().Z());
	    Vector vector3 = new Vector(face.Point3().X(), face.Point3().Y(), face.Point3().Z());

	    Line line1 = new Line(vector1, vector2);
	    Line line2 = new Line(vector2, vector3);
	    Line line3 = new Line(vector3, vector1);
	    //if (noDuplicateLine(line1, lineList))
	    lineListAll.add(line1);
	    //if (noDuplicateLine(line2, lineList))
	    lineListAll.add(line2);
	    //if (noDuplicateLine(line3, lineList))
	    lineListAll.add(line3);
	}
//	System.out.println("LINES FOUND ALL: " + lineListAll.size());
//	//Print results:
//	for (int i = 0; i < lineListAll.size(); i++)
//	{
//	    String output = lineListAll.get(i).first.getID() + " and " + lineListAll.get(i).second.getID();
//	    System.out.println(output);
//	}
	return lineListAll;
    }
    
    //Make sure there are no duplicate lines 
    //Assuming the previous line list is correct then this means that we will be removing all the internal lines and only keeping the outline and openings.
    public static ArrayList<Line> RemoveDuplicateLines(ArrayList<Line> lineList)
    {
	ArrayList<Line> newlineList = new ArrayList<>();
	for (int i = 0; i < lineList.size(); i++)
	{
	    Line test = lineList.get(i);
	    
	    if (noDuplicateLine2(test, lineList))
		newlineList.add(test);
	}
	
//	System.out.println("LINES FOUND: "+newlineList.size());
//	//Print results:
//	for (int i = 0; i < newlineList.size(); i ++)
//	{
//	    String output = newlineList.get(i).first.getID() +" and "+ newlineList.get(i).second.getID();
//	    System.out.println(output);
//	}
	return newlineList;
    }
    
    static Boolean noDuplicateLine2(Line line, ArrayList<Line> lineList)
    {
	for (int l = 0; l < lineList.size(); l++)
	{
	    Line current = lineList.get(l);
	    //dont match with self
	    if(current != line)
	    {
		//if the line vectors match in any order then return false
		if ((current.first.getID().equals(line.first.getID()) && current.second.getID().equals(line.second.getID())) ||
		    (current.first.getID().equals(line.second.getID()) && current.second.getID().equals(line.first.getID())))
		{
		    //System.out.println("MATCHED: "+current.first.getID()+" "+current.second.getID()+" with "+line.first.getID()+" "+line.second.getID());
		    return false;
		}
	    }
	}
	//return true if no match was found
	return true;
    }

    static ArrayList<Vector> orderFaceShape(ArrayList<Line> lineList)
    {
	ArrayList<Vector> frontFace = new ArrayList<>();
	//find bottom left most line point and add it the frontFace array, this is where we start
	Vector startPoint1 = lineList.get(0).first;
	Vector startPoint2 = lineList.get(0).second;
	for (int l = 0; l < lineList.size(); l++)
	{
	    Line currentLine = lineList.get(l);
	    //check first vector
	    if(currentLine.first.Z() <= startPoint1.Z() &&
	       currentLine.first.X() < startPoint1.X() &&
	       currentLine.first.Y() < startPoint1.Y())
	    {
		startPoint1 = currentLine.first;
		startPoint2 = currentLine.second;
	    }
	    //check second vector
	    if(currentLine.second.Z() <= startPoint1.Z() &&
	       currentLine.second.X() < startPoint1.X() &&
	       currentLine.second.Y() < startPoint1.Y())
	    {
		startPoint1 = currentLine.second;
		startPoint2 = currentLine.first;
	    }
	}
	frontFace.add(startPoint1);
	frontFace.add(startPoint2);
	
	//iterate rest of lines to create a shape that joins back to the first point.
	//vector points will come linked pairs because they are lines
	boolean done = false;
	while (true)
	{
	    Vector previousPoint1 = frontFace.get(frontFace.size()-2);
	    Vector previousPoint2 = frontFace.get(frontFace.size()-1);
	    for (int l = 0; l < lineList.size(); l++)
	    {
		Line currentLine = lineList.get(l);
		Vector first = currentLine.first;
		Vector second = currentLine.second;
		//make sure one point matches
		if (previousPoint2.getID().equals(first.getID()) || previousPoint2.getID().equals(second.getID()))
		{
		    boolean firstMatch = (previousPoint2.getID().equals(first.getID()));
		    boolean secondMatch = (previousPoint2.getID().equals(second.getID()));
		    boolean previousFirstMatch = (previousPoint1.getID().equals(first.getID()));
		    boolean previousSecondMatch = (previousPoint1.getID().equals(second.getID()));
		    //make sure the other point is not the same as the previous line segment
		    if ((firstMatch && previousSecondMatch) ||
			(secondMatch && previousFirstMatch))
		    {
			//Do nothing, just move to next iteration
			continue;
		    }
		    //check if only one point matches, and add next vector point
		    else if (firstMatch)
		    {
			//Check if line joins back to the start, and break loop if it does
			if (second.getID().equals(startPoint1.getID()))
			{
			    done = true;
			    break;
			}
			//add next vector point
			frontFace.add(second);
		    }
		    //check other end of line
		    else if (secondMatch)
		    {
			//Check if line joins back to the start, and break loop if it does
			if (first.getID().equals(startPoint1.getID()))
			{
			    done = true;
			    break;
			}
			//add next vector point
			frontFace.add(first);
		    }
		}
	    }
	    if (done)
	    {
		break;
	    }
	}
	
//	System.out.println("LINES DONE: "+ frontFace.size());
//	for (int i = 0; i < frontFace.size(); i++)
//	{
//	    System.out.println(frontFace.get(i).getID());
//	}
	
	return frontFace;
    }
}
