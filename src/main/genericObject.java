/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author j.simpson
 */
public class genericObject
{
    float minX = 0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
    float[] vertices;
    String name = "noName";
    int scale = 1;
    
    genericObject(float[] vertices, String name, int scale)
    {
	this.vertices = vertices;
	this.name = name;
	int count = 0;
	
	//Adjust with scale
	for (int v = 0; v < vertices.length; v++)
	{  
	    vertices[v] = vertices[v] * scale;
	}
	
	//Find extents
	for (int v = 0; v < vertices.length; v++)
	{   
	    float value = vertices[v];
	    count ++;
	    if(count == 1)
	    {
		if (v == 0)
		{
		    minX = value;
		    maxX = value;
		}
		else if (value < minX) minX = value;
		else if (value > maxX) maxX = value;
	    }
	    else if(count == 2)
	    {
		if (1 == 0)
		{
		    minY = value;
		    maxY = value;
		}
		else if (value < minY) minY = value;
		else if (value > maxY) maxY = value;
	    }
	    else if(count == 3)
	    {
		if (2 == 0)
		{
		    minZ = value;
		    maxZ = value;
		}
		else if (value < minZ) minZ = value;
		else if (value > maxZ) maxZ = value;
		count = 0;
	    }
	}
    }

    public float getMinX(){return minX;}
    public float getMaxX(){return maxX;}
    public float getMinY(){return minY;}
    public float getMaxY(){return maxY;}
    public float getMinZ(){return minZ;}
    public float getMaxZ(){return maxZ;}
    public void setMinX(float minX){this.minX = minX;}
    public void setMaxX(float maxX){this.maxX = maxX;}
    public void setMinY(float minY){this.minY = minY;}
    public void setMaxY(float maxY){this.maxY = maxY;}
    public void setMinZ(float minZ){this.minZ = minZ;}
    public void setMaxZ(float maxZ){this.maxZ = maxZ;}
}
