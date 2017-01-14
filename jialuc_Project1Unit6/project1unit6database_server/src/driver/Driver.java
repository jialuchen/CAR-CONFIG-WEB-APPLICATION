package driver;

import java.io.IOException;

import adapter.BuildAuto;
import adapter.ConnectDBAuto;
import util.DatabaseIO;

public class Driver {
	public static void main(String[] args) throws IOException {
		

		new DatabaseIO().createDataBase();
		System.out.println();;
		ConnectDBAuto autos = new BuildAuto();
		//add model from file
		System.out.println("**************************************************************************");
		System.out.println("Add model:");
		System.out.println();
		
		autos.dbBuildAuto("testfiles/test_Air.txt");
		autos.dbBuildAuto("testfiles/test_X5.txt");
		autos.dbBuildAuto("testfiles/testFocus.Properties");
		autos.dbBuildAuto("testfiles/testS7.Properties");
		System.out.println("**************************************************************************");
		new DatabasePrinter().printTable();
		System.out.println();
		//delete auto 
		System.out.println("**************************************************************************");
		System.out.println("Delete Auto:");
		System.out.println();
		
		autos.dbDeleteAuto("Nissan Air");
		autos.dbDeleteAuto("Volvo S7");
		autos.dbDeleteAuto("BMW X5");
		//delete auto that doen't exist
		autos.dbDeleteAuto("Nissan Air");
		//print tables to see the result
		System.out.println("**************************************************************************");
		new DatabasePrinter().printTable();
		System.out.println();

		System.out.println("**************************************************************************");
		System.out.println("Update Auto:");
		System.out.println();
		//update auto base price
		autos.dbUpdateAutoBasePrice("Ford Focus", 1);
		//update auto option price
		autos.dbUpdateAutoOptionPrice("Ford Focus", "Color", "Fort Knox Gold Clearcoat Metallic", 9999);

		autos.dbUpdateAutoOptionPrice("Ford Focus", "Power Moonroof", "None",9999);
		//update auto that doesn't exist in database
		autos.dbUpdateAutoBasePrice("Nissan Air", 0);

		autos.dbUpdateAutoOptionPrice("Nissan Air", "Power Moonroof", "None",9999);
		//print all the tables
		System.out.println("**************************************************************************");
		new DatabasePrinter().printTable();
		
	}
}
