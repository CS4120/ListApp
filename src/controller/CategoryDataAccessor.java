package controller;

import model.Category;
import model.ToDoList;

import java.sql.*;
import java.util.List ;
import java.util.ArrayList ;

public class CategoryDataAccessor {
private Connection connection;
	
	// need to add connection pooling here instead of using Driver Manager - caches
	// connections to the db to reduce overhead of connecting to the db repeatedly
	
	public CategoryDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException{
		Class.forName(driverClassName);
		connection = DriverManager.getConnection(dbURL, user, password);
	}
	
	public void shutdown() throws SQLException {
		if (connection != null) {
			connection.close();
		}
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
	
	public List<Category> getGroupList() throws SQLException {
		try (
				Statement stmnt = connection.createStatement();
				ResultSet rs = stmnt.executeQuery("select * from Category");
		){
			List<Category> groupList = new ArrayList<>();
			while (rs.next()) {
				String name = rs.getString("GroupName");
				String color = rs.getString("ColorIndicator");
				int priority = rs.getInt("Priority");
				Category group = new Category(name, color, priority);
				groupList.add(group);
			}
			return groupList;
		}
	}
	
	// add category
	public void addCategory(String name, String color, int pri) throws SQLException{
		Category newCategory = new Category();
		
		Statement stmnt = connection.createStatement();
		int insertSuccessful = stmnt.executeUpdate("INSERT INTO Category (GroupName, ColorIndicator, Priority) " + 
				"VALUES ( '" + name + "', '" + color + "', '" + pri + "')");
		if(insertSuccessful == 1)
			System.out.println("Insert was successful");
		else 
			System.out.println("Insert failed");
	}
	
	// delete list
        public void deleteCategory(String name) throws SQLException{
                // get Id of named list
                int id = getId(name);

                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT Id FROM Category WHERE GroupName = '" + name + "'");
                // Need to add in error handling for when the Id doesn't exist
                while(rs.next()){
                        System.out.println("Id of " + name + " is " + rs.getInt("Id"));
                        id = rs.getInt("Id");
                }
                int deleteSuccessful = stmnt.executeUpdate("DELETE FROM Category WHERE Id = " + id);

                // check if delete was successful
                if (deleteSuccessful > -1)
                        System.out.println("Delete was successful");
                else 
                        System.out.println("Delete failed");
        }
		
        //filter by ListName
        public List<Category> filterByCategoryName(String catName) throws SQLException {
                try (
                                Statement stmnt = connection.createStatement();
                                ResultSet rs = stmnt.executeQuery("SELECT * FROM Category WHERE GroupName = '" + catName + "'");
                ) {
                        List<Category> filteredCategory = new ArrayList<>();
                        while (rs.next()){
                                String name = rs.getString("GroupName");
                                //System.out.println("name: " + name);
                                String color = rs.getString("ColorIndicator");
                                int pri = rs.getInt("Priority");
                                Category cat = new Category(name, color, pri);
                                filteredCategory.add(cat);
                        }
                        return filteredCategory;
                }
        }
		
        // filter by Priority
        public List<Category> filterByPriority(int priority) throws SQLException {
                try (
                                Statement stmnt = connection.createStatement();
                                ResultSet rs = stmnt.executeQuery("SELECT * FROM Category WHERE Priority = '" + priority + "'");
                ) {
                        List<Category> categoryList = new ArrayList<>();
                        while (rs.next()){
                                String name = rs.getString("GroupName");
                                //System.out.println("name: " + name);
                                String color = rs.getString("ColorIndicator");
                                int pri = rs.getInt("Priority");
                                Category category = new Category(name, color, pri);
                                categoryList.add(category);
                        }
                        return categoryList;
                }
        }
		
        public void displayFilteredCategoryName(List<Category> filteredList){
                for (int i = 0; i < filteredList.size(); i++){
                        System.out.println(filteredList.get(i).getCategoryName());
                }
        }
        
}
