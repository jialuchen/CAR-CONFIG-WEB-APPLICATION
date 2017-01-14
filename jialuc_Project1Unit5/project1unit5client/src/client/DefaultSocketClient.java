package client;

import util.CarModelOptionsIO;
import model.Automobile;
import java.net.*;
import java.util.*;
import java.io.*;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {

	private Socket sock;// socket instance
	private int iPort;// port address
	private String strHost;// ip address
	ObjectOutputStream objectOutputStream = null;// output stream
	ObjectInputStream objectInputStream = null;// input stream
	private boolean ifOpen = false;// flag to show if connected

	// constructor
	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}

	// set hostaddress
	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	// port setter
	public void setPort(int iPort) {
		this.iPort = iPort;
	}

	public boolean waitOpen() {
		return this.ifOpen;
	}

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}

	// open socket
	public boolean openConnection() {
		try {
			sock = new Socket(strHost, iPort);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			return false;
		}

		try {

			objectInputStream = new ObjectInputStream(sock.getInputStream());
			objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream");
			return false;
		}
		ifOpen = true;
		return true;
	}

	//handle session
	public void handleSession() {
		String sInput = "";
		String uInput = "";
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		if (DEBUG)
			System.out.println("Handling session: " + strHost + ":" + iPort);

		//read connection info
		try {
			sInput = (String) objectInputStream.readObject();
			System.out.println("Server: " + sInput);
		} catch (Exception e) {
			e.getMessage().toString();
		}
		//allow users to reinput if the input is invalid
		while (true) {
			//show menu
			displayMenu();
			//prompt users to choose what to do next
			try {
				uInput = stdIn.readLine();
			} catch (IOException e) {
				continue;
			}
			//choose to quit
			if (uInput.equals("0")) {

				try {
					objectOutputStream.writeObject(uInput);
				} catch (IOException e) {
					e.getMessage().toString();
				}
				break;
			} else if (uInput.equals("1") || uInput.equals("2")) {

			} else {
				System.out.println("Invalid number! Please input again!");
				continue;
			}

			try {
				objectOutputStream.writeObject(uInput);
			} catch (IOException e) {
				e.getMessage().toString();
			}

			//allow users to do multiple actions
			while (true) {

				//if user chooses to upload files
				if (uInput.equals("1")) {
					try {

						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					//show all the available files
					String[] fileList = displayavailableFiles();
					// System.out.println("Which file you want to upload? input
					// the number.");
					try {
						//user chooses file
						uInput = stdIn.readLine();
					} catch (IOException e) {
						e.getMessage().toString();
					}

					int fileIndex = Integer.parseInt(uInput);
					String fileName = fileList[fileIndex];

					//send filename to server
					try {
						objectOutputStream.writeObject(fileName);
						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (IOException | ClassNotFoundException e) {
						e.getMessage().toString();
					}

					//if user chooses to upload property file
					if (fileName.matches("[a-zA-Z0-9]+.Properties")) {
						Properties prop = new Properties();

						try {
							FileInputStream fileInputStream = new FileInputStream(fileName);
							prop.load(fileInputStream);
							fileInputStream.close();
							objectOutputStream.writeObject(prop);
						} catch (IOException e) {
							e.getMessage().toString();
						}

						try {
							sInput = (String) objectInputStream.readObject();
							System.out.println("Server: " + sInput);
						} catch (Exception e) {
							e.getMessage().toString();
						}

					}

					//if user chooses to upload text file
					else {
						try {
							FileInputStream fileInputStream = new FileInputStream(new File(fileName));
							BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
									sock.getOutputStream());
							byte[] buffer = new byte[1024];
							int len = 0;

							while ((len = fileInputStream.read(buffer)) != -1) {
								bufferedOutputStream.write(buffer, 0, len);
								bufferedOutputStream.flush();
							}
							fileInputStream.close();

						} catch (IOException e) {
							e.getMessage().toString();
						}

						try {
							sInput = (String) objectInputStream.readObject();
						} catch (Exception e) {
							e.getMessage().toString();
						}
						System.out.println(sInput);

					}

				}

				//if user chooses to configure file
				else {
					try {

						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}
					// get auto name from server
					ArrayList<String> autoList = null;
					try {
						autoList = (ArrayList<String>) objectInputStream.readObject();
					} catch (Exception e) {
						e.getMessage().toString();
					}
					// automodels have been sent
					try {
						sInput = (String) objectInputStream.readObject();// been
																			// sent
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					if (autoList.size() == 0) {
						System.out.println("No auto list exists now. Please upload first.");
						break;
					}

					//show all the model lists
					System.out.println("Model Lists :");
					for (int i = 0; i < autoList.size(); i++) {
						System.out.println("Model " + i + " : " + autoList.get(i));
					}

					System.out.println("Select the number of model you want to configure");// no
																							// problem

					//choose model
					try {
						uInput = stdIn.readLine();// 0
					} catch (IOException e) {
						e.getMessage().toString();
					}

					int configureAutoIndex = Integer.parseInt(uInput);
					String modelName = autoList.get(configureAutoIndex);// get
																		// ford
																		// focus
					// System.out.println(modelName);

					try {
						objectOutputStream.writeObject(modelName);// pass ford
																	// focus
					} catch (IOException e) {
						e.getMessage().toString();
					}

					//get the auto passed by server
					Automobile selected = null;
					try {
						selected = (Automobile) objectInputStream.readObject();
						// Automobile selected = (Automobile)
						// objectInputStream.readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}

					try {
						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					System.out.println(selected.getAutomotiveName());
					System.out.println("Choose your preference to configure your car.");
					//allow users to choose their preference option
					new SelectCarOption().configuration(selected);
					//print choice
					System.out.println("Now see your choice!");
					System.out.println(selected.getAutomotiveName());
					selected.printChoice();
					selected.printTotalPrice();
					System.out.println();
					// System.out.println();
				}
				break;
			}
		}

	}

	//share output/input stream with servlet
	public ObjectOutputStream getOutputStream() {
		return this.objectOutputStream;
	}

	public ObjectInputStream getInputStream() {
		return this.objectInputStream;
	}

	//display menu
	public void displayMenu() {
		System.out.println("Select the number below to continue: ");
		System.out.println("1.Upload properties from textfile or property file");
		System.out.println("2.Configure the model");
		System.out.println("0.Quit");
	}

	//display all available files saved in filelist.txt
	public String[] displayavailableFiles() {

		String[] list = new CarModelOptionsIO().getAutoFileList("FileList.txt");
		for (int i = 0; i < list.length; i++) {
			System.out.println(i + " " + list[i]);
		}
		return list;
	}

	//close session
	public void closeSession() {
		try {
			sock.close();
			objectOutputStream.close();
			objectInputStream.close();
			System.out.println("The socket is closed now!");
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}

	public static void main(String arg[]) {
		String strLocalHost = "";
		try {
			strLocalHost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Unable to find local host");
		}

		DefaultSocketClient client = new DefaultSocketClient(strLocalHost, iDAYTIME_PORT);
		client.start();
	}
}