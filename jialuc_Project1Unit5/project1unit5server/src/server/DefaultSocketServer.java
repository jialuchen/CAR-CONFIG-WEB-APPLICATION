
package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

//import model.Automobile;

public class DefaultSocketServer extends Thread implements SocketServerInterface, SocketServerConstants {

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
			objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
			objectInputStream = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from Echo Port" + iPort);
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

		// allow reinput if use invalid number||realize exit
		while (true) {
			try {
				strInput = (String) objectInputStream.readObject();
			} catch (Exception e) {
				continue;
			}

			if (strInput.equals("0")) {
				break;
			} else if (strInput.equals("1") || strInput.equals("2") || strInput.equals("choosemodel")
					|| strInput.equals("configureoption")) {

			} else {
				System.out.println("Invalid number! Please input again!");
				continue;
			}

			while (true) {// reallize upload model and configuration

				//upload file
				if (strInput.equals("1")) {

					strOutput = "Please input the file number you want to upload.";
					try {
						objectOutputStream.writeObject(strOutput);
					} catch (IOException e) {
						e.getMessage().toString();
					}

					String fileName = null;
					try {
						fileName = (String) objectInputStream.readObject();
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
							System.out.println("New auto has been built!");
							objectOutputStream.writeObject(strOutput);
						} catch (IOException e) {
							e.getMessage().toString();
						}
						break;
					} else {
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
					}
				}

				else if (strInput.equals("2")) {// start 2 configuration
					strOutput = "Start configuration";
					try {
						objectOutputStream.writeObject(strOutput);
						System.out.println("Start configuration");
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
						System.out.println("Auto models have been sent!");
						// System.out.println(autoNameList.size());
					} catch (IOException e) {
						e.getMessage().toString();
					}
					if (autoNameList.size() == 0) {
						break;
					}

					String model = null;
					try {
						model = (String) objectInputStream.readObject();
						// System.out.println(model);//read ford focus from
						// client correctly
					} catch (Exception e) {
						e.getMessage().toString();
					}
					try {
						buildCarModelOptions.comSelected(objectOutputStream, model);
						// objectOutputStream.writeObject(test);//write selected
						// auto object to client
						// test.printOptionSets();
					} catch (IOException e) {
						e.printStackTrace();
					}

					strOutput = "Auto models have already been sent!";
					try {
						objectOutputStream.writeObject(strOutput);
						System.out.println("Auto models have already been sent!");
					} catch (IOException e) {
						e.getMessage().toString();
					}
					break;
				}
				// get choosemodel request from servlet
				else if (strInput.equals("choosemodel")) {
					ArrayList<String> autoNameList = buildCarModelOptions.getTotalAuto();
					try {
						objectOutputStream.writeObject(autoNameList);
						objectOutputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
					break;

				}
				// get configure model request
				else if (strInput.equals("configureoption")) {

					// else{
					String modelName = null;
					try {
						modelName = (String) objectInputStream.readObject();
						// System.out.println(modelName);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}

					// send  selected auto mobile to client
					try {
						buildCarModelOptions.comSelected(objectOutputStream, modelName);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					break;
				}
				break;
			}
		}
	}

	public void closeSession() {
		try {
			objectInputStream.close();
			objectInputStream.close();
			sock.close();

		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket");
		}
	}
}
