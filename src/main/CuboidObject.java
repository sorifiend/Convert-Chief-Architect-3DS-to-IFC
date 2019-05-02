/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import java.util.List;
import no.myke.parser.ModelObject;
import no.myke.parser.Vector;
/**
 *
 * @author j.simpson
 */
public class CuboidObject extends genericObject
{    
    final Vector[] footPrint = new Vector[4];
    private ArrayList<Vector> frontFace = new ArrayList<>();
    private double length = 0;
    Vector longestFaceStart, longestFaceEnd;
    
    CuboidObject(ModelObject model, ArrayList<String> materialsToKeep)
    {
	super(model, materialsToKeep);
	
	findShape();
    }
    
    private void findShape()
    {
	//Find object 2D extents/footprint
	Vector bottomLeft = null, leftTop = null, topRight = null, rightBottom = null;
	for (int f = 0; f < super.getFaceList().size(); f++)
	{
	    Face face = super.getFaceList().get(f);  
	    if (f == 0)
	    {
		//Set to opposite
		bottomLeft = new Vector(getMaxX(), getMaxY(), 0);
		leftTop = new Vector(getMaxX(), getMinY(), 0);
		topRight = new Vector(getMinX(), getMinY(), 0);
		rightBottom = new Vector(getMinX(), getMaxY(), 0);
	    }
	    
	    Vector vector = new Vector(0, 0, 0);
	    for (int i = 0; i < 3; i++)
	    {
		if(i == 0)
		    vector = face.Point1();
		else if(i == 1)
		    vector = face.Point2();
		else
		    vector = face.Point3();
		    
		//Find bottomLeft most vector (Left end)
		if (vector.Y() < bottomLeft.Y() || (vector.Y() == bottomLeft.Y() && vector.X() < bottomLeft.X())) 
		{
		    bottomLeft = vector;
		}
		//Find leftTop most vector (Top end)
		if (vector.X() < leftTop.X() || (vector.X() == leftTop.X() && vector.Y() > leftTop.Y())) {
		    leftTop = vector;
		}
		//Find topRight most vector (rightBottom end)
		if (vector.Y() > topRight.Y() || (vector.Y() == topRight.Y() && vector.X() > topRight.X())) {
		    topRight = vector;
		}
		//Find rightBottom most vector (bottomLeft end)
		if (vector.X() > rightBottom.X() || (vector.X() == rightBottom.X() && vector.Y() < rightBottom.Y())) {
		    rightBottom = vector;
		}
	    }
	}
	footPrint[0] = bottomLeft;
	footPrint[1] = leftTop;
	footPrint[2] = topRight;
	footPrint[3] = rightBottom;
	//Find longest side of the extents/footprint
	double bottomLength = Utils.distance2D(rightBottom, bottomLeft);
	double leftLength = Utils.distance2D(bottomLeft, leftTop);
	double topLength = Utils.distance2D(leftTop, topRight);
	double rightLength = Utils.distance2D(topRight, rightBottom);
	
	//Check it bottom side is longest
	if (bottomLength > leftLength && bottomLength > topLength && bottomLength > rightLength)
	{
	    longestFaceStart = rightBottom;
	    longestFaceEnd = bottomLeft;
	}
	//Check it left side is longest
	else if (leftLength > topLength && leftLength > rightLength)
	{
	    longestFaceStart = bottomLeft;
	    longestFaceEnd = leftTop;
	}
	//Check it top side is longest
	else if (topLength > rightLength)
	{
	    longestFaceStart = leftTop;
	    longestFaceEnd = topRight;
	}
	//Right side is longest
	else
	{
	    longestFaceStart = topRight;
	    longestFaceEnd = rightBottom;
	}
	
	length = Utils.distance2D(longestFaceStart, longestFaceEnd);
	//System.out.println(name + " WALL LENGTH: "+length);
	//Find the object face shape along the longest side (We need this so that we can deal with walls that have multiple heights, beam pockets or angles)
	//Iterates through all faces and find the faces where all vectors are on the longest face from above
	ArrayList<Face> meshList = super.getFaceList();
	ArrayList<Face> filteredMeshList = new ArrayList<>();
	for (int m = 0; m < meshList.size(); m++)
	{
	    if (Utils.isVectorBetween2D(longestFaceStart, longestFaceEnd, meshList.get(m).Point1())
	     && Utils.isVectorBetween2D(longestFaceStart, longestFaceEnd, meshList.get(m).Point2())
	     && Utils.isVectorBetween2D(longestFaceStart, longestFaceEnd, meshList.get(m).Point3()))
	    {
		filteredMeshList.add(meshList.get(m));
	    }
	}
	
//	ArrayList<Vector> tempFrontFace = new ArrayList<>();
//	//Then iterate through the filtered faces and find the shape of the wall elevation.
//	//Start from the bottomLeft polygon/face and iterate the filteredMeshList to find the next external point.
//	if (longestFaceStart.Z() < longestFaceEnd.Z())
//	    tempFrontFace.add(longestFaceStart);
//	else
//	    tempFrontFace.add(longestFaceEnd);
//	System.out.println("START");
//	System.out.println("STR: "+longestFaceEnd.getID());
//	
//	while (true)
//	{
//	    =; //All broken. First work out how to save IFC files, and then I will know how to best manipulate the polygons (Extrusion from a polyline?).
//	    //When an outer edge is found add it to the frontFace array
//	    Vector found = findNextJointVector(tempFrontFace.get(tempFrontFace.size()-1), filteredMeshList, tempFrontFace);
//	    //Check if shape has been completed
//	    if (tempFrontFace.size() > 2 && found == null
//	     || tempFrontFace.size() > 2 && tempFrontFace.get(0).getID().equals(found.getID()))
//	    {
//		break;
//	    }
//	    System.out.println("OUT: "+found.getID());
//	    //Add vector
//	    tempFrontFace.add(found);
//	    =;
//	}
//	=; // frontFace needs to be created with a set of ordered points (clockwise order suggested).
//	frontFace = tempFrontFace;
	
	
	
	//Create list of lines from the filteredMeshList, but make sure there are no duplicate lines (This means that we will be removing all the internal lines and only keeping outline and openings)
	ArrayList<Line> lineListAll = Utils.convertMeshToLines(filteredMeshList);
	
	//remove all duplicates
	ArrayList<Line> lineList = Utils.RemoveDuplicateLines(lineListAll);
	
	//Create ordered list of vectors for the front face
	frontFace = Utils.orderFaceShape(lineList);
    }
    
//    Boolean noDuplicateLine(Line line, ArrayList<Line> lineList)
//    {
//	for (int l = 0; l < lineList.size(); l++)
//	{
//	    Line current = lineList.get(l);
//	    //if the line vectors match in any order then declear a match found and return false
//	    if ((current.first.getID().equals(line.first.getID()) && current.second.getID().equals(line.second.getID())) ||
//		(current.first.getID().equals(line.second.getID()) && current.second.getID().equals(line.first.getID())))
//	    {
//		return false;
//	    }
//	}
//	//return true if no match was found
//	return true;
//    }
    
//    Vector findNextJointVector(Vector previousPoint, ArrayList<Face> listToCheck, ArrayList<Vector> doneList)
//    {
//	//Work clockwise from bottom left
//	Face currentFace;
//	Vector currentVector;
//	Vector currentNextPoint = null;
//	for (int f = 0; f < listToCheck.size(); f++)
//	{
//	    currentFace = listToCheck.get(f);
//	    //first check if any point on the polygon/face matches
//	    if (previousPoint.getID().equals(currentFace.Point1().getID())
//	    || previousPoint.getID().equals(currentFace.Point2().getID())
//	    || previousPoint.getID().equals(currentFace.Point3().getID()))
//	    {
//		//loop through vector on a face
//		for (int v = 0; v < 3; v++)
//		{
//		    if (v == 0)
//			currentVector = currentFace.Point1();
//		    else if (v == 1)
//			currentVector = currentFace.Point2();
//		    else
//			currentVector = currentFace.Point3();
//
//		    //make sure it is not matching with itself
//		    if (false == previousPoint.getID().equals(currentVector.getID()) && false == doneList.contains(currentVector))
//		    {
//			//Set a point to work with
//			if (currentNextPoint == null)
//			{
//			    currentNextPoint = currentVector;
//			}
//			//Now check for next vector in clockwise motion
//			//Get next bottom most point that is to the left
//			if (isAbove(previousPoint, currentVector)
//			&& isLeft(previousPoint, currentVector)
//			&& isBelow(currentNextPoint, currentVector)
//			&& isRight(currentNextPoint, currentVector))
//			{
//			    currentNextPoint = currentVector;
//			}
//			//If none, then get next left most point that is top
//			else if (isRight(previousPoint, currentVector)
//			&& isAbove(previousPoint, currentVector)
//			&& isLeft(currentNextPoint, currentVector)
//			&& isBelow(currentNextPoint, currentVector))
//			{
//			    currentNextPoint = currentVector;
//			}
//			//If none, then get next top most point that is right
//			else if (isBelow(previousPoint, currentVector)
//			&& isRight(previousPoint, currentVector)
//			&& isAbove(currentNextPoint, currentVector)
//			&& isLeft(currentNextPoint, currentVector))
//			{
//			    currentNextPoint = currentVector;
//			}
//			//If none, then get next right most point that is bottom
//			else if (isLeft(previousPoint, currentVector)
//			&& isBelow(previousPoint, currentVector)
//			&& isRight(currentNextPoint, currentVector)
//			&& isAbove(currentNextPoint, currentVector))
//			{
//			    currentNextPoint = currentVector;
//			}
//		    }
//		}
//	    }
//	}
//	
//	//make sure it doesnt match with itself
//	return currentNextPoint;
//    }
    
    List<Vector> getFaceShape(){
	return frontFace;
    }
    
    double getAngleX(){
	=;
	double xDiff = longestFaceEnd.X() - longestFaceStart.X();
        double yDiff = longestFaceEnd.Y() - longestFaceStart.Y();
        double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));

	if (angle < 0)
	{
	    angle += 360;
	}
	return Math.sin(Math.toRadians(angle));
    }
    double getAngleY(){
	=;
//	double xDiff = 50 - 0;
//        double yDiff = -10 - 0;
//        double angleX = Math.toDegrees(Math.atan2(yDiff, xDiff));
//	double angle;
//
//	if (angleX < 0)
//	{
//	    angle = -90-angleX;
//	}
//	else
//	{
//	    angle = 90-angleX;
//	}
//	
//	if (angle < 0)
//	{
//	    angle += 360;
//	}
//	return Math.sin(Math.toRadians(angle));
	double result = Math.sin(Math.toRadians(getAngleX()));
	if (result == 1.0)
	    return 0.0;
	else if (result == -1.0)
	    return 0.0;
	else if (result == 0.0)
	    return 1.0;
	else
	    return 0.0;
    }
    
    Boolean isAbove(Vector previous, Vector toCheck)
    {
	return previous.Z() < toCheck.Z();
    }
    Boolean isBelow(Vector previous, Vector toCheck)
    {
	return previous.Z() > toCheck.Z();
    }
    Boolean isLeft(Vector previous, Vector toCheck)
    {
	return previous.X() >= toCheck.X() && previous.Y() > toCheck.Y()
	    || previous.X() > toCheck.X() && previous.Y() >= toCheck.Y();
    }
    Boolean isRight(Vector previous, Vector toCheck)
    {
	return previous.X() <= toCheck.X() && previous.Y() < toCheck.Y()
	    || previous.X() < toCheck.X() && previous.Y() <= toCheck.Y();
    }
    
    Double getLength() {return length;}
}
