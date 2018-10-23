package model;

public class Group {
	private String groupName;
	private String colorIndicator; // may make a class color/find class color instead
	private int priority;
	
	public Group(){}
	
	public Group(String name, String color, int pri){
		this.groupName = name;
		this.colorIndicator = color;
		this.priority = pri;
	}
	
	// getters and setters
	public void setGroupName(String name){
		this.groupName = name;
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public void setColorIndicator(String color){
		this.colorIndicator = color;
	}
	
	public String getColor(){
		return this.colorIndicator;
	}
	
	public void setPriority(int pri){
		this.priority = pri;
	}
	
	public int getPriority(){
		return this.priority;
	}
	
}
