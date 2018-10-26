/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author j.simpson
 */
public class JobModel
{
    String jobName;
    List<Wall> walls = new ArrayList<>();
    List<Opening> windows = new ArrayList<>();
    List<Opening> doors = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    List<RoofPlane> roofPlanes = new ArrayList<>();

    JobModel(String jobName)
    {
	if (null == jobName)
	    this.jobName = "noName";
	else 
	    this.jobName = jobName;
    }
    
    void addWall(Wall wall)
    {
	if (walls.contains(wall) == false)
	    walls.add(wall);
    }
    void addWalls(List<Wall> walls)
    {
	walls.stream().forEach((wall) ->
	{
	    this.walls.add(wall);
	});
    }
    void addWindow(Opening window)
    {
	if (windows.contains(window) == false)
	    windows.add(window);
    }
    void addwindows(List<Opening> windows)
    {
	windows.stream().forEach((window) ->
	{
	    this.windows.add(window);
	});
    }
    void addDoor(Opening door)
    {
	if (doors.contains(door) == false)
	    doors.add(door);
    }
    void addDoors(List<Opening> doors)
    {
	doors.stream().forEach((door) ->
	{
	    this.doors.add(door);
	});
    }
    void addPost(Post post)
    {
	if (posts.contains(post) == false)
	    posts.add(post);
    }
    void addPost(List<Post> posts)
    {
	posts.stream().forEach((post) ->
	{
	    this.posts.add(post);
	});
    }
    void addRoofPlane(RoofPlane roofPlane)
    {
	if (roofPlanes.contains(roofPlane) == false)
	    roofPlanes.add(roofPlane);
    }
    void addRoofPlane(List<RoofPlane> roofPlanes)
    {
	roofPlanes.stream().forEach((roofPlane) ->
	{
	    this.roofPlanes.add(roofPlane);
	});
    }
}
