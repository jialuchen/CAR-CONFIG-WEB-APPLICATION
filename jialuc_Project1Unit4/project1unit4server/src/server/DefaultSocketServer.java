
package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;


public class DefaultSocketServer extends Thread implements
		SocketServerInterface, SocketServerConstants {

	private int iPort;
	private Socket sock = null;

	private ObjectInputStream objectInputStream = null;
	private ObjectOutputStream objectOutputStream = null;

	public DefaultSocketServer(Socket sock) {
		this.sock = sock;
		this.iPort = iDAYTIME_PORT;
	}

	public void run() {
		if (openConnection()) {
			handleSession();
		}
	}

	public boolean openConnection() {
		try {
			objectOutputStream = new ObjectOutputStream(
					sock.getOutputStream());
			objectInputStream = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from Echo Port"+ iPort);
			return false;
		}
		return true;
	}


	public void handleSession() {
		String strInput;
		String strOutput;
		if (DEBUG)
			System.out.println("Handling Session in" + iPort);
		
		strOutput = "connect to " + iPort;
		try {
			objectOutputStream.writeObject(strOutput);
		} catch (IOException e) {
			e.getMessage().toString();
		}
		BuildCarModelOptions buildCarModelOptions = new BuildCarModelOptions();
		
		
		while(true){
			try {
				strInput = (String)objectInputStream.readObject();
			} catch (Exception e) {
				continue;
			}
			
			if (strInput.equals("0")){
				break;
			} else if(strInput.equals("1") || strInput.equals("2")){
				
			}else{
				System.out.println("Invalid number! Please input again!");
				continue;
			}

			while(true){
				
				if (strInput.equals("1")) {

					strOutput = "Please input the file number you want to upload.";
					try {
						objectOutputStream.writeObject(strOutput);
					} catch (IOException e) {
						e.getMessage().toString();
					}
					
					String fileName = null;
					try {
						fileName = (String)objectInputStream.readObject();
						String strout = "File received: " + fileName;
						objectOutputStream.writeObject(strout);
					} catch (ClassNotFoundException | IOException e) {
						e.getMessage().toString();
					}

					if (fileName.matches("[a-zA-Z0-9]+.Properties")) {
						Properties prop = null;
						try {
							prop = (Properties) objectInputStream.readObject();
						} catch (ClassNotFoundException | IOException e) {
							e.getMessage().toString();
						}
						buildCarModelOptions.propertyBuildAuto(prop);
						
						try {
							strOutput = "New auto has been built!";
							objectOutputStream.writeObject(strOutput);
						} catch (IOException e) {
							e.getMessage().toString();
						}											
						break;						
					} 				
					else{ 												
						try {
							File file = new File(fileName);
							FileOutputStream fileOutputStream = new FileOutputStream(file);
							BufferedInputStream bufferedInputStream = new BufferedInputStream(sock.getInputStream());							
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = bufferedInputStream.read(buffer)) > 0) {
								fileOutputStream.write(buffer, 0, len);
								fileOutputStream.flush();
								break;
							}
							fileOutputStream.close();
							
						} catch (IOException e) {
							e.getMessage().toString();
						}
						buildCarModelOptions.textBuildAuto(fileName);
						File file = new File(fileName);
						file.delete();
						strOutput = "New auto has been built!";
						try {
							objectOutputStream.writeObject(strOutput);
						} catch (IOException e) {
							e.getMessage().toString();
						}
						break;
					} 														} 
				
				else {
					strOutput = "Start configuration";
					try {
						objectOutputStream.writeObject(strOutput);
					} catch (IOException e) {
						e.getMessage().toString();
					}
					
					ArrayList<String> autoNameList = buildCarModelOptions.getTotalAuto();
					try {
						objectOutputStream.writeObject(autoNameList);
					} catch (IOException e) {
						e.getMessage().toString();
					}
					strOutput = "Auto models have been sent!";
					try {
						objectOutputStream.writeObject(strOutput);
					} catch (IOException e) {
						e.getMessage().toString();
					}
					if (autoNameList.size() == 0) {
						break;
					}
					
					String model = null;
					try {
						model = (String)objectInputStream.readObject();
					} catch (Exception e) {
						e.getMessage().toString();
					}			
					try {
						buildCarModelOptions.comSelected(objectOutputStream, model);
					} catch (IOException e) {
						e.getMessage().toString();
					}
					
					strOutput = "Auto models have been sent!";
					try {
						objectOutputStream.writeObject(strOutput);
					} catch (IOException e) {
						e.getMessage().toString();
					}
								break;
				}				
			} 
		}
	}
	public void closeSession() {
		try {
			sock.close();
			
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket");
		}
	}
}
