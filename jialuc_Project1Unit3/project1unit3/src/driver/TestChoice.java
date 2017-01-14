package driver;

import exception.AutoException;
import model.Automobile;
import util.FileIO;

public class TestChoice {
	public static void main(String arg[]) throws AutoException{
	Automobile auto = new FileIO().buildAutoObject("test.txt");
	auto.setOptionChoice("Color","Liquid Grey Clearcoat Metallic");
	auto.setOptionChoice("Transmission", "Automatic");
	auto.setOptionChoice("Brakes/Traction Control", "Standard");
	auto.setOptionChoice("Side Impact Airbags", "Selected");
	auto.setOptionChoice("Power Moonroof", "None");
	System.out.println("Base price: " + auto.getBasePrice());
	auto.printChoice();
	auto.printTotalPrice();
	}
}
