import java.sql.SQLException;

import model.Task;
import controller.TaskDataAccessor;

public class TaskExample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		TaskDataAccessor tsk = new TaskDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
		System.out.println("Tasks: " + tsk.getTaskList());
	
	}

}
