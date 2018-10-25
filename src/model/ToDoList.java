package model;

public class ToDoList {
	private String listName;
	private String status;
	
	// default constructor
	public ToDoList(){}
	
	// constructor
	public ToDoList(String name, String created){
		this.listName = name;
		this.status = created;
	}
	
	public void setListName(String list){
		this.listName = list;
	}
	
	public String getListName(){
		return this.listName;
	}
	
	public void setStatus(boolean listStatus){
		if (!listStatus)
			this.status = "Complete";
		this.status = "Active";
	}
	
	public String getListStatus() {
		return this.status;
	}
	
	
	
}
