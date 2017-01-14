package driver;

import model.Automotive;
import util.FileIO;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class Driver {

	public static void main(String[] args) {
		
		FileIO util= new FileIO();
		
		//Build Automobile Object from a file.
		Automotive FordZTW = util.buildAutoObject("test.txt");
		//Print attributes before serialization
		FordZTW.print();
		//Serialize the object
		util.serialization(FordZTW);
		//Deserialize the object and read it into memory.
		Automotive newFordZTW = util.deserialization("auto.ser");
		//Print new attributes.
		newFordZTW.print();

	}

}
