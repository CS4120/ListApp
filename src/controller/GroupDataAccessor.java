package controller;

import model.Group;

import java.sql.*;
import java.util.List ;
import java.util.ArrayList ;

public class GroupDataAccessor {
private Connection connection;
	
	// need to add connection pooling here instead of using Driver Manager - caches
	// connections to the db to reduce overhead of connecting to the db repeatedly
	
	public GroupDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException{
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}
	
	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	public List<Group> getGroupList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Category");
		){
			List<Group> groupList = new ArrayList<>();
			while (rs.next()) {
				String name = rs.getString("GroupName");
				String color = rs.getString("ColorIndicator");
				int priority = rs.getInt("Priority");
				Group group = new Group(name, color, priority);
				groupList.add(group);
			}
			return groupList;
		}
	}
	
	// add other methods like add task, delete task, update task, etc.
        public String getGroupColor(int groupID)throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select ColorIndicator from Category where Id = " + groupID);
		){
			String colorIndicator = new String();
			while (rs.next()) {
				colorIndicator = rs.getString("ColorIndicator");
			}
			return colorIndicator;
		}
	}
            
}

