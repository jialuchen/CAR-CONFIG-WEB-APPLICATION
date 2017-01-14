package exception;

import model.Automobile;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class Fix1to2 {
	private final static String DEFAULT_FILE = "test.txt";
	//fix file not found by replacing file not found with default file
	public String fixFileNotFound() throws AutoException {
		System.out.println("The file name doesn't exist! " + "Default file has been imported!");
		String defaultName = (DEFAULT_FILE);
		return defaultName;
	}
	//fix baseprice missed by replacing it with 0
	public String fixFileMissBasePrice() {
		System.out.println("The model base price doesn't exist! " + "Using 0 as base price now!");
		return "0";
	}

}
