package adapter;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

import util.FileIO;
import model.*;
import scale.EditOptions;
import java.util.LinkedHashMap;
import exception.AutoException;
import exception.EnumException;

public abstract class ProxyAutomobile {

	private static LinkedHashMap<String, Automobile> autoList=new LinkedHashMap<String, Automobile>();
	private static Automobile auto;
	private static int threadID = 0;

	/*
	 * buildAuto()
	 * 
	 * buildAuto using filename and save the auto info into linkedhashmap
	 * 
	 */
	public void buildAuto(String filename) {

		try {
			auto = new FileIO().buildAutoObject(filename);

			autoList.put(auto.getAutomotiveName(), auto);
		} catch (AutoException e) {
			System.out.println("Error1:  " + e.toString());
			e.fix(e.getErrorNumber());
			System.exit(1);
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