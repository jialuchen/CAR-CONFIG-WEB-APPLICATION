package driver;

import adapter.BuildAuto;

public class MultipleOptionset {
	public static void main(String[] args) {
		BuildAuto autoBuilder = new BuildAuto();
		autoBuilder.buildAuto("test.txt");
		//test two threads to update option set name
		autoBuilder.edit(1, "Ford Focus Wagon ZTW", "Transmission", "newTransmission");
		autoBuilder.edit(1, "Ford Focus Wagon ZTW", "Transmission", "newTransmission");
	}
}
