/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todoapp;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import controller.TaskDataAccessor;
import controller.GroupDataAccessor;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Task;

public class MainViewController implements Initializable {

    private List<Task> listOfTasks;
    private List<Task> cbList;
    private String categoryColor;
    private GroupDataAccessor gda;
    private Color c;

    
    @FXML
    private VBox taskListVBox;
    
    @FXML
    private JFXComboBox sortComboBox;
    
    @FXML
    private JFXComboBox groupComboBox;
    
    @FXML
    private Label todayLbl;
    
    @FXML
    private Label weekLbl;
    
    @FXML
    private Label monthLbl;
    
    @FXML
    private Label recurringLbl;
    
    @FXML
    private Label completedLbl;
    
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
              
        // instantiate TDA object and pass list of tasks
        try {
            TaskDataAccessor tda = new TaskDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
            listOfTasks = tda.getTaskList();
            
            // clear all of the tasks in the VBox 
            taskListVBox.getChildren().clear();
            
            // iterate through taskList and create checkboxes
            for (Task task: listOfTasks){
                final JFXCheckBox taskCheckBox = new JFXCheckBox(task.getName());
                
                // check if the task is completed in the database or not
                if (task.getStatus() == 0){
                    taskCheckBox.setSelected(false);
                }
                else{
                    taskCheckBox.setSelected(true);
                }
                
                // instantiate GDA object to get category
                gda = new GroupDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
                
                // check if the task is associated to a category and color
                categoryColor = gda.getGroupColor(Integer.parseInt(task.getGroup()));
                
                // set task text to category color
                taskCheckBox.setTextFill(findColor(categoryColor));
                
                // checkbox event
                taskCheckBox.setOnAction(e -> {
                    try {
                        task.setStatus(task.getStatus() == 0 ? 1 : 0);
                        tda.updateStatus(task);
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
                    
                });
                taskListVBox.getChildren().add(taskCheckBox);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
    }    
    private Color findColor(String color){
        if (color.equalsIgnoreCase("BLUE")){
            return Color.BLUE;
        }
        else if (color.equalsIgnoreCase("RED")){
            return Color.RED;
        }
        else if (color.equalsIgnoreCase("GREEN")){
            return Color.GREEN;
        }
        else if (color.equalsIgnoreCase("YELLOW")){
            return Color.YELLOW;
        }
        else if (color.equalsIgnoreCase("ORANGE")){
            return Color.ORANGE;
        }
        else if (color.equalsIgnoreCase("MAGENTA")){
            return Color.MAGENTA;
        }
        else {
            return Color.BLACK;
        }      
    }
    
    // write method to create list of tasks
}
