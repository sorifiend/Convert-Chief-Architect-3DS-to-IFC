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
		if (model.objects.get(o).getName().startsWith("Wall") && (objectName.length() == wallTag.length() || objectName.charAt(wallTag.length()) != ' '))
		{
		    jobModel.addWall(new Wall(model.objects.get(o)));
		}
		//create openings
		if (model.objects.get(o).getName().startsWith("Window"))
		{
		    jobModel.addWindow(new Opening(model.objects.get(o)));
		}
		if (model.objects.get(o).getName().startsWith("Door"))
		{
		    jobModel.addDoor(new Opening(model.objects.get(o)));
		}
		//create posts && (Make sure post name is either straight or numbered with no space)
		if (model.objects.get(o).getName().startsWith("Post") && (objectName.length() == postTag.length() || objectName.charAt(postTag.length()) != ' '))
		{
		    jobModel.addPost(new Post(model.objects.get(o)));
		}
		//create roof
		if (model.objects.get(o).getName().startsWith("Roof"))
		{
		    jobModel.addRoofPlane(new RoofPlane(model.objects.get(o)));
		}
	    }
	}
	catch (ParserException ex)
	{
	    ex.printStackTrace();
	}
	
	//Walls: Keep required materials only so that the resulting model will be clear of junk.
	for (Wall wall : jobModel.walls)
	{	    
	    //Prune unneeded materials/faces:
	    ArrayList materialsToKeep = new ArrayList<String>();
	    materialsToKeep.add("Zog frame");
	    Utils.pruneMaterials(wall, materialsToKeep);
	    
	}
	//Merge openings that are touching or overlapping
	
	//Openings: Remove complex geometry, and create simple boxes that will be used to extrude walls and be recognised as openings in software importing the model.
	for (Opening door : jobModel.doors)
	{
	    Utils.getInnerOpeningSize(door);
	}
	
	//Custom logic to fix incorrect opening sizes
	
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
