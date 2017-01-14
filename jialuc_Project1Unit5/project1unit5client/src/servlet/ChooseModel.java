package servlet;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.DefaultSocketClient;
import client.SocketClientConstants;
import coreservlets.ServletUtilities;

@WebServlet("/choosemodel")
public class ChooseModel extends HttpServlet implements SocketClientConstants {
	private static final long serialVersionUID = 8157650812223697264L;
	private DefaultSocketClient client;

	// override init to start the client thread
	@Override
	public void init(ServletConfig config) {
		// String hostAddress = "localhost";
		String hostAddress = "";

		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage().toString() + ": Unknown host!");
		}

		this.client = new DefaultSocketClient(hostAddress, iDAYTIME_PORT);
		client.start();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// wait until server and clients get connected
		while (!client.waitOpen()) {
		}

		// get inputstream and outputstream from default socket client
		ObjectOutputStream objectOutputStream = client.getOutputStream();
		ObjectInputStream objectInputStream = client.getInputStream();

		// show to start choosemodel
		objectOutputStream.writeObject("choosemodel");
		objectOutputStream.flush();

		// get model list from server
		ArrayList<String> modelList = null;
		try {
			modelList = (ArrayList<String>) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// thread wait
		if (modelList == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String title = "Model Lists";
		out.println(ServletUtilities.headWithTitle(title) + "<body>\n" + "<h3>"
				+ "Choose the model you want to configure!" + "</h3>\n");

		// print all the models available
		if (modelList.size() != 0) {
			out.println("<form action=\"configurationresult\" method=\"Get\">");
			out.println("<div>" + "Models:");
			out.println("<select name = \"models\">");
			for (int i = 0; i < modelList.size(); i++) {
				out.println("<option value=\"" + modelList.get(i) + "\">" + modelList.get(i) + "</option>");
			}
			out.println("</select>");
			out.println("</div><br>");
			out.println("<input type=\"submit\" value=\"Done\">");
		} else {
			out.println("No model available, please upload one model first!");
		}
		out.println("</form ></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
