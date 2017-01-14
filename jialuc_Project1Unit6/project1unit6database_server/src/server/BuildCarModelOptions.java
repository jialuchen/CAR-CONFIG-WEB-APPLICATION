/**
 * 
 */
package server;
import java.io.*;
import java.util.*;
import adapter.BuildAuto;

public class BuildCarModelOptions implements AutoServer{
	
	private static BuildAuto buildAuto;
	
	// constructor
	public BuildCarModelOptions(){
		buildAuto = new BuildAuto();
	}
	
	//build car by property file
	public void propertyBuildAuto(Properties prop){
		
		buildAuto.propertyBuildAuto(prop);
		
	}

	//build car based on text file
	public void textBuildAuto(String fileName) {
		buildAuto.textBuildAuto(fileName);
		
	}

	//get all the auto names
	public ArrayList<String> getTotalAuto(){
		ArrayList<String> autoNameList = buildAuto.getTotalAuto();
		
		return autoNameList;
	}

	//objectoutputstream selected auto
	public void comSelected(ObjectOutputStream objectOutputStream, String modelName) throws IOException{
		
		buildAuto.comSelected(objectOutputStream, modelName);
		//buildAuto.printAuto(modelName);
	}
/*public Automobile comSelected(String modelName) throws IOException{
		
	Automobile testauto	
	= buildAuto.comSelected(modelName);
	//testauto.printOptionSets();
	return testauto;
		 //objectOutputStream.writeObject(buildAuto.comSelected(modelName));
		//buildAuto.printAuto(modelName);
	}*/
}
