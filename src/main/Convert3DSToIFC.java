/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.File;
import java.util.ArrayList;
import no.myke.parser.Model;
import no.myke.parser.ModelLoader;
import no.myke.parser.ParserException;
/**
 *
 * @author j.simpson
 */
public class Convert3DSToIFC
{
    private static final File file = new File("Plan2.3ds");
    static JobModel jobModel = new JobModel(null);
    static float scale = 10;
    static int wallThickness = 90; //mm
    static double tolerance = 0.001;
    
    static final String wallTag = "Wall";
    static final String windowTag = "Window";
    static final String doorTag = "Door";
    static final String postTag = "Post";
    static final String roofTag = "Roof";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	jobModel = new JobModel(file.getName());
	
	try
	{
	    Model model = ModelLoader.load3dModel(file, scale);
	    for (int o = 0; o < model.objects.size(); o++)
	    {
		//Get object name
		String objectName = model.objects.get(o).getName();
		//System.out.println("Name: "+ objectName);
		
		//Create walls && (Make sure wall name is either straight or numbered with no space)
		if (model.objects.get(o).getName().startsWith(wallTag) && (objectName.length() == wallTag.length() || objectName.charAt(wallTag.length()) != ' '))
		{
		    jobModel.addWall(new Wall(model.objects.get(o)));
		}
		//create openings
		else if (model.objects.get(o).getName().startsWith(windowTag))
		{
		    //jobModel.addWindow(new Opening(model.objects.get(o)));
		}
		else if (model.objects.get(o).getName().startsWith(doorTag))
		{
		    //jobModel.addDoor(new Opening(model.objects.get(o)));
		}
		//create posts && (Make sure post name is either straight or numbered with no space)
		else if (model.objects.get(o).getName().startsWith(postTag) && (objectName.length() == postTag.length() || objectName.charAt(postTag.length()) != ' '))
		{
		    //jobModel.addPost(new Post(model.objects.get(o)));
		}
		//create roof
		else if (model.objects.get(o).getName().startsWith(roofTag))
		{
		    //jobModel.addRoofPlane(new RoofPlane(model.objects.get(o)));
		}
	    }
	}
	catch (ParserException ex)
	{
	    ex.printStackTrace();
	}
	
	//Keep required materials only so that the resulting model will be clear of junk.
	for (Wall wall : jobModel.walls)
	{
	    //Materials to keep:
	    ArrayList materialsToKeep = new ArrayList<String>();
	    materialsToKeep.add("Zog frame");
	    //Prune unneeded materials/faces:
	    Utils.pruneMaterials(wall, materialsToKeep);
	}
	for (Opening window : jobModel.windows)
	{
	    //Materials to keep:
	    ArrayList materialsToKeep = new ArrayList<String>();
	    materialsToKeep.add("Antique");
	    //Prune unneeded materials/faces:
	    Utils.pruneMaterials(window, materialsToKeep);
	}
	for (RoofPlane roofPlane : jobModel.roofPlanes)
	{
	    //Materials to keep:
	    ArrayList materialsToKeep = new ArrayList<String>();
	    materialsToKeep.add("Fir Stock Std. +");
	    //Prune unneeded materials/faces:
	    Utils.pruneMaterials(roofPlane, materialsToKeep);
	}
	
	//Openings: Convert object to a footprint and a face shape. These can be used to determin shape and to extrude walls/openings.
	for (Opening door : jobModel.doors)
	{
	    //Utils.getInnerOpeningSize(door);
	}
	for (Opening window : jobModel.windows)
	{
	    //Utils.getInnerOpeningSize(door);
	}
	
	//Assign doors to walls
	for (Opening door : jobModel.doors)
	{
	    //Compare opening location with wall location
	    for (Wall wall : jobModel.walls)
	    {
		if (wall.contains(door.getVectorList()))
		{
		    wall.addOpening(door);
		    break;
		}
	    }
	}
	//Assign windows to walls
	for (Opening window : jobModel.windows)
	{
	    //Compare opening location with wall location
	    for (Wall wall : jobModel.walls)
	    {
		if (wall.contains(window.getVectorList()))
		{
		    wall.addOpening(window);
		    break;
		}
	    }
	}
	
	//Merge openings that are touching or overlapping
	
	//Custom logic to fix incorrect opening sizes
	//Openings: Remove complex geometry, and create simple boxes that will be used to extrude walls and be recognised as openings in software importing the model.
	
	//Find and fix collisions
	
	//Custom logic to check for drafting errors and common mistakes
	
	//Generate IFC header
	
	//add components to IFC
	
	//Expart data to IFC or other format
	DXF_Render_Testing.DXFwrite("output.dxf", jobModel);
    }
    
    //Check if values are equal or within tolerance
    public static boolean CloseEnough(double value1, double value2)
    {
	return Math.abs(value1 - value2) <= tolerance;
    }
}
