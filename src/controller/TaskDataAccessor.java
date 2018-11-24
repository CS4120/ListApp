package controller;
import model.Task;
import model.ToDoList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List ;
import java.util.ArrayList ;
import model.Category;


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
				ResultSet rs = stmnt.executeQuery("select * from Task t inner join Category c on c.id = t.category");
		){
			List<Task> taskList = new ArrayList<>();
			while (rs.next()) {
				String taskName = rs.getString("TaskName");
				Date taskCreate = rs.getDate("TaskCreateDate");
				Date taskDue = rs.getDate("TaskDueDate");
				String summary = rs.getString("Summary");
				int group = rs.getInt("Category");
                                String categoryName = rs.getString("GroupName");
				Date reminder = rs.getDate("Reminder");
				int priority = rs.getInt("Priority");
				int status = rs.getInt("Status");
				Task task = new Task(taskName, taskCreate, taskDue, summary, group, reminder, priority, status, categoryName);
				taskList.add(task);
			}
			return taskList;
		}
	}
	
        public List<Category> getGroupList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Category");
		){
			List<Category> groupList = new ArrayList<>();
			while (rs.next()) {
				String groupName = rs.getString("GroupName");
				String groupColor = rs.getString("ColorIndicator");
				int groupPrior = rs.getInt("Priority");
				Category group = new Category(groupName, groupColor, groupPrior);
				groupList.add(group);
			}
			return groupList;
		}
	}
	// add Task
	public void addTask(String name, Date create, Date due, String sum, int grp, Date remind, int pri, int stat) throws SQLException{
		Task newTask = new Task();
		
		Statement stmnt = connection.createStatement();
		int insertSuccessful = stmnt.executeUpdate("INSERT INTO Task (TaskName, TaskCreateDate, TaskDueDate, Summary, Category, Reminder, Priority, Status) " + 
				"VALUES ( '" + name + "', '" + create + "', '" + due + "', '" + sum + "', '" + grp + "', '" + remind + "', '" + pri + "', '" + stat + "')");
                
                if(insertSuccessful == 1)
			System.out.println("Insert was successful");
		else 
			System.out.println("Insert failed");
                
	}
		
	public int getId(String taskName) throws SQLException{
		// get Id of named list
		int id = -1;
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + taskName + "'");
		// Need to add in error handling for when the Id doesn't exist
		while(rs.next()){
			System.out.println("Id of " + taskName + " is " + rs.getInt("Id"));
			id = rs.getInt("Id");
		}
		return id;
	}
	
	// delete Task
	public void deleteTask(String name) throws SQLException{
		int id = getId(name);
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
	
	// update statements
	public void updateTaskName(String name, String newName) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET TaskName = '" + newName + "' WHERE Id = " + id);
                
		// check if delete was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");
	}
	
	// update TaskDueDate
	// FOR ALL DATE VALUES WE NEED TO FIGURE OUT WHAT THEY WILL BE ON THE FRONT END
	// THEN WE CAN PARSE OUT STRINGS IF NECESSARY OR CONVERT DATES TO THE CORRECT FORMAT
	public void updateTaskDueDate(String name, Date date) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sqlTimestamp = sdf.format(date);
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET TaskDueDate = \'" + Timestamp.valueOf(sqlTimestamp) + "\' WHERE Id = " + id);
                
                // check if update was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");		
	}
	
	// update Summary
	public void updateSummary(String name, String summary) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET TaskDueDate = '" + summary + "' WHERE Id = " + id);
                
                // check if update was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");		
	}
	
	// update Category
	public void updateCategory(String name, int category) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET Category = " + category + " WHERE Id = " + id);

		// check if update was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");		
	}
	
	// update Priority
	public void updatePriority(String name, int priority) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET Priority = " + priority + " WHERE Id = " + id);

		// check if update was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");		
	}
	
	// update Reminder
	public void updateReminder(String name, Date remind) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET Reminder = " + remind + " WHERE Id = " + id);

		// check if update was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");		
	}
	
	public void updateStatus(String name, int stat) throws SQLException{
		// get Id of named list
		int id = getId(name);
		
		Statement stmnt = connection.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT Id FROM Task WHERE TaskName = '" + name + "'");
		
		int updateSuccessful = stmnt.executeUpdate("UPDATE Task SET Status = " + stat + " WHERE Id = " + id);

		// check if update was successful
		if (updateSuccessful > -1)
			System.out.println("Update was successful");
		else 
			System.out.println("Update failed");
	}
	
        // select statements
        public String returnTaskName(Task task){
            return task.getName();
        }
        
	// get foreign key values
        public String returnColorString(String taskName) throws SQLException{
            // get Id of named list
            int id = getId(taskName);

            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT Category FROM Task WHERE TaskName = '" + taskName + "'");
            //select colorindicator from category inner join task on task.category = category.id where task.taskname = :taskname
            
            int category = 0;
            while (rs.next()){
                category = rs.getInt("Category");
                System.out.println("Category " + category);
            }
            
            ResultSet rs2 = stmnt.executeQuery("SELECT ColorIndicator FROM Category c WHERE Id = " + category);
            
            String categoryString = "";
            while (rs2.next()){
                categoryString = rs2.getString("ColorIndicator");
            }
            return categoryString;
        }
	
	// filter by TaskCreateDate
	
	// filter by TaskDueDate
        public List<Task> filterTaskDueDate(int numDays) throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Task t inner join Category c on c.id = t.category where t.TaskDueDate <= DATE_ADD(current_date(), interval " + numDays + " day)");
		){
			List<Task> taskList = new ArrayList<>();
			while (rs.next()) {
				String taskName = rs.getString("TaskName");
				Date taskCreate = rs.getDate("TaskCreateDate");
				Date taskDue = rs.getDate("TaskDueDate");
				String summary = rs.getString("Summary");
				int group = rs.getInt("Category");
                                String categoryName = rs.getString("GroupName");
				Date reminder = rs.getDate("Reminder");
				int priority = rs.getInt("Priority");
				int status = rs.getInt("Status");
				Task task = new Task(taskName, taskCreate, taskDue, summary, group, reminder, priority, status, categoryName);
				taskList.add(task);
			}
			return taskList;
		}
	}
	
	// filter by Group
	
	// filter by Status
         public List<Task> filterCompleted() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Task t inner join Category c on c.id = t.category where t.Status = 1");
		){
			List<Task> taskList = new ArrayList<>();
			while (rs.next()) {
				String taskName = rs.getString("TaskName");
				Date taskCreate = rs.getDate("TaskCreateDate");
				Date taskDue = rs.getDate("TaskDueDate");
				String summary = rs.getString("Summary");
				int group = rs.getInt("Category");
                                String categoryName = rs.getString("GroupName");
				Date reminder = rs.getDate("Reminder");
				int priority = rs.getInt("Priority");
				int status = rs.getInt("Status");
				Task task = new Task(taskName, taskCreate, taskDue, summary, group, reminder, priority, status, categoryName);
				taskList.add(task);
			}
			return taskList;
		}
	}

	
}
