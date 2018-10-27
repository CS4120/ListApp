import java.sql.SQLException;

import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


public class TaskExample {

	public static void main(String[] args)  throws ClassNotFoundException, SQLException, ParseException {
		TaskDataAccessor tsk = new TaskDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
		System.out.println("Tasks: " + tsk.getTaskList());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date create = sdf.parse("2018-10-27");
		java.sql.Date create2 = new java.sql.Date(create.getTime());
		
		Date due = sdf.parse("2018-10-31");
		java.sql.Date due2 = new java.sql.Date(create.getTime());
		
		Date remind = sdf.parse("2018-10-31");
		java.sql.Date remind2 = new java.sql.Date(create.getTime());

		
		//tsk.addTask("Task1", create2, due2, "A task I need to finish.", 1, remind2, 1, true);
		//task.deleteTask("Task2");

	}

}
