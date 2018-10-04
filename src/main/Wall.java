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
public class Wall extends genericObject
{    
    
    public Wall(float[] vertices, String name, int scale)
    {
	//Initialize using constructor in extended class genericObject
	super(vertices, name, scale);
	
	if (name.startsWith("Wall35"))
	{
	    int count = 0;
	    String point = "";
	    for (int v = 0; v < vertices.length; v++)
	    {
		count++;
		point += vertices[v] + ",";
		if (count == 3)
		{
		    System.out.println(point.substring(0, point.length()-1));
		    point = "";
		    count = 0;
		}
	    }
	    
//	    System.out.println("Point 1:   " + minX + "," + minY + "," + minZ);
//	    System.out.println("Point 2:   " + minX + "," + maxY + "," + minZ);
//	    System.out.println("Point 3:   " + minX + "," + maxY + "," + maxZ);
//	    System.out.println("Point 4:   " + minX + "," + minY + "," + maxZ);
//	    System.out.println("Point 5:   " + minX + "," + minY + "," + minZ);
//	    System.out.println("Point 6:   " + maxX + "," + minY + "," + minZ);
//	    System.out.println("Point 7:   " + maxX + "," + maxY + "," + minZ);
//	    System.out.println("Point 8:   " + maxX + "," + maxY + "," + maxZ);
//	    System.out.println("Point 9:   " + maxX + "," + minY + "," + maxZ);
//	    System.out.println("Point 10:  " + maxX + "," + minY + "," + minZ);
	}
    }
}
