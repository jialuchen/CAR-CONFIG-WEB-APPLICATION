package driver;

import adapter.BuildAuto;

public class MultipleOptionPrice {
	
	public static void main(String[] args) {
		BuildAuto autoBuilder = new BuildAuto();
		autoBuilder.buildAuto("test.txt");
		//test two threads to update option price
		autoBuilder.edit(2, "Ford Focus Wagon ZTW", "Side Impact Airbags", "None", 1);
		autoBuilder.edit(2, "Ford Focus Wagon ZTW", "Side Impact Airbags", "None", 1);

	}
}
