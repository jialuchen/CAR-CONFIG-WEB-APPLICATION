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
	
	
	public void propertyBuildAuto(Properties prop){
		
		buildAuto.propertyBuildAuto(prop);
		
	}

	public void textBuildAuto(String fileName) {
		buildAuto.textBuildAuto(fileName);
		
	}

	public ArrayList<String> getTotalAuto(){
		ArrayList<String> autoNameList = buildAuto.getTotalAuto();
		
		return autoNameList;
	}

	public void comSelected(ObjectOutputStream objectOutputStream, String modelName) throws IOException{
		
		buildAuto.comSelected(objectOutputStream, modelName);
		//buildAuto.printAuto(modelName);
	}
}
