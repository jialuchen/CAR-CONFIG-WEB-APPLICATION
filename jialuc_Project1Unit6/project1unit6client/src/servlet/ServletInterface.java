package servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletInterface {

	 void init(ServletConfig config);
	 void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	 void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
