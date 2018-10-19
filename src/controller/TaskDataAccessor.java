package controller;
import model.Task;

import java.sql.*;

import java.util.List ;
import java.util.ArrayList ;


public class TaskDataAccessor {
	private Connection connection;
	
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
	
	// add other methods like add task, delete task, update task, etc.
	
}
