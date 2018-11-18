package model;

import java.util.Date;

public class Task {
	private String taskName;
	private Date taskCreateDate;
	private Date taskDueDate;
	private String summary;
	private int group ; // how do you deal with foreign keys? Inherit a group class?
	private Date reminder;
	private int priority; // another foreign key
	private int status;
	
	public Task(){}
	
	public Task(String name, Date create, Date due, String sum, int grp, Date remind, int pri, int stat){
		this.taskName = name;
		this.taskCreateDate = create;
		this.taskDueDate = due;
		this.summary = sum;
		this.group = grp;
		this.reminder = remind;
		this.priority=pri;
		this.status=stat;
	}
	
	// Individual getters and setters
	public void setName(String name){
		this.taskName = name;
	}
	
	public String getName(){
		return this.taskName;
	}
	
	public void setTaskCreateDate(Date createDate){
		this.taskCreateDate = createDate;
	}
	
	public Date getTaskCreateDate(){
		return this.taskCreateDate;
	}
	
	public void setTaskDueDate(Date dueDate){
		this.taskDueDate = dueDate;
	}
	
	public Date getTaskDueDate(){
		return this.taskDueDate;
	}
	
	public void setSummary(String sum){
		this.summary = sum;
	}
	
	public String getSummary(){
		return this.summary;
	}
	
	public void setGroup(int grp){
		this.group = grp;
	}
	
	public int getGroup(){
		return this.group;
	}
	
	public void setReminder(Date remind){
		this.reminder = remind;
	}
	
	public Date getReminder(){
		return this.reminder;
	}
	
	public void setPriority(int pri){
		this.priority = pri;
	}
	
	public int getPriority(){
		return this.priority;
	}
	
	public void setStatus(int stat){
		this.status = stat;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	
}
