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
	
	//Walls: Remove cladding and linings so that only the raw size will be used in the IFC model.
	//We expect a wall centre of 90mm wide, so prune anything with a different width.
	for (Wall wall : jobModel.walls)
	{
	    System.out.println(wall.name+": "+wall.getFaceList().size());
	    
	    //Prune unneeded faces:
	    ArrayList materialsToKeep = new ArrayList<String>();
	    materialsToKeep.add("Zog frame");
	    wall.pruneMaterials(materialsToKeep);
	    
//	    //Find real wall bounds
//	    //Compare every bound with every other bound to find a match with wall thickness
//	    String vectorType = Vector.TYPE_NA;
//	    float side1 = 0, side2 = 0;
//	    for (int i = 0; i < wall.getVectorList().length; i++)
//	    {
//		for (int j = i+1; j < wall.getVectorList().length; j++)
//		{
//		    if (CloseEnough(90, wall.getVectorList()[i].X() - wall.getVectorList()[j].X()) || CloseEnough(-90, wall.getVectorList()[i].X() - wall.getVectorList()[j].X()))
//		    {
//			side1 = wall.getVectorList()[i].X();
//			side2 = wall.getVectorList()[j].X();
//			if (side1 > side2)
//			{
//			    side1 = wall.getVectorList()[j].X();
//			    side2 = wall.getVectorList()[i].X();
//			}
//			vectorType = Vector.TYPE_X;
//			break;
//		    }
//		    if (CloseEnough(90, wall.getVectorList()[i].Y() - wall.getVectorList()[j].Y()) || CloseEnough(-90, wall.getVectorList()[i].Y() - wall.getVectorList()[j].Y()))
//		    {
//			side1 = wall.getVectorList()[i].Y();
//			side2 = wall.getVectorList()[j].Y();
//			if (side1 > side2)
//			{
//			    side1 = wall.getVectorList()[j].Y();
//			    side2 = wall.getVectorList()[i].Y();
//			}
//			vectorType = Vector.TYPE_Y;
//			break;
//		    }
//		}
//		if (vectorType.equals(Vector.TYPE_NA) == false) break;
//	    }
//	    
//	    ArrayList<Float> arrayList = new ArrayList<>();
//	    arrayList.add(side1);
//	    arrayList.add(side2);
//	    
//	    System.out.println(wall.name+": "+vectorType+" "+side1+" "+side2);
//	    System.out.println(wall.name+": "+wall.getFaceList().size());
//	    	    
//	    wall.pruneVertices(vectorType, arrayList);
	    
	    System.out.println(wall.name+": "+wall.getFaceList().size());
	    //System.out.println("  Extents X,Y,Z: "+wall.getMinX()+"|"+wall.getMaxX()+" "+wall.getMinY()+"|"+wall.getMaxY());
	}
	DXF_Render_Testing.DXFwrite("output.dxf", jobModel);
	//Openings: Remove complex geometry, and use simple boxes that will be recognised as openings in software importing the IFC model.
	
	//Find and fix collisions
	
	//Custom logic to check for drafting errors and common mistakes
	
	//Generate IFC header
	
	//add components to IFC
    }

//    public static ArrayList<Float> findCoreWallThickness(Wall wall)
//    {
//	ArrayList<Float> arrayList = new ArrayList<>();
//	
//	
//	
//	
//	=;
//	
//	
//	
//	
//	return arrayList;
//    }
    
    //Check if values are equal or within tolerance
    public static boolean CloseEnough(double value1, double value2)
    {
	return Math.abs(value1 - value2) <= tolerance;
    }
}
