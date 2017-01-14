package servlet;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.DefaultSocketClient;
import client.SocketClientConstants;
import coreservlets.ServletUtilities;
import model.Automobile;

@WebServlet("/configurationresult")
public class ConfigureOption extends HttpServlet implements SocketClientConstants, ServletInterface {
	private static final long serialVersionUID = 8157650812223697264L;
	private String modelType;
	private DefaultSocketClient client;

	// override init to start the client thread
	@Override
	public void init(ServletConfig config) {
		String hostAddress = "";
		// get local IP address
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage().toString() + ": Unknown host!");
		}

		this.client = new DefaultSocketClient(hostAddress, iDAYTIME_PORT);
		client.start();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		modelType = request.getParameter("models");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// wait until server and clients get connected
		while (!client.waitOpen()) {
		}

		// get inputstream and outputstream from default socket client
		ObjectOutputStream objectOutputStream = client.getOutputStream();
		ObjectInputStream objectInputStream = client.getInputStream();

		// show to start configure a model
		objectOutputStream.writeObject("configureoption");
		objectOutputStream.flush();

		// send the selected model name to server
		objectOutputStream.writeObject(modelType);
		objectOutputStream.flush();

		// get the automobile instance from server
		Automobile auto = null;
		try {
			auto = (Automobile) objectInputStream.readObject();
			// System.out.println(auto.getBasePrice());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// thread wait
		if (auto == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(auto.getBasePrice());

		// session to go to showchoice page
		HttpSession session = request.getSession();
		// wait to connect
		while (session == null) {
		}

		session.setAttribute("modelName", modelType);
		session.setAttribute("basePrice", auto.getBasePrice() + "");

		// get option,price linked hash map
		LinkedHashMap<String, Float> color = auto.getAllOptions("Color");
		LinkedHashMap<String, Float> tranmmission = auto.getAllOptions("Transmission");
		LinkedHashMap<String, Float> abs = auto.getAllOptions("Brakes/Traction Control");
		LinkedHashMap<String, Float> airbag = auto.getAllOptions("Side Impact Airbags");
		LinkedHashMap<String, Float> moonroof = auto.getAllOptions("Power Moonroof");

		// save optionsets html
		String colorHtml = "<select name=color>" + "<optgroup label=\"select color\">";
		for (String s : color.keySet()) {
			colorHtml += "<option value=\"" + s + "=" + color.get(s) + "\">" + s + "</option>";
		}
		colorHtml += "</optgroup>";

		String transmissionHtml = "<select name=transmission>" + "<optgroup label=\"select transmission\">";
		for (String s : tranmmission.keySet()) {
			transmissionHtml += "<option value=\"" + s + "=" + tranmmission.get(s) + "\">" + s + "</option>";
		}
		transmissionHtml += "</optgroup>";

		String tractionHtml = "<select name=bracket>" + "<optgroup label=\"select abs\">";
		for (String s : abs.keySet()) {
			tractionHtml += "<option value=\"" + s + "=" + abs.get(s) + "\">" + s + "</option>";
		}
		tractionHtml += "</optgroup>";

		String airbagHtml = "<select name=airbag>" + "<optgroup label=\"select airbag\">";
		for (String s : airbag.keySet()) {
			airbagHtml += "<option value=\"" + s + "=" + airbag.get(s) + "\">" + s + "</option>";
		}
		airbagHtml += "</optgroup>";

		String moonroofHtml = "<select name=moonroof>" + "<optgroup label=\"select moonroof\">";
		for (String s : moonroof.keySet()) {
			moonroofHtml += "<option value=\"" + s + "=" + moonroof.get(s) + "\">" + s + "</option>";
		}
		moonroofHtml += "</optgroup>";

		// print html
		String title = "Basic Car Choice";
		out.println(ServletUtilities.headWithTitle(title) + "<body>\n" + "<h1>" + title + "</h1>\n"
				+ "<form action=\"ShowChoice.jsp\" method=\"Get\">" + "<table border=1>\n");

		out.println("<tr><td align=\"center\">" + "Make/Model:" + "<td>" + modelType + "</tr>");
		out.println("<tr><td align=\"center\">" + "Color:" + "<td>" + colorHtml + "</tr>");
		out.println("<tr><td align=\"center\">" + "Transmission:" + "<td>" + transmissionHtml + "</tr>");
		out.println("<tr><td align=\"center\">" + "Bracket/Traction Control:" + "<td>" + tractionHtml + "</tr>");
		out.println("<tr><td align=\"center\">" + "Side Impact Air Bags:" + "<td>" + airbagHtml + "</tr>");
		out.println("<tr><td align=\"center\">" + "Power Moonroof:" + "<td>" + moonroofHtml + "</tr>");

		out.println("<tr><td> <td> <input type=\"submit\" align=\"right\" value=\"Done\">");
		out.println("</table>");
		out.println("</form ></body></html>");

	}

	/** POST and GET requests handled identically. */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}