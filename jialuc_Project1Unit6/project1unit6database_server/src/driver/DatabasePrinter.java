package driver;

import java.io.IOException;
import java.sql.*;
import util.DatabaseIO;

public class DatabasePrinter {
	
	private static final String URL = "jdbc:mysql://localhost:3306";	
	private static final String USER_NAME = "root";
	private static final String PWD = "";
	DatabaseIO databaseIO = new DatabaseIO(); //DatabaseIO instance


	public void printTable() throws IOException {
		System.out.println("Below are all the tables in database!");
		System.out.println();

		System.out.println("Automobile Table");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
		printAutomobileTable();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println("Option Set Table");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
		printOptionSetTable();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println("Option Table");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
		printOptionTable();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
		
	}
	
	//print automobile table
	public void printAutomobileTable() throws IOException {
		Connection connection = null;
		Statement statement = null;
		String operation = null;
		ResultSet rs;
		try {

			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			//get query command from printdbcommandlists.txt
			operation = databaseIO.getCommand("testfiles/printdbcommandlists.txt", "SelectAutomobile");
			statement = connection.createStatement();
			rs = statement.executeQuery(operation);
			//print table
			System.out.format("%-5s%20s%21s\n", "AutoId", "AutoName", "BasePrice");

			while (rs.next()) {
				System.out.format("%-18s%-20s%9s\n", rs.getString("AutoId"), rs.getString("AutoName"),
						rs.getString("BasePrice"));
			}

			statement.close();
			rs.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//print option set table
	public void printOptionSetTable() throws IOException {
		Connection connection = null;
		Statement statement = null;
		String operation = null;

		ResultSet rs;
		try {
			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			statement = connection.createStatement();


			statement = connection.createStatement();
			operation = databaseIO.getCommand("testfiles/printdbcommandlists.txt", "SelectOptionSet");
			rs = statement.executeQuery(operation);

			System.out.format("%10s%20s%16s\n", "OptionSetId", "OptionSetName", "AutoId");

			while (rs.next()) {
				System.out.format("%-18s%-20s%9s\n", rs.getString("OptionSetId"), rs.getString("OptionSetName"),
						rs.getString("AutoId"));

			}

			statement.close();
			rs.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}
	//print option table
	public void printOptionTable() throws IOException {
		Connection connection = null;
		Statement statement = null;
		String operation = null;

		ResultSet rs;
		try {

			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			statement = connection.createStatement();

			statement = connection.createStatement();
			operation = databaseIO.getCommand("testfiles/printdbcommandlists.txt", "SelectAutoOption");
			rs = statement.executeQuery(operation);

			System.out.format("%-5s%20s%41s%15s%20s\n", "OptionId", "OptionName", "OptionPrice","OptionSetId","AutoID");

			while (rs.next()) {
				System.out.format("%-18s%-40s%-15s%-15s%11s\n", rs.getString("OptionId"), rs.getString("OptionName"),
						rs.getString("OptionPrice"), rs.getString("OptionSetId"), rs.getString("AutoID"));
			}

			statement.close();
			rs.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}
}
