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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Task;

public class MainViewController implements Initializable {

    private List<Task> listOfTasks;
    private List<Task> cbList;
    private String categoryColor;
    private CategoryDataAccessor gda;
    private Color c;

    
    @FXML
    private VBox taskListVBox;
    @FXML
    private JFXButton addTaskButton;
    @FXML
    private JFXComboBox sortComboBox;
    @FXML
    private JFXComboBox selectTaskcb;
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
    private void handleAddTaskBtnAction(ActionEvent event) throws IOException{
        System.out.println("Add Task Button Clicked!");
        Parent addTaskParent = FXMLLoader.load(getClass().getClassLoader().getResource("addTask/AddTask.fxml"));
        Scene addTaskScene = new Scene(addTaskParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addTaskScene);
        appStage.show();
    }
    
    
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
                // gda = new CategoryDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
                 gda = new CategoryDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "root");
                 
                // check if the task is associated to a category and color
                //categoryColor = gda.getColor(Integer.parseInt(task.getGroup()));
                
                // set task text to category color
                taskCheckBox.setTextFill(findColor(categoryColor));
                
                // checkbox event
                taskCheckBox.setOnAction(e -> {
                    try {
                        task.setStatus(task.getStatus() == 0 ? 1 : 0);
                        tda.updateStatus(task.getName(),task.getStatus()); //this has to be an integer
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
        
        // add sortOptions to sort combo box
        sortComboBox.getItems().addAll("Date", "Group", "Priority");
        
        // add tasks to selectTask combo box to view details in the right pane text area
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
      
}
