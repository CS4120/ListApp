import java.sql.SQLException;

import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.List;


public class TaskExample {

	public static void main(String[] args)  throws ClassNotFoundException, SQLException{
		TaskDataAccessor tsk = new TaskDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
		System.out.println("Tasks: " + tsk.getTaskList());

	}

}
