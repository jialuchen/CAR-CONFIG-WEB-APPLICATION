
package server;


import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.io.IOException;


public interface AutoServer {

	public ArrayList<String> getTotalAuto();

	public void comSelected(ObjectOutputStream objectOutputStream, String model) throws IOException;	
	
	public void propertyBuildAuto(Properties prop);

	public void textBuildAuto(String fileName);


}
