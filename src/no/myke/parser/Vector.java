/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package no.myke.parser;

/**
 *
 * @author j.simpson
 */
public class Vector
{
//    public static final String TYPE_X = "X", TYPE_Y = "Y", TYPE_Z = "Z", TYPE_NA = "NA";
    float x, y, z;
    
    public Vector (float x, float y, float z)
    {
	this.x = x;
	this.y = y;
	this.z = z;
    }
    public float X()
    {
	return x;
    }

    public float Y()
    {
	return y;
    }

    public float Z()
    {
	return z;
    }
    
//    public String getID()
//    {
//	return x+"|"+y+"|"+z;
//    }
}
