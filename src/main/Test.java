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
public class Test
{
    public static void main(String[] args)
    {
	Vector pt1 = new Vector(0, 70, 2450);
	Vector pt2 = new Vector(0, 970, 2450);
	Vector pt3 = new Vector(0, 970, 0);
	Vector pt4 = new Vector(0, 70, 0);
	
//	Vector pt1 = new Vector(70, 0, 2450);
//	Vector pt2 = new Vector(970, 0, 2450);
//	Vector pt3 = new Vector(970, 0, 0);
//	Vector pt4 = new Vector(70, 0, 0);
	
	Face face1 = new Face(pt4, pt1, pt2);
	Face face2 = new Face(pt2, pt3, pt4);
	ArrayList<Face> faceList = new ArrayList<>();
	faceList.add(face1); 
	faceList.add(face2);
	//Create list of lines from the filteredMeshList, but make sure there are no duplicate lines (This means that we will be removing all the internal lines and only keeping outline and openings)
	ArrayList<Line> lineListAll = Utils.convertMeshToLines(faceList);
	
	//remove all duplicates
	ArrayList<Line> lineList = Utils.RemoveDuplicateLines(lineListAll);
	
	//Create ordered list of vectors for the front face
	ArrayList<Vector> frontFace = Utils.orderFaceShape(lineList);
	
	//getAngleX();
    }
    static double getAngleX()
    {
	//Horizontal/X = 1.,0.,0. and //VerticleY= 0.,1.,0. and //Angle example= 0.63,0.77,0.
	//The values are the Sine of the angle SIN(angle), for example 45 degrees is: 0.70710678118654752440084436210485
	double xDiff = 45 - 0;
        double yDiff = 50 - 0;
        double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));
	System.out.println("ATAN: "+Math.atan2(yDiff, xDiff));
	System.out.println("DEGREES: "+angle);	
	System.out.println("RADIANS: "+Math.toRadians(angle));	
	System.out.println("SIN: "+Math.sin(Math.toRadians(angle)));	

	if (angle < 0)
	{
	    double X = Math.sin(Math.toRadians(angle));
	    double Y = Math.sin(Math.toRadians(-90-angle));
	    System.out.println("-X: "+X);
	    System.out.println("-Y: "+Y);
	}
	else
	{
	    double X = Math.sin(Math.toRadians(angle));
	    double Y = Math.sin(Math.toRadians(90-angle));
	    System.out.println("X: "+X);
	    System.out.println("Y: "+Y);
	}
	
	return angle;
    }
}
