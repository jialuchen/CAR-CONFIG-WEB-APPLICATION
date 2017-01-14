package client;

import model.Automobile;
import java.io.*;

public class SelectCarOption {
	//configure car 
	public void configuration(Automobile selected) {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		String userChoice = null;
	
		//print all the options and allow users to choose choice
	//	System.out.println(selected.getOptionSetsSize());
		for (int i = 0; i < selected.getOptionSetsSize(); i++) {
			String optionSetName = selected.getOpsetName(i);
			System.out.println(optionSetName);
			selected.printOptions(optionSetName);

			System.out.println("Now please select your preferred choice:"
			+ optionSetName);

			try {
				userChoice = stdIn.readLine();
			} catch (IOException e) {
				e.getMessage().toString();
			}
				
				int option = Integer.parseInt(userChoice);

			System.out.println();
			selected.setOptionChoice(optionSetName, option);

		}
	}
}
