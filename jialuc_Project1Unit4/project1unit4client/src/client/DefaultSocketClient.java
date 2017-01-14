package client;

import util.CarModelOptionsIO;
import model.Automobile;
import java.net.*;
import java.util.*;
import java.io.*;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {

	private Socket sock;
	private int iPort;
	private String strHost;
	ObjectOutputStream objectOutputStream = null;
	ObjectInputStream objectInputStream = null;

	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}

	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	public void setPort(int iPort) {
		this.iPort = iPort;
	}

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}

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
				System.err.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		return true;
	}

	public void handleSession() {
		String sInput = "";
		String uInput = "";
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		if (DEBUG)
			System.out.println("Handling session with " + strHost + ":" + iPort);

		try {
			sInput = (String) objectInputStream.readObject();
			System.out.println("Server: " + sInput);
		} catch (Exception e) {
			e.getMessage().toString();
		}
		while (true) {
			displayMenu();

			try {
				uInput = stdIn.readLine();
			} catch (IOException e) {
				continue;
			}

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

			while (true) {

				if (uInput.equals("1")) {
					try {

						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					String[] fileList = displayavailableFiles();
					System.out.println("Which file you want to upload? input the number.");
					try {
						uInput = stdIn.readLine();
					} catch (IOException e) {
						e.getMessage().toString();
					}

					int fileIndex = Integer.parseInt(uInput);
					String fileName = fileList[fileIndex];

					try {
						objectOutputStream.writeObject(fileName);
						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (IOException | ClassNotFoundException e) {
						e.getMessage().toString();
					}

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

				else {
					try {

						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					ArrayList<String> autoList = null;
					try {
						autoList = (ArrayList<String>) objectInputStream.readObject();
					} catch (Exception e) {
						e.getMessage().toString();
					}

					try {
						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					if (autoList.size() == 0) {
						System.out.println("No auto list exists now. Please upload first.");
						break;
					}

					System.out.println("Model Lists :");
					for (int i = 0; i < autoList.size(); i++) {
						System.out.println("Model " + i + " : " + autoList.get(i));
					}

					System.out.println("Select the number of model you want to configure");
			/*		try {

						uInput = stdIn.readLine();
					} catch (IOException e) {
						e.getMessage().toString();
					}*/

						try {
							uInput = stdIn.readLine();
						} catch (IOException e) {
							e.getMessage().toString();
						}

					int configureAutoIndex = Integer.parseInt(uInput);
					String modelName = autoList.get(configureAutoIndex);
					//System.out.println(modelName);

					try {
						objectOutputStream.writeObject(modelName);
					} catch (IOException e) {
						e.getMessage().toString();
					}

					Automobile selected = null;
					try {
						selected = (Automobile) objectInputStream.readObject();
					} catch (Exception e) {
						e.getMessage().toString();
					}

					try {
						sInput = (String) objectInputStream.readObject();
						System.out.println("Server: " + sInput);
					} catch (Exception e) {
						e.getMessage().toString();
					}

					System.out.println("Choose your preference to configure your car.");
					new SelectCarOption().configuration(selected);
					System.out.println("Now see your choice!");
					System.out.println(selected.getAutomotiveName());
					selected.printChoice();
					System.out.println();
					System.out.println();
				}
				break;
			}
		}

	}

	public void displayMenu() {
		System.out.println("Select the number below to continue: ");
		System.out.println("1.Upload properties");
		System.out.println("2.Configure the model");
		System.out.println("0.Quit");
	}
	public String[] displayavailableFiles() {

		String[] list = new CarModelOptionsIO().getAutoFileList("AutomobileFileList.txt");
		for (int i = 0; i < list.length; i++) {
			System.out.println(i + " " + list[i]);
		}
		return list;
	}

	public void closeSession() {
		try {
			sock.close();
			objectOutputStream.close();
			objectInputStream.close();
			System.out.println("closed!");
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