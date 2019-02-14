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
public class RoofPlane extends genericObject
{
    public RoofPlane(ModelObject model, ArrayList<String> materialsToKeep)
    {
	//Initialize using constructor in extended class genericObject
	super(model, materialsToKeep);
    }
}
