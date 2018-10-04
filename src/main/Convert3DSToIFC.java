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

    //private static File file = new File("src/test/resources/fighter.3ds");
    private static File file = new File("Plan2.3ds");
    private static int scale = 10000;
    static JobModel jobModel = new JobModel(null);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	try
	{
	    jobModel = new JobModel(file.getName());
	    // TODO code application logic here
	    Model model = ModelLoader.load3dModel(file);
	    for (int o = 0; o < model.objects.size(); o++)
	    {
		//
		String objectName = model.objects.get(o).getName();
		System.out.println("Name: "+ objectName);
		
		//Create walls
		if (model.objects.get(o).getName().startsWith("Wall") && (objectName.length() == 4 || objectName.charAt(4) != ' '))
		{
		    jobModel.addWall(new Wall(model.objects.get(o).vertices, objectName));
		}
		//create openings
		if (model.objects.get(o).getName().startsWith("Window"))
		{
		    jobModel.addWindow(new Opening(model.objects.get(o).vertices, objectName));
		}
		if (model.objects.get(o).getName().startsWith("Door"))
		{
		    jobModel.addWindow(new Opening(model.objects.get(o).vertices, objectName));
		}
		
	    }
	}
	catch (ParserException ex)
	{
	    ex.printStackTrace();
	}
    }
    
}
