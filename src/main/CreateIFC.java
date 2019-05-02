/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.myke.parser.Vector;
/**
 *
 * @author j.simpson
 */
public final class CreateIFC //Vertex BD needs IFC2x3 to work correctly
{
    final String Save1_header = ""
	    + "ISO-10303-21;\n"
	    + "HEADER;\n"
	    + "FILE_DESCRIPTION(('ViewDefinition [CoordinationView_V2.0]'),'2;1');\n"
	    + "FILE_NAME('";
    String Save2_fileName = "IFC_For_Detailing";
    final String Save3_header = ""
	    + "','2019-02-27T14:48:40',('James'),(''),'Ifc2x3_Custom_TC1','Custom for Vertex BD Pro','');\n"
	    + "FILE_SCHEMA(('IFC2X3'));\n"
	    + "ENDSEC;\n"
	    + "DATA;";
    final String Save4_projectDetails = ""
	    + "#1=IFCPERSON($,'James','Simpson',$,$,$,$,$);\n"
	    + "#2=IFCORGANIZATION($,'RCDC','RCDC Ltd.',$,$);\n"
	    + "#3=IFCPERSONANDORGANIZATION(#1,#2,$);\n"
	    + "#4=IFCAPPLICATION(#2,'V1.0','3DS Converter','Custom_App');\n"
	    + "#5=IFCOWNERHISTORY(#3,#4,.READWRITE.,.NOCHANGE.,$,$,$,1000000000);\n"
	    //Job location
	    + "#6=IFCCARTESIANPOINT((0.,0.,0.));\n"
	    + "#7=IFCDIRECTION((1.,0.,0.));\n"
	    + "#8=IFCDIRECTION((0.,1.,0.));\n"
	    + "#9=IFCDIRECTION((0.,0.,1.));\n"
	    + "#10=IFCAXIS2PLACEMENT3D(#6,#9,#7);\n"
	    + "#11=IFCAXIS2PLACEMENT3D(#6,#9,#7);\n"
	    //Job properties
	    + "#12=IFCGEOMETRICREPRESENTATIONCONTEXT($,'Model',3,1.E-005,#10,$);\n"
	    + "#13=IFCGEOMETRICREPRESENTATIONSUBCONTEXT('Body','Model',*,*,*,*,#12,$,.MODEL_VIEW.,$);\n"
	    + "#14=IFCGEOMETRICREPRESENTATIONSUBCONTEXT('Axis','Model',*,*,*,*,#12,$,.GRAPH_VIEW.,$);\n"
	    + "#15=IFCSIUNIT(*,.LENGTHUNIT.,$,.METRE.);\n"
	    + "#16=IFCSIUNIT(*,.PLANEANGLEUNIT.,$,.RADIAN.);\n"
	    + "#17=IFCSIUNIT(*,.TIMEUNIT.,$,.SECOND.);\n"
	    + "#18=IFCSIUNIT(*,.THERMODYNAMICTEMPERATUREUNIT.,$,.DEGREE_CELSIUS.);\n"
	    + "#19=IFCSIUNIT(*,.LUMINOUSINTENSITYUNIT.,$,.LUMEN.);\n"
	    + "#20=IFCSIUNIT(*,.MASSUNIT.,$,.GRAM.);\n"
	    + "#21=IFCSIUNIT(*,.AREAUNIT.,$,.SQUARE_METRE.);\n"
	    + "#22=IFCSIUNIT(*,.VOLUMEUNIT.,$,.CUBIC_METRE.);\n"
	    + "#23=IFCSIUNIT(*,.LENGTHUNIT.,.MILLI.,.METRE.);\n"
	    + "#24=IFCSIUNIT(*,.MASSUNIT.,.KILO.,.GRAM.);\n"
	    + "#25=IFCDERIVEDUNITELEMENT(#15,1);\n"
	    + "#26=IFCDERIVEDUNITELEMENT(#17,-2);\n"
	    + "#27=IFCDERIVEDUNIT((#25,#26),.ACCELERATIONUNIT.,$);\n"
	    + "#28=IFCDIMENSIONALEXPONENTS(1,0,0,0,0,0,0);\n"
	    + "#29=IFCMEASUREWITHUNIT(IFCLENGTHMEASURE(0.0254),#15);\n"
	    + "#30=IFCCONVERSIONBASEDUNIT(#28,.LENGTHUNIT.,'inch',#29);\n"
	    + "#31=IFCDIMENSIONALEXPONENTS(2,0,0,0,0,0,0);\n"
	    + "#32=IFCMEASUREWITHUNIT(IFCAREAMEASURE(0.09290304000000001),#21);\n"
	    + "#33=IFCCONVERSIONBASEDUNIT(#31,.AREAUNIT.,'square foot',#32);\n"
	    + "#34=IFCDIMENSIONALEXPONENTS(3,0,0,0,0,0,0);\n"
	    + "#35=IFCMEASUREWITHUNIT(IFCVOLUMEMEASURE(0.028316846592),#22);\n"
	    + "#36=IFCCONVERSIONBASEDUNIT(#34,.VOLUMEUNIT.,'cubic foot',#35);\n"
	    + "#37=IFCDIMENSIONALEXPONENTS(0,1,0,0,0,0,0);\n"
	    + "#38=IFCMEASUREWITHUNIT(IFCMASSMEASURE(453.59237),#20);\n"
	    + "#39=IFCCONVERSIONBASEDUNIT(#37,.MASSUNIT.,'pound',#38);\n"
	    + "#40=IFCDIMENSIONALEXPONENTS(0,0,0,0,0,0,0);\n"
	    + "#41=IFCMEASUREWITHUNIT(IFCPLANEANGLEMEASURE(0.0174532925199433),#16);\n"
	    + "#42=IFCCONVERSIONBASEDUNIT(#40,.PLANEANGLEUNIT.,'degree (angle)',#41);\n"
	    + "#43=IFCUNITASSIGNMENT((#17,#18,#19,#21,#22,#23,#24,#27,#42));";
    
    //UUID should be a 22 character string
    //Use padding method to get 22 character long UUID
    int UUID = 0;
    String GetUniqueID()
    {
	return String.format("%022d", + (UUID++));
    }
    final String Save5_buildingSetup = ""
	    + "#44=IFCPROJECT('" + GetUniqueID() + "',#5,'IFC_Test',$,$,$,$,(#12),#43);\n"
	    + "#45=IFCLOCALPLACEMENT($,#11);\n"
	    + "#46=IFCSITE('" + GetUniqueID() + "',#5,'',$,$,#45,$,$,.ELEMENT.,$,$,$,$,$);\n"
	    + "#47=IFCLOCALPLACEMENT(#45,#11);\n"
	    + "#48=IFCPOSTALADDRESS($,$,$,$,('unknown'),$,'unknown',$,'unknown','unknown');\n"
	    + "#49=IFCBUILDING('" + GetUniqueID() + "',#5,'',$,$,#47,$,$,.ELEMENT.,$,$,#48);\n"
	    + "#50=IFCLOCALPLACEMENT(#47,#11);\n"
	    + "#51=IFCBUILDINGSTOREY('" + GetUniqueID() + "',#5,'Ground_Floor',$,$,#50,$,$,.ELEMENT.,0.);\n"
	    + "#52=IFCPROPERTYSINGLEVALUE('Reference','',IFCIDENTIFIER('Z-INB-GIB-24'),$);\n"
	    + "#53=IFCPROPERTYSINGLEVALUE('ThermalTransmittance','',IFCTHERMALTRANSMITTANCEMEASURE(0.),$);\n"
	    + "#54=IFCPROPERTYSINGLEVALUE('LoadBearing','',IFCBOOLEAN(.F.),$);\n"
	    + "#55=IFCPROPERTYSINGLEVALUE('IsExternal','',IFCBOOLEAN(.F.),$);";
    final String IFCLOCALPLACEMENT = "#50";
    final String IFCBUILDINGSTOREY = "#51";
    //used for last value of IFCPROPERTYSET in walls
    final String IFCPROPERTYSET = "#52,#53,#54,#55";
   
    //This part is where all the IFC content is saved
    //==========================================
    //==========================================
    //==INSERT WALLS OPENINGS AND WINDOWS HERE==
    //==========================================
    //==========================================
    
    //This goes right at the very end of the file
    final String Save6_endOfFile = ""
	    + "ENDSEC;\n"
	    + "END-ISO-10303-21;";
    
    
    //Use this line count to append the start of every line with '#x=' where x is the line count.
    //Do this for all lines except the header
    //Example #1=IFCPERSON...., #2=IFCORGANIZATION...., etc
    //Start 56 so that no conflict occurs wit the above template, and the next line will be #56
    int lineCount = 55;
    String GetLineCount()
    {
	lineCount++;
	return "#"+(lineCount);
    }
    
    public CreateIFC(String absolutePathOf3DS, String jobName, JobModel jobModel)
    {
	//set job name to be used below
	Save2_fileName = jobName;
	
	//Create array of lines, this will be used when writing to file
	List<String> lines = new ArrayList<>();
	//Add header
	lines.add(Save1_header + Save2_fileName + ".ifc" + Save3_header);
	lines.add(Save4_projectDetails);
	lines.add(Save5_buildingSetup);
	
	//Lists used to identify walls at end of job
	List<String> LIST_IFCBUILDINGELEMENTPART = new ArrayList<>();
	List<String> LIST_IFCWALL = new ArrayList<>();
	
	//add walls
	for (int i = 0; i < jobModel.walls.size(); i++)
	{
	    Wall wall = jobModel.walls.get(i);
	    System.out.println("Wall "+wall.name);
	    //Add wall
	    
	    //create referance values for start of wall
	    String ID_IFCCARTESIANPOINT_1 = GetLineCount();
	    String ID_IFCCARTESIANPOINT_2 = GetLineCount();
	    String ID_IFCPOLYLINE_1 = GetLineCount();
	    String ID_IFCSHAPEREPRESENTATION_1 = GetLineCount();
	    String ID_IFCPRODUCTDEFINITIONSHAPE_1 = GetLineCount();
	    String ID_IFCCARTESIANPOINT_3 = GetLineCount();
	    String ID_IFCDIRECTION_1 = GetLineCount();
	    String ID_IFCDIRECTION_2 = GetLineCount();
	    String ID_IFCAXIS2PLACEMENT3D_1 = GetLineCount();
	    String ID_IFCLOCALPLACEMENT_1 = GetLineCount();
	    String ID_IFCWALL = GetLineCount();
	    String ID_IFCPROPERTYSET = GetLineCount();
	    String ID_IFCRELDEFINESBYPROPERTIES = GetLineCount();
	    
	    //setup wall size in 2D, this is used to create the IFCWALL
	    lines.add(ID_IFCCARTESIANPOINT_1 + "=IFCCARTESIANPOINT((0.,0.));");
	    lines.add(ID_IFCCARTESIANPOINT_2 + "=IFCCARTESIANPOINT(("+wall.getLength()+".,0.));");
	    lines.add(ID_IFCPOLYLINE_1 + "=IFCPOLYLINE(("+ID_IFCCARTESIANPOINT_1+","+ID_IFCCARTESIANPOINT_2+"));");
	    lines.add(ID_IFCSHAPEREPRESENTATION_1 + "=IFCSHAPEREPRESENTATION(#14,'Axis','Curve2D',("+ID_IFCPOLYLINE_1+"));");
	    lines.add(ID_IFCPRODUCTDEFINITIONSHAPE_1 + "=IFCPRODUCTDEFINITIONSHAPE($,$,("+ID_IFCSHAPEREPRESENTATION_1+"));");
	    //The next five lines define the wall object placement
	    //Start location of the wall (x,y,z) from the left middle point
	    lines.add(ID_IFCCARTESIANPOINT_3 + "=IFCCARTESIANPOINT(("+wall.footPrint[0].X()+","+wall.footPrint[0].Y()+","+wall.footPrint[0].Z()+"));");
	    //This designates the wall as Z axis for extruding, and extruding is the only time that '0.,0.,1.' should be used
	    lines.add(ID_IFCDIRECTION_1 + "=IFCDIRECTION((0.,0.,1.));");
	    //Horizontal/X = 1.,0.,0. and //VerticleY= 0.,1.,0. and //Angle example= 0.63,0.77,0.
	    //The values are the Sine of the angle SIN(angle), for example 45 degrees is: 0.70710678118654752440084436210485
	    lines.add(ID_IFCDIRECTION_2 + "=IFCDIRECTION(("+wall.getAngleX()+","+wall.getAngleY()+",0.));");
	    lines.add(ID_IFCAXIS2PLACEMENT3D_1 + "=IFCAXIS2PLACEMENT3D("+ID_IFCCARTESIANPOINT_3+","+ID_IFCDIRECTION_1+","+ID_IFCDIRECTION_2+");");
	    lines.add(ID_IFCLOCALPLACEMENT_1 + "=IFCLOCALPLACEMENT("+IFCLOCALPLACEMENT+","+ID_IFCAXIS2PLACEMENT3D_1+");");
	    //Create wall object
	    lines.add(ID_IFCWALL + "=IFCWALL('" + GetUniqueID() + "',#5,'Z-INB-GIB-24',$,'WALLINT',"+ID_IFCLOCALPLACEMENT_1+","+ID_IFCPRODUCTDEFINITIONSHAPE_1+",'"+wall.name+"');");
	    lines.add(ID_IFCPROPERTYSET + "=IFCPROPERTYSET('" + GetUniqueID() + "',#5,'Pset_WallCommon',$,("+IFCPROPERTYSET+"));");
	    lines.add(ID_IFCRELDEFINESBYPROPERTIES + "=IFCRELDEFINESBYPROPERTIES('" + GetUniqueID() + "',#5,$,$,("+ID_IFCWALL+"),"+ID_IFCPROPERTYSET+");");
	    //Add wall to lists used to identify walls at end of job
	    LIST_IFCWALL.add(ID_IFCWALL);
	    
	    //create referance values for wall shape
	    lines.add(createPolyLine(wall.getFaceShape()));
	    String ID_IFCCOMPOSITECURVE_WALL = "#"+lineCount;

	    //Create wall face with openings if they exist
	    if (wall.getWindows().size() > 0)
	    {
		//Get ID of last line returned from CreatePolyLine
		
		String openingValues = "";
		for (int w = 0; w < wall.getWindows().size(); w++)
		{
		    //create referance values for wall shape
		    lines.add(createPolyLine(wall.getWindows().get(w).getFaceShape()));
		    //Get ID of last line returned from CreatePolyLine
		    if (openingValues.length() == 0)
		    {
			openingValues += "#" + lineCount;
		    }
		    else
		    {
			openingValues += "," + "#" + lineCount;
		    }
		}
		//Use IFCARBITRARYCLOSEDPROFILEDEF for blank walls or walls with doors only (voids that touch the outside), use IFCARBITRARYPROFILEDEFWITHVOIDS for walls with windows or voids
		String ID_IFCARBITRARYPROFILEDEFWITHVOIDS = GetLineCount();
		lines.add(ID_IFCARBITRARYPROFILEDEFWITHVOIDS + "=IFCARBITRARYPROFILEDEFWITHVOIDS(.AREA.,''," + ID_IFCCOMPOSITECURVE_WALL + ",(" + openingValues + "));");
	    }
	    else
	    {
		//Use IFCARBITRARYCLOSEDPROFILEDEF for blank walls or walls with doors only (voids that touch the outside), use IFCARBITRARYPROFILEDEFWITHVOIDS for walls with windows or voids
		String ID_IFCARBITRARYCLOSEDPROFILEDEF = GetLineCount();
		lines.add(ID_IFCARBITRARYCLOSEDPROFILEDEF + "=IFCARBITRARYCLOSEDPROFILEDEF(.AREA.,''," + ID_IFCCOMPOSITECURVE_WALL + ");");
	    }
	    //Get ID of last line from creating the wall faces
	    String ID_IFCARBITRARYCLOSEDPROFILEDEF_WALL = "#"+lineCount;
	    
	    //create referance values for 3D wall locations
	    String ID_IFCCARTESIANPOINT = GetLineCount();
	    ID_IFCDIRECTION_1 = GetLineCount();
	    ID_IFCDIRECTION_2 = GetLineCount();
	    ID_IFCAXIS2PLACEMENT3D_1 = GetLineCount();
	    String ID_IFCDIRECTION_3 = GetLineCount();
	    String ID_IFCEXTRUDEDAREASOLID_1 = GetLineCount();
	    
	    
	    
	    lines.add(ID_IFCCARTESIANPOINT + "=IFCCARTESIANPOINT((0.,-45.,0.));");
	    lines.add(ID_IFCDIRECTION_1 + "=IFCDIRECTION((0.,1.,0.));");
	    lines.add(ID_IFCDIRECTION_2 + "=IFCDIRECTION((1.,0.,0.));");
	    lines.add(ID_IFCAXIS2PLACEMENT3D_1 + "=IFCAXIS2PLACEMENT3D("+ID_IFCCARTESIANPOINT+","+ID_IFCDIRECTION_1+","+ID_IFCDIRECTION_2+");");
	    lines.add(ID_IFCDIRECTION_3 + "=IFCDIRECTION((0.,0.,1.));");
	    //Extrude the wall and opening face shapes to create a 3D wall
	    lines.add(ID_IFCEXTRUDEDAREASOLID_1 + "=IFCEXTRUDEDAREASOLID("+ID_IFCARBITRARYCLOSEDPROFILEDEF_WALL+","+ID_IFCAXIS2PLACEMENT3D_1+","+ID_IFCDIRECTION_3+","+wall.getThickness()+");");
	    
	    //create referance values for end of wall values
	    String ID_IFCCOLOURRGB = GetLineCount();
	    String ID_IFCSURFACESTYLERENDERING = GetLineCount();
	    String ID_IFCSURFACESTYLE = GetLineCount();
	    String ID_IFCPRESENTATIONSTYLEASSIGNMENT = GetLineCount();
	    String ID_IFCSTYLEDITEM = GetLineCount();
	    
	    String ID_IFCSHAPEREPRESENTATION = GetLineCount();
	    String ID_IFCPRODUCTDEFINITIONSHAPE = GetLineCount();
	    String ID_IFCLOCALPLACEMENT_2 = GetLineCount();
	    String ID_IFCBUILDINGELEMENTPART = GetLineCount();
	    
	    //Color for ZOG framing - Potentially useless
	    lines.add(ID_IFCCOLOURRGB + "=IFCCOLOURRGB($,0.7019607843137254,0.7019607843137254,0.7019607843137254);");
	    lines.add(ID_IFCSURFACESTYLERENDERING + "=IFCSURFACESTYLERENDERING("+ID_IFCCOLOURRGB+",0.,$,$,$,$,$,$,.PHONG.);");
	    lines.add(ID_IFCSURFACESTYLE + "=IFCSURFACESTYLE($,.BOTH.,("+ID_IFCSURFACESTYLERENDERING+"));");
	    lines.add(ID_IFCPRESENTATIONSTYLEASSIGNMENT + "=IFCPRESENTATIONSTYLEASSIGNMENT(("+ID_IFCSURFACESTYLE+"));");
	    lines.add(ID_IFCSTYLEDITEM + "=IFCSTYLEDITEM("+ID_IFCEXTRUDEDAREASOLID_1+",("+ID_IFCPRESENTATIONSTYLEASSIGNMENT+"),$);");

	    //Add end of wall data
	    lines.add(ID_IFCSHAPEREPRESENTATION + "=IFCSHAPEREPRESENTATION(#13,'Body','SweptSolid',("+ID_IFCEXTRUDEDAREASOLID_1+"));");
	    lines.add(ID_IFCPRODUCTDEFINITIONSHAPE + "=IFCPRODUCTDEFINITIONSHAPE($,$,("+ID_IFCSHAPEREPRESENTATION+"));");
	    lines.add(ID_IFCLOCALPLACEMENT_2 + "=IFCLOCALPLACEMENT("+ID_IFCLOCALPLACEMENT_1+",#11);");
	    lines.add(ID_IFCBUILDINGELEMENTPART + "=IFCBUILDINGELEMENTPART('"+GetUniqueID()+"',#5,'SFRAME-90','','FRAME',"+ID_IFCLOCALPLACEMENT_2+","+ID_IFCPRODUCTDEFINITIONSHAPE+",$);");
	    //Add wall to lists used to identify walls at end of job
	    LIST_IFCBUILDINGELEMENTPART.add(ID_IFCBUILDINGELEMENTPART);
	}
	
	//Add all walls to job
	//create referance values for end of wall values
	String ID_IFCMATERIAL = GetLineCount();
	String ID_IFCRELASSOCIATESMATERIAL = GetLineCount();
	String ID_IFCWALLTYPE = GetLineCount();
	String ID_IFCRELDEFINESBYTYPE = GetLineCount();
	
	lines.add(ID_IFCMATERIAL + "=IFCMATERIAL('SFRAME-90');");
	String walls = "";
	for (int i = 0; i < LIST_IFCWALL.size(); i++)
	{
	    if(i == 0)
		walls = LIST_IFCBUILDINGELEMENTPART.get(i);
	    else
		walls +=","+LIST_IFCBUILDINGELEMENTPART.get(i);
	}
	lines.add(ID_IFCRELASSOCIATESMATERIAL + "=IFCRELASSOCIATESMATERIAL('"+GetUniqueID()+"',#5,$,$,("+walls+"),"+ID_IFCMATERIAL+");");
	lines.add(ID_IFCWALLTYPE + "=IFCWALLTYPE('"+GetUniqueID()+"',#5,'WALLINT','Interior Wall',$,$,$,$,$,.STANDARD.);");
	
	String wallList = "";
	for (int i = 0; i < LIST_IFCWALL.size(); i++)
	{
	    if (wallList.length() == 0)
	    {
		wallList = LIST_IFCWALL.get(i);
	    }
	    else
	    {
		wallList += "," + LIST_IFCWALL.get(i);
	    }
	}
	lines.add(ID_IFCRELDEFINESBYTYPE + "=IFCRELDEFINESBYTYPE('"+GetUniqueID()+"',#5,$,$,("+wallList+"),"+ID_IFCWALLTYPE+");");
	
	//This has been put at the start of the job, but it may need to move here if it doesnt work
	lines.add(GetLineCount() + "=IFCRELAGGREGATES('" + GetUniqueID() + "',#5,'ProjectContainer','ProjectContainer for Sites',#44,(#46));");
	lines.add(GetLineCount() + "=IFCRELAGGREGATES('" + GetUniqueID() + "',#5,'SiteContainer','SiteContainer For Buildings',#46,(#49));");
	lines.add(GetLineCount() + "=IFCRELAGGREGATES('" + GetUniqueID() + "',#5,'BuildingContainer','BuildingContainer for BuildigStories',#49,(#51));");
	String ID_IFCRELCONTAINEDINSPATIALSTRUCTURE = GetLineCount();
	lines.add(ID_IFCRELCONTAINEDINSPATIALSTRUCTURE + "=IFCRELCONTAINEDINSPATIALSTRUCTURE('"+GetUniqueID()+"',#5,$,$,("+wallList+"),"+IFCBUILDINGSTOREY+");");
	
	for (int i = 0; i < LIST_IFCWALL.size(); i++)
	{
	    lines.add(GetLineCount() + "=IFCRELAGGREGATES('"+GetUniqueID()+"',#5,$,$,"+LIST_IFCWALL.get(i)+",("+LIST_IFCBUILDINGELEMENTPART.get(i)+"));");
	}
	
	//end of file
	lines.add(Save6_endOfFile);
			
	//Write lines to file
	String newFilePath = absolutePathOf3DS.substring(0, absolutePathOf3DS.lastIndexOf(File.pathSeparator)+1) + Save2_fileName + ".ifc";
	Path file = Paths.get(newFilePath);
	System.out.println("Saving IFC to: "+ newFilePath);
	try
	{
	    Files.write(file, lines, Charset.forName("UTF-8"));
	}
	catch (IOException ex)
	{
	    Logger.getLogger(CreateIFC.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private String createPolyLine(List<Vector> faceShape)
    {
	//Vector startOfWall = new Vector(0, 0, 0);
	List<String> lines = new ArrayList<>();
	String polyLineFacePoints = "";
	String firstPoint = "";
	for (int f = 0; f < faceShape.size(); f++)
	{
	    //get line ID and add it to list
	    String lineID = GetLineCount();
	    if (polyLineFacePoints.length() == 0)
	    {
		polyLineFacePoints = lineID;
		firstPoint = lineID;
	    }
	    else
	    {
		polyLineFacePoints = polyLineFacePoints + "," + lineID;
	    }
	    
	    double distangeFromStart  = Utils.distance2D(faceShape.get(0), new Vector(faceShape.get(f).X(), faceShape.get(f).Y(), 0));
	    =;
	    //Define front face of wall by X,Y points
	    //lines.add(lineID + "=IFCCARTESIANPOINT((" + faceShape.get(f).X() + "," + faceShape.get(f).Z() + "));");
	    lines.add(lineID + "=IFCCARTESIANPOINT((" + distangeFromStart + "," + faceShape.get(f).Z() + "));");
	    System.out.println("DIS: "+distangeFromStart);
	}
	polyLineFacePoints = polyLineFacePoints + "," + firstPoint;

	//create referance values for wall shape
	String ID_IFCPOLYLINE_2 = GetLineCount();
	String ID_IFCCOMPOSITECURVESEGMENT_1 = GetLineCount();
	String ID_IFCCOMPOSITECURVE_WALL = GetLineCount();

	//Create polyline of face shape
	lines.add(ID_IFCPOLYLINE_2 + "=IFCPOLYLINE((" + polyLineFacePoints + "));");
	lines.add(ID_IFCCOMPOSITECURVESEGMENT_1 + "=IFCCOMPOSITECURVESEGMENT(.CONTINUOUS.,.T.,"+ID_IFCPOLYLINE_2+");");
	lines.add(ID_IFCCOMPOSITECURVE_WALL + "=IFCCOMPOSITECURVE(("+ID_IFCCOMPOSITECURVESEGMENT_1+"),.F.);");
	
	String returnValue = "";
	for (int i = 0; i < lines.size(); i++)
	{
	    //add all lines together and add \n to end
	    if (i < lines.size()-1)
		returnValue += lines.get(i) + "\n";
	    else
		returnValue += lines.get(i);
	}
	return returnValue;
    }
}
