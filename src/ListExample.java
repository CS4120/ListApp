import java.sql.SQLException;

import model.*;
import controller.*;
import java.util.ArrayList;
import java.util.List;

public class ListExample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// See if we can't add in a dynamic endpoint here so that each developer
		// can test/use this locally rather than having to manually pass in endpoints
		
//		PriorityDataAccessor pri = new PriorityDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
//		System.out.println("Priorities: " + pri.getPriorityList());
//
//		GroupDataAccessor grp = new GroupDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
//		System.out.println("Categories: " + grp.getGroupList());

		ListDataAccessor list = new ListDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
		
		//list.addToDoList("Test2", "Active");
		//list.deleteToDoList("Test2");
		
		// filter list by name
		List<ToDoList> filtered = list.filterByListName("Test");
		
		// Display filtered by name list
		list.displayFilteredListName(filtered);
		
		// filter by status
		filtered = list.filterByListStatus("Active");
		
		// Display filtered by status list
		list.displayFilteredListName(filtered);
				
	}

}
