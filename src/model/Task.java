package model;

import java.util.Comparator;
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
        private String categoryName;
	
	public Task(){}
	
	public Task(String name, Date create, Date due, String sum, int grp, Date remind, int pri, int stat, String categoryName){
		this.taskName = name;
		this.taskCreateDate = create;
		this.taskDueDate = due;
		this.summary = sum;
		this.group = grp;
		this.reminder = remind;
		this.priority=pri;
		this.status=stat;
                this.categoryName=categoryName;
	}
        
        public Task(String name){
            this.taskName = name;
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
        
        
        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
	
        static final String FUTURE_DATE = "01/01/3000";
        
        public static Comparator<Task> TaskDateAscComparator = (Task t1, Task t2) -> {
            
            Date t1Date = t1.getTaskDueDate();
            Date t2Date = t2.getTaskDueDate();
            
            t1Date = ((null==t1Date) ? new Date(FUTURE_DATE):t1Date);
            t2Date = ((null==t2Date) ? new Date(FUTURE_DATE):t2Date);
            
            if(t1Date.before(t2Date)) {
                return -1;
            } else if(t1Date.after(t2Date)) {
                return 1;
            } else {
                return 0;
            }
        };
        
        public static Comparator<Task> TaskDateDescComparator = (Task t1, Task t2) -> {
           
            Date t1Date = t1.getTaskDueDate();
            Date t2Date = t2.getTaskDueDate();
            
            t1Date = ((null==t1Date) ? new Date(FUTURE_DATE):t1Date);
            t2Date = ((null==t2Date) ? new Date(FUTURE_DATE):t2Date);
            
            if(t2Date.before(t1Date)) {
                return -1;
            } else if(t2Date.after(t1Date)) {
                return 1;
            } else {
                return 0;
            }
        };
        
        public static Comparator<Task> TaskGroupAscComparator = (Task t1, Task t2) -> {
            
            Integer t1Grp = t1.getGroup();
            Integer t2Grp = t2.getGroup();
            
            return t1Grp.compareTo(t2Grp);
        };
        
        public static Comparator<Task> TaskGroupDescComparator = (Task t1, Task t2) -> {
           
            Integer t1Grp = t1.getGroup();
            Integer t2Grp = t2.getGroup();
            
            return t2Grp.compareTo(t1Grp);
        };
        
        public static Comparator<Task> TaskPriorAscComparator = (Task t1, Task t2) -> {
            
            Integer t1Prior = t1.getPriority();
            Integer t2Prior = t2.getPriority();
            
            return t1Prior.compareTo(t2Prior);
        };
        
        public static Comparator<Task> TaskPriorDescComparator = (Task t1, Task t2) -> {
           
            Integer t1Prior = t1.getPriority();
            Integer t2Prior = t2.getPriority();
            
            return t2Prior.compareTo(t1Prior);
        };
	
}
