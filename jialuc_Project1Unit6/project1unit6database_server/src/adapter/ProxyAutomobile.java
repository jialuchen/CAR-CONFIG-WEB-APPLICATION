package adapter;

import util.DatabaseIO;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

import util.FileIO;
import model.*;
import scale.EditOptions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;

import exception.AutoException;
import exception.EnumException;

public abstract class ProxyAutomobile {

	private static LinkedHashMap<String, Automobile> autoList=new LinkedHashMap<String, Automobile>();
	private static Automobile auto;
	private static int threadID = 0;
	
		private static int autoID = 0; 
		private static int opsetID = 0; 
		private static int optID = 0; 
	

	/*
	 * buildAuto()
	 * 
	 * buildAuto using filename and save the auto info into linkedhashmap
	 * 
	 */
	public void buildAuto(String filename) {

		try {
			auto = new FileIO().readAuto(filename);

			autoList.put(auto.getAutomotiveName(), auto);
		} catch (AutoException e) {
			System.out.println("Error1:  " + e.toString());
			e.fix(e.getErrorNumber());
			System.exit(1);
		}
	}
	//add auto in database
	public void dbBuildAuto(String filename) throws IOException {
		Automobile auto = null;
		//read models from files
		try {
			if (filename.matches("[a-zA-Z0-9/]+.Properties")) {

				auto = new FileIO().propertyReadAuto(filename);
			} else {
				auto = new FileIO().readAuto(filename);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//already exists
		if (autoList.get(auto.getAutomotiveName()) != null) {
			return;
		} else {
			autoList.put(auto.getAutomotiveName(), auto);
			//record id
			int[] idMemo = new DatabaseIO().addToDatabase(auto, autoID, opsetID, optID);
			autoID++;
			opsetID = idMemo[0];
			optID = idMemo[1];
		}

	}
	//update auto base price in database
	public void dbUpdateAutoBasePrice(String autoName, float price) throws IOException {
		Automobile auto = autoList.get(autoName);
		if (auto != null) {
			auto.setBasePrice(price);
			new DatabaseIO().updateAutoBasePrice(autoName, price);
		}else{
			System.out.println("Error " + autoName + " does not exist in the system!");
		}
	}

	//update auto option price
	public void dbUpdateAutoOptionPrice(String autoName, String opsetName, String optName,
			float optPrice) throws IOException {
		Automobile auto = autoList.get(autoName);
		if (auto != null) {
			new DatabaseIO().updateAutoOptionPrice(autoName, opsetName, optName, optPrice);
		}else{
			System.out.println("Error " + autoName + " does not exist in the system!");
		}
	}
	//delete auto
	public void dbDeleteAuto(String autoName) throws IOException {
		if (autoList.get(autoName) != null) {
			autoList.remove(autoName);
			new DatabaseIO().deleteAutoInDatabase(autoName);
			//if auto name doesn't exist
		} else{
			System.out.println("Error " + autoName + "does not exist in the system!");
		}
	}
				
	/*
	 * printAuto()
	 * 
	 * print Auto info
	 * 
	 */
	public void printAuto(String modelName) {

		auto = autoList.get(modelName);
		try {
			if (auto != null)
				auto.print();
			else
				System.out.println("No such auto exists!");
		} catch (Exception e) {
			System.out.println("Error  " + e.toString());
		}
	}

	/*
	 * updateOptionSetName()
	 * 
	 * update option set name
	 * 
	 */

	public  void updateOptionSetName(String modelName, String optionSetName, String newName) {

		try {
			auto = autoList.get(modelName);
			if (auto != null) {
				auto.updateOptionSetName(optionSetName, newName);
				autoList.put(modelName, auto);
			} else {
				System.out.println("Error:  There is no auto with such name!");
			}
		} catch (AutoException e) {
			System.out.println("Error2:  " + e.toString());
		}

	}

	/*
	 * updateOptionPrice()
	 * 
	 * update option price
	 * 
	 * exception : OptionNotFound
	 */

	public void updateOptionPrice(String modelName, String Optionname, String Option, float newprice) {
		try {
			auto = autoList.get(modelName);
			if (auto != null) {
				auto.updateOptionPrice(Optionname, Option, newprice);
				autoList.put(modelName, auto);
			} else {
				System.out.println("Error:  There is no auto with such name!");
			}
		} catch (AutoException e) {
			System.out.println("Error3  " + e.toString());
		}
	}

	/*
	 * deleteAuto()
	 * 
	 * delete auto
	 * 
	 */
	public void deleteAuto(String modelName) {

		try {
			autoList.remove(modelName);
		} catch (Exception e) {
			System.out.println("Error  " + e.toString());
		}

	}
	
	/*
	 * edit()
	 * 
	 * implement abstract method edit in EditAuto
	 * thread update option price
	 */
	public void edit(int funcNum, String model, String optionSetName, String optionName, float newPrice) {
		try {
			auto = autoList.get(model);
			EditOptions edit = new EditOptions(++threadID, funcNum, auto, 
					optionSetName, optionName, newPrice);
			edit.start();
		} catch (Exception e) {
			System.out.println("Error  " + e.toString());
		}

	}
	
	/*
	 * edit()
	 * 
	 * implement abstract method edit in EditAuto
	 * thread update optionset name
	 */
	public void edit(int funcNum, String model, String optionSetName, String newOptionSetName) {
		try {
			auto = autoList.get(model);
			EditOptions edit = new EditOptions(++threadID, funcNum, auto, 
					optionSetName, newOptionSetName);
			edit.start();
		} catch (Exception e) {
			System.out.println("Error  " + e.toString());
		}

	}

	public void propertyBuildAuto(Properties prop){
		Automobile auto = new FileIO().readProperty(prop);
		autoList.put(auto.getAutomotiveName(), auto);
		//auto.printOptionSets();
		//System.out.println(auto.);
	}

	public void textBuildAuto(String fileName){
		try {
			Automobile auto = new FileIO().readAuto(fileName);
			autoList.put(auto.getAutomotiveName(), auto);
		} catch (Exception e) {
			e.getMessage().toString();
		}
	}
	
	public ArrayList<String> getTotalAuto(){
		ArrayList<String> autoNameList = new ArrayList<String>();
		for (String key: autoList.keySet()) {
			autoNameList.add(key);
			//System.out.println(key);
		}
		return autoNameList;
	}

	public void comSelected(ObjectOutputStream objectOutputStream, String model) throws IOException{
		Automobile selected = autoList.get(model);
		//System.out.println(selected.getBasePrice());
		//System.out.println(selected.getBasePrice());
		objectOutputStream.writeObject(selected);
	}
/*	public Automobile comSelected(String model) throws IOException{
		Automobile selected = autoList.get(model);
		//System.out.println(selected.getBasePrice());
		//objectOutputStream.writeObject(selected);
		//selected.printOptionSets();
		return selected;
		
	}*/

	
	/*
	 * saveAuto()
	 * 
	 * serialization
	 */
	public void saveAuto(String modelName) {
		auto = autoList.get(modelName);
		try {
			new FileIO().serialization(auto);
		} catch (Exception e) {
			System.out.println("Error -- " + e.toString());
		}
	}

	/*
	 * loadAuto()
	 * 
	 * deserialization
	 */

	public void loadAuto(String modelName) throws AutoException {

		try {
			auto = new FileIO().deserialization("auto.ser");
			autoList.put(auto.getAutomotiveName(),auto);
			auto.print();
		} catch (Exception e) {
			throw new AutoException(EnumException.FileNotFound);
		}

	}

}