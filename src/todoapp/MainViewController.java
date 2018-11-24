/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todoapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import controller.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Task;

public class MainViewController implements Initializable {
    
    private static enum Filter {
        AllTask,
        Today,
        Week,
        Month,
        Complete
    }
    
    private static enum SortType {
        Date,
        Group,
        Priority
    }
    
    private static Filter currentFilter;
    private static SortType currentSort;
    
    private final int HOURS_IN_DAY = 24;
    private final int CHECKCOL = 0;
    private final int SNZCOL = 1;
    private final int DELCOL = 2;
    private final int UPCOL = 3;
    private final int COMPLETE = 1;
    private final int INCOMPLETE = 0;
   
    private List<Task> listOfTasks;
    private List<Task> cbList;
    private String categoryColor;
    private CategoryDataAccessor gda;
    private Color c;
    private TaskDataAccessor tda;
    private ScrollPane scrollPane = new ScrollPane();
    private GridPane grid = new GridPane();

    @FXML
    private VBox taskListVBox;
    @FXML
    private JFXButton addTaskButton;
    @FXML
    private JFXComboBox sortComboBox;

    @FXML
    private void sortComboAction(ActionEvent event) throws SQLException {
        if (sortComboBox.getValue() == "Date") {
            currentSort = SortType.Date;
        } else if (sortComboBox.getValue() == "Group") {
            currentSort = SortType.Group;
        } else if (sortComboBox.getValue() == "Priority") {
            currentSort = SortType.Priority;
        }
        
        refresh(currentFilter, currentSort);
    }

    @FXML
    private JFXComboBox selectTaskcb;

    @FXML
    private void taskComboAction(ActionEvent event) {
        if (null != selectTaskcb.getValue()) {
            for(Task task : listOfTasks) {
                if (selectTaskcb.getValue().equals(task.getName())) {
                    populTaskTextArea(task);
                }
            }
        }
    }

    @FXML
    private JFXComboBox groupComboBox;
    
    @FXML
    private void grpComboBoxAction(ActionEvent event){
        // get existing groups
        
        // add groups to groupComboBox
        for (Task task : listOfTasks){
            
        }
        
        // groupComboBox.getValue().equals(task.getGroup())
        
        // display all tasks for the selected group
    }

    @FXML
    private Label todayLbl;
    @FXML
    private Label weekLbl;
    @FXML
    private Label monthLbl;
    @FXML
    private Label allTasksLbl;
    @FXML
    private Label completedLbl;
    @FXML
    private Label titleLbl;
    @FXML
    private VBox leftPaneVBox;
    @FXML
    private VBox centerPaneVBox;
    @FXML
    private HBox centerPaneHBox;
    @FXML
    private VBox rightPaneVBox;
    @FXML
    private JFXTextArea taskTextArea;

    @FXML
    private void handleAddTaskBtnAction(ActionEvent event) throws IOException {
        System.out.println("Add Task Button Clicked!");
        Parent addTaskParent = FXMLLoader.load(getClass().getClassLoader().getResource("addTask/AddTask.fxml"));
        Scene addTaskScene = new Scene(addTaskParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addTaskScene);
        appStage.show();
    }

    @FXML
    private void handleAllTasksFilter(MouseEvent event) throws SQLException, IOException {
        currentFilter = Filter.AllTask;
        refresh(currentFilter, currentSort);
    }

    @FXML
    private void handleTodayFilter(MouseEvent event) throws SQLException, IOException {
        currentFilter = Filter.Today;
        refresh(currentFilter, currentSort);
    }

    @FXML
    private void handleWeekFilter(MouseEvent event) throws SQLException, IOException {
        currentFilter = Filter.Week;
        refresh(currentFilter, currentSort);
    }
    

    @FXML
    private void handleMonthFilter(MouseEvent event) throws SQLException, IOException {
        currentFilter = Filter.Week;
        refresh(currentFilter, currentSort);
    }

    @FXML
    private void handleCompleteFilter(MouseEvent event) throws SQLException, IOException {
        currentFilter = Filter.Complete;
        refresh(currentFilter, currentSort);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // initialize tda object and get a list of tasks
        try {
            tda = new TaskDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
            currentFilter = Filter.AllTask;
            currentSort = SortType.Date;
            
            refresh(currentFilter, currentSort);
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // add sortOptions to sort combo box
        sortComboBox.getItems().addAll("Date", "Group", "Priority");
    }

    private void displayListOfTasks(List<Task> listOfTasks) {
        try {
            int rowCount = 0;

            grid.setHgap(50);
            grid.setVgap(22);
            // clear all of the tasks in the VBox 
            taskListVBox.getChildren().clear();
            selectTaskcb.getItems().clear();
            taskTextArea.clear();
            grid.getChildren().clear();

            // iterate through taskList and create checkboxes
            for (Task task : listOfTasks) {
                final JFXCheckBox taskCheckBox = new JFXCheckBox(task.getName());
                final JFXButton snooze = new JFXButton("Snooze");
                //add Delete button
                final JFXButton delete = new JFXButton("Delete");

                // check if the task is completed in the database or not
                if (task.getStatus() == 0) {
                    taskCheckBox.setSelected(false);
                } else {
                    taskCheckBox.setSelected(true);
                }

                // check if the task is associated to a category and color
                categoryColor = tda.returnColorString(task.getName());
                System.out.println(categoryColor);

                // set task text to category color
                taskCheckBox.setTextFill(findColor(categoryColor));

                // checkbox event
                taskCheckBox.setOnAction(e -> {
                    try {
                        task.setStatus(task.getStatus() == INCOMPLETE ? COMPLETE : INCOMPLETE);
                        tda.updateStatus(task.getName(), task.getStatus()); 
                        snooze.setDisable(checkForSnooze(task));
                    } catch (SQLException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                // snooze button event listener
                snooze.setOnAction(e -> {
                    try {
                        task.setTaskDueDate(upCurrDateOneDay());
                        tda.updateTaskDueDate(task.getName(), task.getTaskDueDate());
                        snooze.setDisable(checkForSnooze(task));
                    } catch (SQLException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                // delete button event listener
                delete.setOnAction(e -> {
                    try {
                        tda.deleteTask(task.getName());
                        refresh(currentFilter, currentSort);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                // update button event listener
                
                
                // disable the Snooze button when the task.getTaskDueDate == null or task.getTaskDueDate > upCurrDateOneDay()
                snooze.setDisable(checkForSnooze(task));
                   
                // add tasks to select Task combo box to view details in the right pane text area
                selectTaskcb.getItems().add(task.getName());
                grid.add(taskCheckBox, CHECKCOL, rowCount);
                grid.add(snooze, SNZCOL, rowCount);
                grid.add(delete, DELCOL, rowCount);
                rowCount++;
            }
            
            taskListVBox.getChildren().add(grid);

        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Color findColor(String color) {
        if ("BLUE".equalsIgnoreCase(color)) {
            return Color.BLUE;
        } else if ("RED".equalsIgnoreCase(color)) {
            return Color.RED;
        } else if ("GREEN".equalsIgnoreCase(color)) {
            return Color.GREEN;
        } else if ("YELLOW".equalsIgnoreCase(color)) {
            return Color.YELLOW;
        } else if ("ORANGE".equalsIgnoreCase(color)) {
            return Color.ORANGE;
        } else if ("MAGENTA".equalsIgnoreCase(color)) {
            return Color.MAGENTA;
        } else {
            return Color.BLACK;
        }
    }

    private void populTaskTextArea(Task task) {
        StringBuilder sb = new StringBuilder();
        String dueDate = convertDateToString(task.getTaskDueDate());
        String category = task.getCategoryName();
        String remind = convertDateToString(task.getReminder());
        String status = convertStatus(task.getStatus());
        String priority = Integer.toString(task.getPriority());
        
        if (dueDate == null){
            dueDate = "\nDUE DATE:  No Due Date Assigned";
        }
        else {
            dueDate = ("\nDUE DATE:  " + dueDate);
        }
        
        
        if (category == null){
            category = ("\nCATEGORY:  No Category Assigned");  
        }
        else {
            category = ("\nCATEGORY:  " + category);
        }
        
        if (remind == null) {
            remind = ("\nREMINDER:  No Reminder Assigned");
        }
        else {
            remind = ("\nREMINDER:  " + remind);            
        }
        
        if (priority.equals("1")){
            priority = ("\nPRIORITY:  High");
        }
        else if (priority.equals("2")){
            priority = ("\nPRIORITY:  Medium");
        }
        else {
            priority = ("\nPRIORITY:  Low");
        }
        
        sb.append("\nNAME:  " + task.getName() + "\n");
        sb.append("\nSUMMARY:  " + task.getSummary() + "\n");
        sb.append(category + "\n");
        sb.append(priority + "\n");
        sb.append(dueDate + "\n");
        sb.append(remind + "\n");
        sb.append("\nSTATUS:  " + status + "\n");

        taskTextArea.setText(sb.toString());
    }
    
    private Date upCurrDateOneDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, HOURS_IN_DAY);
        
        return cal.getTime();
    }
    
    private String convertDateToString(Date indate){
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy");
        
        if(!(null == indate)) {
            try {
                dateString = sdfr.format(indate);
            } catch (Exception ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }

        return dateString;
    }
    
    private String convertStatus(int status){
        String stat;
        if (status == INCOMPLETE){
            stat = "Incomplete";
        }
        else {
            stat = "Complete";
        }
        return stat;
    }
    
    private GridPane removeRow(int rowCnt, GridPane grid){
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()){
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);
            
            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;
            
            if (r == rowCnt){
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }
        grid.getChildren().removeAll(deleteNodes);
        return grid;
    }
    
    private boolean checkForSnooze(Task task){
        return task.getTaskDueDate() == null || task.getStatus() == COMPLETE || task.getTaskDueDate().after(upCurrDateOneDay());
                
    }
    
    private void refresh(Filter filter, SortType sort) throws SQLException {
        switch (filter) {
            case AllTask:
                listOfTasks = tda.getTaskList();
                break;
            case Today:
                listOfTasks = tda.filterTaskDueDate(1);
                break;
            case Week:
                listOfTasks = tda.filterTaskDueDate(7);
                break;
            case Month:
                listOfTasks = tda.filterTaskDueDate(30);
                break;
            case Complete:
                listOfTasks = tda.filterCompleted();
                break;
            default:
                listOfTasks = tda.getTaskList();
                break;
        }
        
        switch(sort) {
            case Date:
                Collections.sort(listOfTasks, Task.TaskDateAscComparator);
                break;
            case Group:
                Collections.sort(listOfTasks, Task.TaskGroupAscComparator);
                break;
            case Priority:
                Collections.sort(listOfTasks, Task.TaskPriorAscComparator);
                break;
        }
          
        displayListOfTasks(listOfTasks);
    }

}
