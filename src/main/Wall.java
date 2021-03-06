/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import no.myke.parser.ModelObject;
/**
 *
 * @author j.simpson
 */
public class Wall extends CuboidObject
{   
    final static double thickness = 90;
    
    private ArrayList<Opening> openings = new ArrayList<>();
    
    Wall(ModelObject model, ArrayList<String> materialsToKeep)
    {
	super(model, materialsToKeep);
    }

    public void addOpening(Opening door)
    {
	openings.add(door);
    }
    public ArrayList<Opening> getOpenings(){return openings;}
    public Double getThickness(){return thickness;}

    ArrayList<Opening> getWindows()
    {
	return openings;
    }
}
