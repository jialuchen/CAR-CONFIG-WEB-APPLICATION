package model;

import java.io.Serializable;
import java.util.ArrayList;

import exception.*;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

class OptionSet implements Serializable {

	private ArrayList<Option> opt;
	private String optionSetName;
	private Option choiceOption;

	// Constructor
	protected OptionSet() {
	}

	/*
	 * Constructor
	 * 
	 * @param name, size
	 * 
	 */
	protected OptionSet(String name) {
		optionSetName = name;
		opt = new ArrayList<Option>();
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
	protected void setOptionSetName(String optsetname) {
		optionSetName = optsetname;
	}

	protected void setOption(String optionName, float price) {
		opt.add(new Option(optionName, price));
	}

	// find option by name
	protected Option findOption(String optionName) {
		for (Option op : opt) {
			if (op.getOptionName().equals(optionName)) {
				return op;
			}
		}
		return null;
	}

	// update option
	protected void updateOption(String optname, String newoptname, float price) {
		if (findOption(optname) != null) {
			findOption(optname).setOptionPrice(price);
			findOption(optname).setOptionName(newoptname);
		}
	}

	protected void updateOptionPrice(String optionName, float price) throws AutoException {
		if (findOption(optionName) == null) {
			throw new AutoException(EnumException.OptionNotFound);
		} else {
			findOption(optionName).setOptionPrice(price);
		}
	}

	// delete option
	protected void deleteOption(String optname) {
		for (Option op : opt) {
			if (op.getOptionName().equals(optname)) {
				opt.remove(op);
				return;
			}
		}
	}

	// print option
	protected void printOptions() {
		for (Option op : opt) {
			System.out.printf("Option is %s, its price is %.1f\n", op.getOptionName(), op.getOptionPrice());
		}
	}

	protected void setChoice(String optionName) {
		choiceOption = findOption(optionName);
	}

	/*
	 * getChoiceName()
	 * 
	 * get the choice name for this option set
	 */
	protected String getChoiceName() {
		return choiceOption.getOptionName();
	}

	/*
	 * setChoicePrice()
	 * 
	 * get the choice price for this option set
	 */
	protected float getChoicePrice() {
		return choiceOption.getOptionPrice();
	}

	protected class Option implements Serializable {
		private String optionName;
		private float optionPrice;

		protected Option() {
		}

		protected Option(String optname, float optprice) {
			optionName = optname;
			optionPrice = optprice;
		}

		// option name getter
		protected String getOptionName() {
			return optionName;
		}

		// option name setter
		protected void setOptionName(String optname) {
			optionName = optname;
		}

		// option price getter
		protected float getOptionPrice() {
			return optionPrice;
		}

		// option price setter
		protected void setOptionPrice(float optprice) {
			optionPrice = optprice;
		}

	}
}
