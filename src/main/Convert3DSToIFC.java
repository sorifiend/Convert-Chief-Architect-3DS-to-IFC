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
		
		//Create walls
		if (model.objects.get(o).getName().startsWith("Wall") && (objectName.length() == 4 || objectName.charAt(4) != ' '))
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
		    jobModel.addWindow(new Opening(model.objects.get(o)));
		}
	    }
	}
	catch (ParserException ex)
	{
	    ex.printStackTrace();
	}
	
	//Walls: Keep required materials only so that the resulting model will be clear of junk.
	//We expect a wall centre of 90mm wide, so prune anything with a different width.
	for (Wall wall : jobModel.walls)
	{	    
	    //Prune unneeded materials/faces:
	    ArrayList materialsToKeep = new ArrayList<String>();
	    materialsToKeep.add("Zog frame");
	    wall.pruneMaterials(materialsToKeep);
	    
	}
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
