package controller;

import model.ToDoList;

import java.sql.*;
import java.util.List ;
import java.util.ArrayList;
import java.util.Iterator;

public class ListDataAccessor {

private Connection connection;
	
	// need to add connection pooling here instead of using Driver Manager - caches
	// connections to the db to reduce overhead of connecting to the db repeatedly
	
	public ListDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException{
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}
	
	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	// May be a better idea to write one query which selects from db one time, stores results into a list
	// and then manipulate/work with it - less overhead and may be easier to convert to java object one time
	// instead of in each function
	
	public List<ToDoList> getToDoList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM ToDoList");
		){
			List<ToDoList> toDoList = new ArrayList<>();
			while (rs.next()) {
				String name = rs.getString("ListName");
				String status = rs.getString("Status");
				ToDoList list = new ToDoList(name, status);
				toDoList.add(list);
			}
			return toDoList;
		}
	}
	
	// add new list
	public void addToDoList(String name, String status) throws SQLException{
		ToDoList newList = new ToDoList();
		
		Statement stmnt = connection.createStatement();
		int insertSuccessful = stmnt.executeUpdate("INSERT INTO ToDoList (ListName, Status) " + "VALUES ( '" + name + "', '" + status + "')");
		if(insertSuccessful == 1)
			System.out.println("Insert was successful");
		else 
			System.out.println("Insert failed");
	}
	
	// delete list
	public void deleteToDoList(String name) throws SQLException{
		// get Id of named list
		int id = -1;
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM ToDoList WHERE ListName = '" + name + "'");
		// Need to add in error handling for when the Id doesn't exist
		while(rs.next()){
			System.out.println("Id of " + name + " is " + rs.getInt("Id"));
			id = rs.getInt("Id");
		}
		int deleteSuccessful = stmnt.executeUpdate("DELETE FROM ToDoList WHERE Id = " + id);
		
		// check if delete was successful
		if (deleteSuccessful > -1)
			System.out.println("Delete was successful");
		else 
			System.out.println("Delete failed");
	}
	
	//filter by ListName
	public List<ToDoList> filterByListName(String listName) throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM ToDoList WHERE ListName = '" + listName + "'");
		) {
			List<ToDoList> filteredList = new ArrayList<>();
			while (rs.next()){
				String name = rs.getString("ListName");
				//System.out.println("name: " + name);
				String status = rs.getString("Status");
				ToDoList list = new ToDoList(name, status);
				filteredList.add(list);
			}
			return filteredList;
		}
	}
	
	//filter by Status
	public List<ToDoList> filterByListStatus(String statusToFilter) throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("SELECT * FROM ToDoList WHERE Status = '" + statusToFilter + "'");
		) {
			List<ToDoList> filteredList = new ArrayList<>();
			while (rs.next()){
				String name = rs.getString("ListName");
				//System.out.println("name: " + name);
				String status = rs.getString("Status");
				ToDoList list = new ToDoList(name, status);
				filteredList.add(list);
			}
			return filteredList;
		}
	}
	
	public void displayFilteredListName(List<ToDoList> filteredList){
		for (int i = 0; i < filteredList.size(); i++){
			System.out.println(filteredList.get(i).getListName());
		}
	}
	
	
}
