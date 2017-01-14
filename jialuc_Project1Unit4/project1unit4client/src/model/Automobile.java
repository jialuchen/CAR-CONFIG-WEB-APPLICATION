package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.OptionSet.Option;
import exception.*;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class Automobile implements Serializable {

	private String make;
	private String model;
	private String automotiveName;
	private float basePrice;
	private ArrayList<OptionSet> opset;
	private ArrayList<Option> choice;

	public Automobile() {
	}

	// constructor
	public Automobile(String make, String model, float baseprice) {
		this.make = make;
		this.model = model;
		this.automotiveName = make + " " + model;
		this.opset = new ArrayList<OptionSet>();
		this.choice = new ArrayList<Option>();
		this.basePrice = baseprice;
	}

	public String getModel() {
		return model;
	}

	/*
	 * getMake()
	 * 
	 * get the make of this automotive
	 */
	public String getMake() {
		return make;
	}

	// get auto name
	public String getAutomotiveName() {
		// automotiveName=make+" "+model;
		return automotiveName;
	}

	// base price getter
	public float getBasePrice() {
		return basePrice;
	}
	
	public String getOpsetName(int i){
		return opset.get(i).getOptionSetName();
	}

	/*
	 * setMake()
	 * 
	 * set the make of this automotive
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/*
	 * setModel()
	 * 
	 * set the model of this automotive
	 */
	public void setModel(String model) {
		this.model = model;
	}

	// auto name setter
	public void setAutomotiveName(String autoname) {
		automotiveName = autoname;
	}

	// base price setter
	public void setBasePrice(float baseprice) {
		basePrice = baseprice;
	}

	// option set setter
	public void setOptionSet(String optsetname) {
		opset.add(new OptionSet(optsetname));
	}

	// option set find
	public OptionSet findOptionSet(String optsetname) {
		for (OptionSet ops : opset) {
			if (ops.getOptionSetName().equals(optsetname)) {
				return ops;
			}
		}
		return null;
	}
	
	public String getOptionSetName(String optionSetName) throws AutoException {
		if (findOptionSet(optionSetName) != null) {
			return findOptionSet(optionSetName).getOptionSetName();
		}else{
			return null;
		}
		
	}
	public int getOptionSetsSize() {
		return opset.size();
	}
	/*
	 * updateOptionSet()
	 * 
	 * update option set name, searching by name
	 */

	public void updateOptionSetName(String setName, String newSetName) throws AutoException {
		boolean flag = false;
		for (OptionSet ops : opset) {
			if (ops.getOptionSetName().equals(setName)) {
				ops.setOptionSetName(newSetName);
				return;
			}
		}
		if (!flag) {
			throw new AutoException(EnumException.OptionSetNotFound);
		}

	}

	// option set delete
	public void deleteOptionSet(String opsetname) {
		for (OptionSet ops : opset) {
			if (ops.getOptionSetName().equals(opsetname)) {
				opset.remove(ops);
				return;
			}
		}
	}

	// option set print
	public void printOptionSets() {
		for (OptionSet ops : opset) {
			System.out.println(ops.getOptionSetName() + ":");
			ops.printOptions();
			System.out.println();
		}
	}

	// option getter in Automotive class
	protected Option getOption(String opsetame, String optname) {
		if (findOptionSet(opsetame) != null) {
			return findOptionSet(opsetame).findOption(optname);
		}
		return null;
	}
	
	public float getOptionPrice(String setName, String optionName) {
		if (findOptionSet(setName) != null) {
			return getOption(setName, optionName).getOptionPrice();
		}
		return 0;
	}
	
	public int getOptionsSize(String opsetName){
		int len = findOptionSet(opsetName).getOptionsSize();
		return len;
	}

	// option setter
	public void setOption(String opsetname, String optname, float price) {
		if (findOptionSet(opsetname) != null) {
			findOptionSet(opsetname).setOption(optname, price);
		}
	}

	// option delete
	public void deleteOption(String opsetame, String optname) {
		if (findOptionSet(opsetame) != null) {
			findOptionSet(opsetame).deleteOption(optname);
		}
	}

	// option update
	public void updateOptionName(String opsetname, String optname, String newoptname, float optprice) {
		if (findOptionSet(opsetname) != null) {
			findOptionSet(opsetname).updateOption(optname, newoptname, optprice);
		}
	}

	public void updateOptionPrice(String opsetname, String optname, float optprice) throws AutoException {
		if (findOptionSet(opsetname) != null) {
			findOptionSet(opsetname).updateOptionPrice(optname, optprice);
		} else {
			throw new AutoException(EnumException.OptionSetNotFound);
		}
	}
	
	public void printOptions(String opsetName){
		findOptionSet(opsetName).printOptions();
	}

	// print auto info
	public void print() {
		System.out.printf("The model is: %s\n", automotiveName);
		System.out.println();
		System.out.printf("The base Price is: %.1f\n", basePrice);
		System.out.println();
		printOptionSets();

	}

	/* choice starts here */
	public void setOptionChoice(String opsetname, String optname) {
		OptionSet ops = findOptionSet(opsetname);
		choice.add(ops.findOption(optname));
		ops.setChoice(optname);
	}
	
	public void setOptionChoice(String opsetname, int index){
		OptionSet ops = findOptionSet(opsetname);
		choice.add(ops.findOption(index));
		ops.setChoice(index);
	}

	/*
	 * getOptionChoice()
	 * 
	 * get option choice
	 */
	public String getOptionChoice(String opsetname) {
		return findOptionSet(opsetname).getChoiceName();
	}

	/*
	 * setOptionChoicePrice()
	 * 
	 * get option choice price
	 */
	public float getOptionChoicePrice(String opsetname) {
		return findOptionSet(opsetname).getChoicePrice();
	}

	/*
	 * printChoice()
	 * 
	 * print all choice selected
	 */
	public void printChoice() {
		for (Option op : choice) {
			System.out.println("Option : " + op.getOptionName() + "Price : " + op.getOptionPrice());
		}
	}

	/*
	 * printTotalPrice()
	 * 
	 * print price for all choice selected
	 */
	public void printTotalPrice() {
		float tP = basePrice;
		for (Option op : choice) {
			tP += op.getOptionPrice();
		}
		System.out.printf("Total price : %.1f", tP);
	}
}
