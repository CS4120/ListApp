package controller;
import model.Priority;

import java.sql.*;

import java.util.List ;
import java.util.ArrayList ;

public class PriorityDataAccessor {
private Connection connection;
	
	// need to add connection pooling here instead of using Driver Manager - caches
	// connections to the db to reduce overhead of connecting to the db repeatedly
	
	public PriorityDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException{
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}
	
	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	public List<Priority> getPriorityList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Priority");
		){
			List<Priority> priorityList = new ArrayList<>();
			while (rs.next()) {
				String priType = rs.getString("PriorityType");
				Priority priority = new Priority(priType);
				priorityList.add(priority);
			}
			return priorityList;
		}
	}
	
	// add other methods like add task, delete task, update task, etc.
}

