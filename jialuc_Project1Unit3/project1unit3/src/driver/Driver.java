package driver;

import adapter.BuildAuto;
import exception.AutoException;


/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class Driver {

	public static void main(String[] args) throws AutoException {

		BuildAuto autoBuilder1 = new BuildAuto();
		BuildAuto autoBuilder2 = new BuildAuto();
		autoBuilder1.buildAuto("test.txt");
		autoBuilder1.printAuto("Ford Focus Wagon ZTW");
		System.out.println("--------------------------------------");
		//different model to test the linkedhashmap
		autoBuilder2.buildAuto("test1.txt");
		autoBuilder2.printAuto("BMW X8");
		System.out.println("--------------------------------------");
		//baseprice missed, using fix method to fix it.
		autoBuilder2.buildAuto("test2.txt");
		autoBuilder2.printAuto("BMW X8");
		System.out.println("--------------------------------------");
		//test serialization and deserialization
		autoBuilder1.saveAuto("Ford Focus Wagon ZTW");
		autoBuilder1.loadAuto("Ford Focus Wagon ZTW");
		System.out.println("--------------------------------------");
		//test deleteAuto
		autoBuilder1.deleteAuto("Ford Focus Wagon ZTW");
		autoBuilder1.printAuto("Ford Focus Wagon ZTW");
		System.out.println("--------------------------------------");

	}

}
