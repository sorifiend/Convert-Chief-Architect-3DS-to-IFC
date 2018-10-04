/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.File;
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
    static int scale = 10;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	jobModel = new JobModel(file.getName());
	
	try
	{
	    Model model = ModelLoader.load3dModel(file);
	    for (int o = 0; o < model.objects.size(); o++)
	    {
		//
		String objectName = model.objects.get(o).getName();
		System.out.println("Name: "+ objectName);
		
		//Create walls
		if (model.objects.get(o).getName().startsWith("Wall") && (objectName.length() == 4 || objectName.charAt(4) != ' '))
		{
		    jobModel.addWall(new Wall(model.objects.get(o).vertices, objectName, scale));
		}
		//create openings
		if (model.objects.get(o).getName().startsWith("Window"))
		{
		    jobModel.addWindow(new Opening(model.objects.get(o).vertices, objectName, scale));
		}
		if (model.objects.get(o).getName().startsWith("Door"))
		{
		    jobModel.addWindow(new Opening(model.objects.get(o).vertices, objectName, scale));
		}
	    }
	}
	catch (ParserException ex)
	{
	    ex.printStackTrace();
	}
	
	//Walls: Remove cladding and linings so that only the raw size will be used in the IFC model.
	
	//Openings: Remove complex geometry, and use simple boxes that will be recognised as openings in software importing the IFC model.
	
	//Find and fix collisions
	
	//Custom logic to check for drafting errors and common mistakes
	
	//Generate IFC header
	
	//add components to IFC
    }
    
}
