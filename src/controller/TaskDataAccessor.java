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
				String group = rs.getString("Group");
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
	public void addTask(String name, Date create, Date due, String sum, String grp, Date remind, int pri, boolean stat) throws SQLException{
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
	
	// filter by TaskName
	
	// filter by TaskCreateDate
	
	// filter by TaskDueDate
	
	// filter by Group
	
	// filter by Priority
	
	// filter by Status
	
	// display filtered
	
}
