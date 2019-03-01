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
public class CuboidObject extends genericObject
{    
    private final Vector[] footPrint = new Vector[4];
    private ArrayList<Vector> frontFace = new ArrayList<>();
    
    CuboidObject(ModelObject model, ArrayList<String> materialsToKeep)
    {
	super(model, materialsToKeep);
	
	findShape();
    }
    
    private void findShape()
    {
	
	System.out.println(super.name);
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
	Vector longestFaceStart, longestFaceEnd;
	
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
	ArrayList<Vector> tempFrontFace = new ArrayList<>();
	//Then iterate through the filtered faces and find the shape of the wall elevation.
	//Start from the bottomLeft polygon/face and iterate the filteredMeshList to find the next external point.
	if (longestFaceStart.Z() < longestFaceEnd.Z())
	    tempFrontFace.add(longestFaceStart);
	else
	    tempFrontFace.add(longestFaceEnd);
	System.out.println("START");
	System.out.println("STR: "+longestFaceEnd.getID());
	while (true)
	{
	    =; //All broken. First work out how to save IFC files, and then I will know how to best manipulate the polygons (Extrusion from a polyline?).
	    //When an outer edge is found add it to the frontFace array
	    Vector found = findNextJointVector(tempFrontFace.get(tempFrontFace.size()-1), filteredMeshList, tempFrontFace);
	    //Check if shape has been completed
	    if (tempFrontFace.size() > 2 && found == null
	     || tempFrontFace.size() > 2 && tempFrontFace.get(0).getID().equals(found.getID()))
	    {
		break;
	    }
	    System.out.println("OUT: "+found.getID());
	    //Add vector
	    tempFrontFace.add(found);
	    =;
	}
	frontFace = tempFrontFace;
	
//	//DELETE ME
//	System.out.println("D: "+filteredMeshList.size());
//	super.setMeshList(filteredMeshList);
    }
  
    Vector findNextJointVector(Vector previousPoint, ArrayList<Face> listToCheck, ArrayList<Vector> doneList)
    {
	//Work clockwise from bottom left
	Face currentFace;
	Vector currentVector;
	Vector currentNextPoint = null;
	for (int f = 0; f < listToCheck.size(); f++)
	{
	    currentFace = listToCheck.get(f);
	    //first check if any point on the polygon/face matches
	    if (previousPoint.getID().equals(currentFace.Point1().getID())
	    || previousPoint.getID().equals(currentFace.Point2().getID())
	    || previousPoint.getID().equals(currentFace.Point3().getID()))
	    {
		//loop through vector on a face
		for (int v = 0; v < 3; v++)
		{
		    if (v == 0)
			currentVector = currentFace.Point1();
		    else if (v == 1)
			currentVector = currentFace.Point2();
		    else
			currentVector = currentFace.Point3();

		    //make sure it is not matching with itself
		    if (false == previousPoint.getID().equals(currentVector.getID()) && false == doneList.contains(currentVector))
		    {
			//Set a point to work with
			if (currentNextPoint == null)
			{
			    currentNextPoint = currentVector;
			}
			//Now check for next vector in clockwise motion
			//Get next bottom most point that is to the left
			if (isAbove(previousPoint, currentVector)
			&& isLeft(previousPoint, currentVector)
			&& isBelow(currentNextPoint, currentVector)
			&& isRight(currentNextPoint, currentVector))
			{
			    currentNextPoint = currentVector;
			}
			//If none, then get next left most point that is top
			else if (isRight(previousPoint, currentVector)
			&& isAbove(previousPoint, currentVector)
			&& isLeft(currentNextPoint, currentVector)
			&& isBelow(currentNextPoint, currentVector))
			{
			    currentNextPoint = currentVector;
			}
			//If none, then get next top most point that is right
			else if (isBelow(previousPoint, currentVector)
			&& isRight(previousPoint, currentVector)
			&& isAbove(currentNextPoint, currentVector)
			&& isLeft(currentNextPoint, currentVector))
			{
			    currentNextPoint = currentVector;
			}
			//If none, then get next right most point that is bottom
			else if (isLeft(previousPoint, currentVector)
			&& isBelow(previousPoint, currentVector)
			&& isRight(currentNextPoint, currentVector)
			&& isAbove(currentNextPoint, currentVector))
			{
			    currentNextPoint = currentVector;
			}
		    }
		}
	    }
	}
	
	//make sure it doesnt match with itself
	return currentNextPoint;
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
}
