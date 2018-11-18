package controller;
import model.Priority;
import model.ToDoList;

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
	
	// add new priority
	public void addPriority(String priType) throws SQLException{
		ToDoList newList = new ToDoList();
		
		Statement stmnt = connection.createStatement();
		int insertSuccessful = stmnt.executeUpdate("INSERT INTO Priority (PriorityType) " + "VALUES ( '" + priType + "')");
		if(insertSuccessful == 1)
			System.out.println("Insert was successful");
		else 
			System.out.println("Insert failed");
	}
	
	// delete priority
	public void deletePriority(String name) throws SQLException{
		// get Id of named priority
		int id = -1;
			
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Priority WHERE PriorityType = '" + name + "'");
		// Need to add in error handling for when the Id doesn't exist
		while(rs.next()){
			System.out.println("Id of " + name + " is " + rs.getInt("Id"));
			id = rs.getInt("Id");
		}
		int deleteSuccessful = stmnt.executeUpdate("DELETE FROM Priority WHERE Id = " + id);
			
		// check if delete was successful
		if (deleteSuccessful > -1)
			System.out.println("Delete was successful");
		else 
			System.out.println("Delete failed");
	}
	
	//filter by PriorityType
	public List<Priority> filterByPriorityName(String priType) throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM Priority WHERE PriorityType = '" + priType + "'");
		) {
			List<Priority> filteredPriority = new ArrayList<>();
			while (rs.next()){
				String pri = rs.getString("PriorityType");
				Priority priority = new Priority(pri);
				filteredPriority.add(priority);
			}
			return filteredPriority;
		}
	}
	
	
	public void displayFilteredPriorityName(List<Priority> filteredPriority){
		for (int i = 0; i < filteredPriority.size(); i++){
			System.out.println(filteredPriority.get(i).getPriorityName());
		}
	}
}

