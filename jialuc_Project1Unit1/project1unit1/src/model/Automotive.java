package model;

import java.io.Serializable;
import model.OptionSet.Option;;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class Automotive implements Serializable{
	
	private String automotiveName;
	private float basePrice;
	private OptionSet opset[];
	
	public Automotive(){}
	
	//constructor
	public Automotive(int optionSetSize, String name, float baseprice)
	{
	opset = new OptionSet[optionSetSize];
	this.automotiveName = name;
	this.basePrice=baseprice;
	}
	
	//get auto name
	public String getAutomotiveName(){
	 return automotiveName;
	}
	
	//base price getter
	public float getBasePrice(){
		 return basePrice;
		}
	
	//optionset getter
	protected OptionSet getOptionSet(int optionsetindex) {
		if (optionsetindex < opset.length && optionsetindex >= 0) {
			if (opset[optionsetindex] != null) {
				return opset[optionsetindex];
			}
		}
		return null;
	}
	
	//auto name setter
	public void setAutomotiveName(String autoname){
		automotiveName=autoname;
	}
	
	//base price setter
	public void setBasePrice(float baseprice){
		basePrice=baseprice;
	}	
	
	//option set setter
	public void setOptionSet(String optsetname, int optionsize) {
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] == null) {
				opset[i] = new OptionSet(optsetname, optionsize);
				break;
			}
		}
	}
	
	//option set setter by index
	public void setOptionSet(int opsetindex, String opsetName, int optionSize) {
		opset[opsetindex] = new OptionSet(opsetName, optionSize);
	}
	
	//option set find
	protected OptionSet findOptionSet(String optsetname) {
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) {
				if (opset[i].getOptionSetName().equals(optsetname)) {
					return opset[i];
				}
			}
		}
		return null;
	}
	
	//option set update
	public void updateOptionSet(String opsetname, int opsetsize) {
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) {
				if (opset[i].getOptionSetName().equals(opsetname)) {
					opset[i] = new OptionSet(opsetname, opsetsize);
					break;
				}
			}
		}
	}
	
	//option set delete
	public void deleteOptionSet(String opsetname) {
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) {
				if (opset[i].getOptionSetName().equals(opsetname)) 
					opset[i] = null;
			}
		}
	}
		
	//option set print
	public void printOptionSets() {
		int flag=0;
		for (int i = 0; i < opset.length; i++) {
			if (opset[i] != null) {
				System.out.println(opset[i].getOptionSetName() + ":");
				opset[i].printOptions();
				System.out.println();
				flag=1;
			}
			}
	if(flag==0){
		System.out.println("There is no option set");
	}
	}
			
	//option getter in Automotive class	
	protected Option getOption(String opsetame, String optname) {
			for (int i = 0; i < opset.length; i++) {
				if (opset[i] != null) {
					if (opset[i].getOptionSetName().equals(opsetame)) {
						return opset[i].findOption(optname);
					}
				}
			}
			return null;
		}
		
	//option setter	
	public void setOption(String opsetname, String optname, float price) {
			if (findOptionSet(opsetname) != null) {
				findOptionSet(opsetname).setOption(optname, price);
			}
		}
		
	//option setter by index	
	public void setOption(int opsetindex, int optindex, String optname, float price) {
			if (getOptionSet(opsetindex) != null) {
				getOptionSet(opsetindex).setOption(optindex,optname, price);
			}
		}
		
	//option delete	
	public void deleteOption(String opsetame, String optname) {
			for (int i = 0; i < opset.length; i++) {
				if (opset[i] != null) {
					if (opset[i].getOptionSetName().equals(opsetame)) {
						opset[i].deleteOption(optname);
					}
				}
			}
		}
		
	//option update	
	public void updateOptionName(String opsetname, String optname, String newoptname, float optprice) {
			if (findOptionSet(opsetname) != null) {
				findOptionSet(opsetname).updateOption(optname, newoptname, optprice);
			}
		}
	
	//print auto info
	public void print() {
		System.out.printf("The model is: %s\n",automotiveName);
		System.out.println();
		System.out.printf("The base Price is: %.1f\n",basePrice);
		System.out.println();
		printOptionSets();

}
}



