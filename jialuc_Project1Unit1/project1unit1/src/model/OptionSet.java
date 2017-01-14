package model;

import java.io.Serializable;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

class OptionSet implements Serializable {

	private Option opt[];
	private String optionSetName;

	//Constructor
	protected OptionSet() {
	}

	/*
	 * Constructor
	 * 
	 * @param name, size 
	 * 
	 */	
	protected OptionSet(String name, int size) {
		opt = new Option[size];
		optionSetName = name;
	}

	/*
	 * getOptionSetName() optionset name getter
	 * 
	 * @return String
	 */	
	protected String getOptionSetName() {
		return optionSetName;
	}

	/*
	 * setOptionSetName() optionset name setter
	 * 
	 * @param optsetname
	 */	
	protected void setOPtionSetName(String optsetname) {
		optionSetName = optsetname;
	}
	
	//option setter by option name
	protected void setOption(String optname, float price) {
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] == null) {
				opt[i] = new Option(optname, price);
				break;
			}
		}
	}

	//set option by optionindex and option name
	protected void setOption(int optindex, String optname, float price) {
		if (optindex < opt.length && optindex >= 0) {
			opt[optindex] = new Option(optname, price);
		}
	}

	//find option by name
	protected Option findOption(String optname) {
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] != null) {
				if (opt[i].getOptionName().equals(optname)) {
					return opt[i];
				}
			}
		}
		return null;
	}	

	//update option 
	protected void updateOption(String optname, String newoptname, float price) {
		if (findOption(optname) != null) {
			findOption(optname).setOptionPrice(price);
			findOption(optname).setOptionName(newoptname);
		}
	}

	//delete option
	protected void deleteOption(String optname) {
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] != null) {
				if (opt[i].getOptionName().equals(optname)) {
					opt[i] = null;
				}
			}
		}
	}

	//print option
	protected void printOptions() {
		int flag = 0;
		for (int i = 0; i < opt.length; i++) {
			if (opt[i] != null) {
				System.out.printf("Option is %s, its price is %.1f\n", 
						opt[i].getOptionName(), opt[i].getOptionPrice());
				flag = 1;
			}
		}
		if (flag == 0) {
			System.out.println("There is no option");
		}
	}

	class Option implements Serializable {
		private String optionName;
		private float optionPrice;

		protected Option() {
		}

		protected Option(String optname, float optprice) {
			optionName = optname;
			optionPrice = optprice;
		}

		//option name getter
		protected String getOptionName() {
			return optionName;
		}

		//option name setter
		protected void setOptionName(String optname) {
			optionName = optname;
		}

		//option price getter
		protected float getOptionPrice() {
			return optionPrice;
		}

		//option price setter
		protected void setOptionPrice(float optprice) {
			optionPrice = optprice;
		}

	}
}
