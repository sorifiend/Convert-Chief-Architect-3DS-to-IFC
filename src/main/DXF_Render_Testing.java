package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author j.simpson
 */
public class DXF_Render_Testing
{
    //--------- DXFwrite --------------------
// Writes all current triangle-based graphics
// (polygons, boxes, spheres, etc.) to a DXF file.
// Call this at the end of your loop (after everything has drawn).
// But be sure to put a condition like a keypress on it!
// You don't want it running over and over in a loop.
// File will appear in the same folder as the
// Processing executable.

  public static void DXFwrite(String fileName, JobModel model) {
    try {
      PrintWriter fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      DXFheader(fileWriter);
      DXFfaces(fileWriter, model);
      DXFfooter(fileWriter);
      fileWriter.close();
    }
    catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      return;
    }
  }

  static int currentDXFLayer = 0;
  //int[] DXFLayerList = new int[MAX_TRI_LAYERS];




// DXFwrite utility functions below. Do not use.
  private static void DXFheader(PrintWriter writer) {
    writer.println("0");
    writer.println("SECTION");
    writer.println("2");
    writer.println("ENTITIES");
  }

  private static void DXFfooter(PrintWriter writer) {
    writer.println("0");
    writer.println("ENDSEC");
    writer.println("0");
    writer.println("EOF");
  }

  private static void DXFfaces(PrintWriter writer, JobModel model) {
    currentDXFLayer = 0;

    //Draw entitise as 3DFaces
    for (int w = 0; w < model.walls.size(); w++) {
	DXFfaces(writer, model.walls.get(w).name, model.walls.get(w).getFaceList());
    }
    for (int w = 0; w < model.doors.size(); w++) {
	DXFfaces(writer, model.doors.get(w).name, model.doors.get(w).getFaceList());
    }
//    for (int w = 0; w < model.windows.size(); w++) {
//	DXFfaces(writer, model.windows.get(w).name, model.windows.get(w).getFaceList());
//    }
  }
    private static void DXFfaces(PrintWriter writer, String layerName, ArrayList<Face> faceList) {
    currentDXFLayer = 0;

    //Draw entitise as 3DFaces
	for (int f = 0; f < faceList.size(); f++)
	{
	//Entity type
        writer.println("0");
        writer.println("3DFACE");
	
	//Layer Name
        writer.println("8");
        writer.println(layerName);

	//First corner
        writer.println("10");
        writer.println(faceList.get(f).Point1().X());
        writer.println("20");
        writer.println(faceList.get(f).Point1().Y());
        writer.println("30");
        writer.println(faceList.get(f).Point1().Z());
	
	//Second corner
        writer.println("11");
        writer.println(faceList.get(f).Point2().X());
        writer.println("21");
        writer.println(faceList.get(f).Point2().Y());
        writer.println("31");
        writer.println(faceList.get(f).Point2().Z());
	
	//Third corner
        writer.println("12");
        writer.println(faceList.get(f).Point3().X());
        writer.println("22");
        writer.println(faceList.get(f).Point3().Y());
        writer.println("32");
        writer.println(faceList.get(f).Point3().Z());
	
	//fourth corner (Or copy of third corner)
        writer.println("13");
        writer.println(faceList.get(f).Point3().X());
        writer.println("23");
        writer.println(faceList.get(f).Point3().Y());
        writer.println("33");
        writer.println(faceList.get(f).Point3().Z());}
    }
}
