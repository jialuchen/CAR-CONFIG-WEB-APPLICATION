package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import exception.AutoException;
import exception.EnumException;
import model.Automobile;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class FileIO {

	public Automobile readAuto(String filename) throws AutoException {

		Automobile auto = null;
		BufferedReader buff = null;
		float basePrice = 0;
		String autoName = "";
		String make = "";
		String model = "";
		String line = null;
		String priceRaw;
		String opsetName;
		String[] optionRead; // store options from same option set read from txt
		String[] optionRaw; // store options read from text file

		try {

			FileReader file = new FileReader(filename);
			buff = new BufferedReader(file);
			make = buff.readLine();
			model = buff.readLine();
			autoName = make + " " + model;
			priceRaw = buff.readLine();
			if (priceRaw.length() == 0) {
				AutoException e = new AutoException(EnumException.FileBasePriceNotFound);
				priceRaw = e.fix(e.getErrorNumber());
			} else {
				basePrice = Float.parseFloat(priceRaw);
			}

			auto = new Automobile(make, model, basePrice);

			while ((opsetName = buff.readLine()) != null) {
				auto.setOptionSet(opsetName);
				line = buff.readLine();
				optionRead = line.split(";");
				for (int i = 0; i < optionRead.length; i++) {
					optionRaw = optionRead[i].split(",");
					if (optionRaw.length != 2) {
						throw new AutoException(EnumException.FileOptionPriceNotFound);
					}
					auto.setOption(opsetName, optionRaw[0], Float.parseFloat(optionRaw[1]));
				}
			}
		} catch (FileNotFoundException e) {
			throw new AutoException(EnumException.FileNotFound);
		}

		catch (IOException e1) {
			System.out.println("Error4 " + e1.toString());
			System.exit(1);
		} finally {
			try {
				buff.close();
			} catch (Exception e2) {
				System.out.println("Error5 " + e2.toString());
				System.exit(1);
			}
		}
		return auto;
	}
	
	public Automobile propertyReadAuto(String filename) throws Exception{
		Properties prop = new Properties();
		Automobile auto = null;
		try {
			FileInputStream input = new FileInputStream(filename);
			prop.load(input);
			
			auto = new Automobile(prop.getProperty("CarMake"), 
					prop.getProperty("CarModel"), 
					Float.parseFloat(prop.getProperty("BasePrice")));
			
			String option = "Option";
			String optionValue = "OptionValue";
			String optionPrice = "OptionPrice";
			
			for (char optNum = '1'; prop.getProperty(option + optNum) != null; optNum++) {
				auto.setOptionSet(prop.getProperty(option + optNum));
				 						
				for (char optValueNum = 'a';prop.getProperty(optionValue + optNum + optValueNum) != null; optValueNum++) {
					auto.setOption(prop.getProperty(option + optNum), 
							prop.getProperty(optionValue + optNum + optValueNum), 
							Float.parseFloat(prop.getProperty(optionPrice + optNum + optValueNum)));
				}						 						
			}														
		} catch (IOException e){
						System.out.println("Error  " + e.toString());
		}								
		return auto;
	}

	// serialization realization
	public void serialization(Automobile auto) {

		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(new FileOutputStream("auto.ser"));
			out.writeObject(auto);
			out.close();
		} catch (Exception e1) {
			System.out.println("Error " + e1.toString());
			System.exit(1);
		} finally {
			try {
				out.close();
			} catch (IOException e2) {
				System.out.println("Error  " + e2.toString());
			}
		}

	}

	// deserialization
	public Automobile deserialization(String filename) {
		ObjectInputStream in = null;
		Automobile automotive = null;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			automotive = (Automobile) in.readObject();
		} catch (Exception e) {
			System.out.println("Error8  " + e.toString());
			System.exit(1);
		} finally {
			try {
				in.close();
			} catch (IOException e2) {
				System.out.println("Error9  " + e2.toString());
			}
		}
		return automotive;

	}
	
	public Automobile readProperty(Properties prop){
		Automobile auto = null;
		auto = new Automobile(prop.getProperty("CarMake"),prop.getProperty("CarModel"), Float.parseFloat(prop.getProperty("BasePrice")));
		String option = "Option";
		String optionValue = "OptionValue";
		String optionPrice = "OptionPrice";

		for (char optNum = '1'; prop.getProperty(option + optNum) != null; optNum++) {
			auto.setOptionSet(prop.getProperty(option + optNum));
			for (char optValueNum = 'a'; prop.getProperty(optionValue+ optNum + optValueNum) != null; optValueNum++) {
				auto.setOption(
						prop.getProperty(option + optNum),
						prop.getProperty(optionValue + optNum+ optValueNum),
						Float.parseFloat(prop.getProperty(optionPrice+ optNum + optValueNum)));
			}
		}

		return auto;
	}

}
