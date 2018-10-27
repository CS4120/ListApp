package controller;
import model.Task;
import model.ToDoList;

import java.sql.*;
import java.util.Date;
import java.util.List ;
import java.util.ArrayList ;


public class TaskDataAccessor {
	private Connection connection;
	
	// need to add connection pooling here instead of using Driver Manager - caches
	// connections to the db to reduce overhead of connecting to the db repeatedly
	
	public TaskDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException{
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}
	
	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	public List<Task> getTaskList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Task");
		){
			List<Task> taskList = new ArrayList<>();
			while (rs.next()) {
				String taskName = rs.getString("TaskName");
				Date taskCreate = rs.getDate("TaskCreateDate");
				Date taskDue = rs.getDate("TaskDueDate");
				String summary = rs.getString("Summary");
				int group = rs.getInt("Group");
				Date reminder = rs.getDate("Reminder");
				int priority = rs.getInt("Priority");
				boolean status = rs.getBoolean("Status");
				Task task = new Task(taskName, taskCreate, taskDue, summary, group, reminder, priority, status);
				taskList.add(task);
			}
			return taskList;
		}
	}
	
	// add Task
	public void addTask(String name, Date create, Date due, String sum, int grp, Date remind, int pri, boolean stat) throws SQLException{
		Task newTask = new Task();
		
		Statement stmnt = connection.createStatement();
		int insertSuccessful = stmnt.executeUpdate("INSERT INTO Task (TaskName, TaskCreateDate, TaskDueDate, Summary, Category, Reminder, Priority, Status) " + 
				"VALUES ( '" + name + "', '" + create + "', '" + due + "', '" + sum + "', '" + grp + "', '" + remind + "', '" + pri + "', '" + stat + "')");
		if(insertSuccessful == 1)
			System.out.println("Insert was successful");
		else 
			System.out.println("Insert failed");
	}
	
	// delete Task
	public void deleteTask(String name) throws SQLException{
		// get Id of named list
		int id = -1;
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		// Need to add in error handling for when the Id doesn't exist
		while(rs.next()){
			System.out.println("Id of " + name + " is " + rs.getInt("Id"));
			id = rs.getInt("Id");
		}
		int deleteSuccessful = stmnt.executeUpdate("DELETE FROM Task WHERE Id = " + id);
		
		// check if delete was successful
		if (deleteSuccessful > -1)
			System.out.println("Delete was successful");
		else 
			System.out.println("Delete failed");
	}
	
	// filter by TaskName
	
	// filter by TaskCreateDate
	
	// filter by TaskDueDate
	
	// filter by Group
	
	// filter by Priority
	
	// filter by Status
	
	// display filtered
	
}
