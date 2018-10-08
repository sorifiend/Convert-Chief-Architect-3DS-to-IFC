/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import no.myke.parser.Vector;
/**
 *
 * @author j.simpson
 */
public class Face
{
    private Vector point1, point2, point3;
    Face(Vector point1, Vector point2, Vector point3)
    {
	this.point1 = point1;
	this.point2 = point2;
	this.point3 = point3;
    }

    public Vector Point1()
    {
	return point1;
    }

    public Vector Point2()
    {
	return point2;
    }

    public Vector Point3()
    {
	return point3;
    }
    
    public Vector[] getVectors(){return new Vector[]{point1, point2, point3};}
}
