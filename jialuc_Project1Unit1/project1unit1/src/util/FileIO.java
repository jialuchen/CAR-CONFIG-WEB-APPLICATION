package util;

import java.io.*;

import model.Automotive;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class FileIO{
		
	public Automotive buildAutoObject(String filename) {
		
			Automotive auto = null; 
			BufferedReader buff = null; 
			float basePrice=0;
			int optionSetSize = 0; // size of option set
			int rawOptionSize=0;//store option size found in text file
			String autoName="";
			String line=null; 
			String optionSetRaw=null;//store option set info read in text file			
			String[] optionRead; //store options from same option set read from txt
			String[] optionRaw; //store options read from text file

			try {
				
				FileReader file = new FileReader(filename);
				buff = new BufferedReader(file);
				autoName=buff.readLine();
				basePrice=Float.parseFloat(buff.readLine());
				optionSetSize=Integer.parseInt(buff.readLine());
				auto=new Automotive(optionSetSize,autoName,basePrice);
				
				for(int i=0;i<optionSetSize;i++){

					optionSetRaw = buff.readLine();
					rawOptionSize = Integer.parseInt(buff.readLine());
					auto.setOptionSet(i,optionSetRaw, rawOptionSize);

					line = buff.readLine();
					optionRead = line.split(";");
					for (int j = 0; j < optionRead.length; j++) {
						optionRaw = optionRead[j].split(",");						
						auto.setOption(i, j, optionRaw[0],
								Float.parseFloat(optionRaw[1]));						
					}					
				}						
						} catch (IOException e1) {
						System.out.println("Error "+ e1.toString());
						}finally{
							try{
								buff.close();
							}catch(IOException e2){
								System.out.println("Error "+ e2.toString());
							}
						}
				return auto;
	}

	//serialization realization
	public void serialization(Automotive auto) {
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
				System.out.println("Error  "+ e2.toString());
			}
		}
	}
	
	//deserialization
	public Automotive deserialization(String filename) {
		ObjectInputStream in = null;
		Automotive automotive=null;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			automotive = (Automotive) in.readObject();
		} catch (Exception e) {
			System.out.println("Error  " + e.toString());
			System.exit(1);
		} finally {
			try {
				in.close();
			} catch (IOException e2) {
				System.out.println("Error  "+ e2.toString());
			}
		}
		return automotive;

	}
}
