package client;

import model.Automobile;
import java.io.*;

public class SelectCarOption {
	public void configuration(Automobile selected) {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		String userChoice = null;
		
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
