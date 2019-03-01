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
import no.myke.parser.Vector;
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
		    //Materials to keep:
		    ArrayList materialsToKeep = new ArrayList<String>();
		    materialsToKeep.add("Zog frame");
		    //Create wall and prune unneeded materials/faces:
		    jobModel.addWall(new Wall(model.objects.get(o), materialsToKeep));
		}
		//create openings
		else if (model.objects.get(o).getName().startsWith(windowTag))
		{
		    //Materials to keep:
		    ArrayList materialsToKeep = new ArrayList<String>();
		    materialsToKeep.add("Antique");
		    //Create wall and prune unneeded materials/faces:
		    //jobModel.addWindow(new Opening(model.objects.get(o), materialsToKeep));
		}
		else if (model.objects.get(o).getName().startsWith(doorTag))
		{
		    //jobModel.addDoor(new Opening(model.objects.get(o), materialsToKeep));
		}
		//create posts && (Make sure post name is either straight or numbered with no space)
		else if (model.objects.get(o).getName().startsWith(postTag) && (objectName.length() == postTag.length() || objectName.charAt(postTag.length()) != ' '))
		{
		    //jobModel.addPost(new Post(model.objects.get(o), materialsToKeep));
		}
		//create roof
		else if (model.objects.get(o).getName().startsWith(roofTag))
		{
		    //Materials to keep:
		    ArrayList materialsToKeep = new ArrayList<String>();
		    materialsToKeep.add("Fir Stock Std. +");
		    //Create wall and prune unneeded materials/faces:
		    //jobModel.addRoofPlane(new RoofPlane(model.objects.get(o), materialsToKeep));
		}
	    }
	}
	catch (ParserException ex)
	{
	    ex.printStackTrace();
	}
	
	//Assign doors to walls
	for (Opening door : jobModel.doors)
	{
	    //Compare opening location with wall location
	    for (Wall wall : jobModel.walls)
	    {
//		if (wall.contains(door.getVectorList()))
//		{
//		    wall.addOpening(door);
//		    break;
//		}
	    }
	}
	//Assign windows to walls
	for (Opening window : jobModel.windows)
	{
	    //Compare opening location with wall location
	    for (Wall wall : jobModel.walls)
	    {
//		if (wall.contains(window.getVectorList()))
//		{
//		    wall.addOpening(window);
//		    break;
//		}
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
}
