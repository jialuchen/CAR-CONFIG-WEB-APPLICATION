package util;

import model.Automobile;
import java.util.LinkedHashMap;
import java.io.*;
import java.sql.*;

public class DatabaseIO {
	private static final String URL = "jdbc:mysql://localhost:3306";
	private static final String USER_NAME = "root";
	private static final String PWD = "";
	
	//get sql command from text file
		public String getCommand(String fileName, String commandNeded) throws IOException{
			BufferedReader buff = null;
			String searchLine = null;
			String command = null;
			
			FileReader file = new FileReader(fileName);
			buff = new BufferedReader(file);
			while((searchLine = buff.readLine()) != null){
			if(searchLine.equals(commandNeded)){
				command = buff.readLine();
			}
			}
			buff.close();
			file.close();
			return command;
		}

	//connect database and create new tables
	public void createDataBase() {
		Statement statement = null;
		BufferedReader br = null; 
		//connect to database
		try {
			
			Connection connection = null;
			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			if (connection != null) {
				System.out.println("Database has been connected!");
			}
			statement = connection.createStatement();
			String operation = null;
			operation = getCommand("testfiles/dbcommandlists.txt", "DeleteDataBase");
			//System.out.println(operation);
			statement.executeUpdate(operation);
			
			br = new BufferedReader(new FileReader(new File("testfiles/createdatabase.sql")));
			while ((operation = br.readLine()) != null) {
				statement.executeUpdate(operation);
			}
			
			br.close();
			connection.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//insert data into tables 
	public int[] addToDatabase(Automobile auto, int autoID, int optSetIDStart, int optionIDStart) throws IOException {
		int[] idrecorder = new int[2]; 
		int optionSetID = optSetIDStart;
		int optionID = optionIDStart;
		LinkedHashMap<String, Float> options = null;

		String[] optionSetName = { "Color", "Transmission", "ABS/Traction Control", "Side Impact Airbags",
				"Power Moonroof" };

		try {

			Connection connection = null;
			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			if (connection != null) {
				System.out.println("Database Connected!");
			}
			
			// insert auto data and form auto table
			PreparedStatement preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "AddAutomobile"));
			preparedStatement.setInt(1, autoID);
			preparedStatement.setString(2, auto.getAutomotiveName());
			preparedStatement.setFloat(3, auto.getBasePrice());
			preparedStatement.executeUpdate();

			// insert optionset data and form optionset table
			for (int i = 0; i < optionSetName.length; i++) {
				preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "AddOptionSet"));
				preparedStatement.setInt(1, optionSetID);
				preparedStatement.setString(2, optionSetName[i]);
				preparedStatement.setInt(3, autoID);
				preparedStatement.executeUpdate();
				// insert auto data and form auto table
				options = auto.getAllOptions(optionSetName[i]);
				for (String option : options.keySet()) {					
					preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "AddOption"));
					preparedStatement.setInt(1, optionID);
					preparedStatement.setString(2, option);
					preparedStatement.setFloat(3, options.get(option));
					preparedStatement.setInt(4, optionSetID);
					preparedStatement.setInt(5, autoID);
					preparedStatement.executeUpdate();					
					optionID++;
				}
				optionSetID++;
			}
			idrecorder[0] = optionSetID;
			idrecorder[1] = optionID;

			System.out.println(auto.getAutomotiveName() + " has been added into Database!");
			preparedStatement.close();
			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		return idrecorder;
	}
	//delete auto in database
	public void deleteAutoInDatabase(String AutoName) throws IOException {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			if (connection != null) {
				System.out.println("Database Connected!");
			}
			//select target auto
			PreparedStatement preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "SelectAutomobileByName"));
			preparedStatement.setString(1, AutoName);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			int AutoID = Integer.parseInt(rs.getString("AutoID"));
			//delete option
			preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "DeleteAutoOption"));
			preparedStatement.setInt(1, AutoID);
			preparedStatement.executeUpdate();
			//delete optionset
			preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "DeleteOptionSet"));
			preparedStatement.setInt(1, AutoID);
			preparedStatement.executeUpdate();
			//delete auto
			preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "DeleteAutomobile"));
			preparedStatement.setInt(1, AutoID);
			preparedStatement.executeUpdate();			
			System.out.println(AutoName + " has been deleted!");

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}
	//update base price
	public void updateAutoBasePrice(String AutoName, float newBasePrice) throws IOException {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			if (connection != null) {
				System.out.println("Database Connected!");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "UpdateAutoBasePrice"));			
			preparedStatement.setFloat(1,newBasePrice);
			preparedStatement.setString(2, AutoName);
			preparedStatement.executeUpdate();
			
			System.out.println("The base price of " + AutoName + " has been updated!");

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(AutoName + " does not exist");
		} 
	}
	//update option price
	public void updateAutoOptionPrice(String AutoName, String OptionSetName, String OptionName, float newOptionPrice) throws IOException {

		Connection connection = null;

		try {
			connection = DriverManager.getConnection(URL, USER_NAME, PWD);
			if (connection != null) {
				System.out.println("Database Connected!");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "SelectAutomobileByName"));
			preparedStatement.setString(1, AutoName);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			int AutoId = Integer.parseInt(rs.getString("AutoId"));

			preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "GetOptionSetID"));
			preparedStatement.setString(1, OptionSetName);
			preparedStatement.setInt(2, AutoId);
			rs = preparedStatement.executeQuery();
			rs.next();
			int OptionSetId = Integer.parseInt(rs.getString("OptionSetId"));

			preparedStatement = connection.prepareStatement(getCommand("testfiles/dbcommandlists.txt", "UpdateOptionPrice"));			
			preparedStatement.setFloat(1, newOptionPrice);
			preparedStatement.setInt(2, OptionSetId);
			preparedStatement.setInt(3, AutoId);
			preparedStatement.setString(4, OptionName);
			preparedStatement.executeUpdate();

			System.out.println("Update " + AutoName + " " + OptionSetName + " " + OptionName + " Successfully");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}	

}
