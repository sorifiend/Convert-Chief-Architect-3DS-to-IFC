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
    private ArrayList<Opening> openings = new ArrayList<>();
    
    public Wall(ModelObject model)
    {
	//Initialize using constructor in extended class genericObject
	super(model);
    }

    public void addOpening(Opening door)
    {
	openings.add(door);
    }
    public ArrayList<Opening> getOpenings(){return openings;}
}
