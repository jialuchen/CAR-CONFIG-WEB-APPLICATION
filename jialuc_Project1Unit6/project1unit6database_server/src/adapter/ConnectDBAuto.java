package adapter;

import java.io.IOException;

public interface ConnectDBAuto {

	//build auto in database
	void dbBuildAuto(String filename) throws IOException;
	//update base price in database
	void dbUpdateAutoBasePrice(String autoName, float price) throws IOException;
	//update optionprice in database
	void dbUpdateAutoOptionPrice(String autoName, String opsetName, String OptName, float optPrice) throws IOException;
	//delete auto in database
	void dbDeleteAuto(String autoName) throws IOException;

}
