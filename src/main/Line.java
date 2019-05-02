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
public class Line
{
    Vector first = new Vector(0, 0, 0);
    Vector second = new Vector(0, 0, 0);
    public Line(Vector first, Vector second)
    {
	this.first = first;
	this.second = second;
    }
    public Vector getFirst()
    {
	return first;
    }
    public Vector getSecond()
    {
	return second;
    }
}
