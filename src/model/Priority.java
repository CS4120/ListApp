package model;

public class Priority {
	private String priorityType;
	
	public Priority(){}
	
	public Priority(String type){
		this.priorityType = type;
	}
	
	// getters and setters
	public void setPriority(String priName){
		this.priorityType = priName;
	}
	
	public String getPriority(){
		return this.priorityType;
	}
}
