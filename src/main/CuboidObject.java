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
	//Then iterate through the filtered faces and find the shape of the wall elevation.
	for (int m = 0; m < filteredMeshList.size(); m++)
	{
	    //Start from the bottomLeft point and iterate the filteredMeshList to find the next external point.
	    //When an outer edge is found add it to the frontFace array
	    
	}
	
//	//DELETE ME
//	System.out.println("D: "+filteredMeshList.size());
//	super.setMeshList(filteredMeshList);
    }
}
