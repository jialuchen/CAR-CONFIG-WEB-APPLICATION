
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements SocketServerConstants {
	private ServerSocket serverSocket = null;

	// constructor
	public Server() {
		try {

			serverSocket = new ServerSocket(iDAYTIME_PORT);
		} catch (IOException e) {
			e.getMessage().toString();
		}
		System.out.println("Listening on port: " + iDAYTIME_PORT);
	}

	//server behavior
	public void runServer() {
		DefaultSocketServer defaultClientSocket = null;
		try {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				defaultClientSocket = new DefaultSocketServer(clientSocket);
				defaultClientSocket.start();
			}
		} catch (IOException e) {
			e.getMessage().toString();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.runServer();
	}
}
