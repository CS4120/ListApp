package controller;

import model.ToDoList;

import java.sql.*;
import java.util.List ;
import java.util.ArrayList ;

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
	
	public List<ToDoList> getToDoList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Category");
		){
			List<ToDoList> toDoList = new ArrayList<>();
			while (rs.next()) {
				String name = rs.getString("ListName");
				String status = rs.getString("Status");
				ToDoList list = new ToDoList();
				toDoList.add(list);
			}
			return toDoList;
		}
	}
	
	// add other methods like add task, delete task, update task, etc.
}
