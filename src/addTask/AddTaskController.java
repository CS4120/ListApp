//Brian Graham 
//backend logic for the Add Task scene
//gives the user the ability to add groups and tasks
package addTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.CategoryDataAccessor;
import controller.TaskDataAccessor;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Category;



public class AddTaskController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private List<Category> groups;
    
    @FXML
    private Label lbGroupName;
    
    @FXML
    private JFXComboBox cbGroupName;
    
    @FXML
    private JFXTextField tfGroupName;
    
    @FXML
    private JFXColorPicker cpColor;
    
    @FXML
    private JFXButton btnAddGroup;
    
    @FXML
    private JFXButton cancelBtn;
    
    @FXML
    private JFXButton submitBtn;
    
    @FXML
    private JFXTextField tfTaskName;
    
    @FXML
    private DatePicker dpDueDate;
    
    @FXML
    private JFXTextArea taSummary;
    
    @FXML 
    private DatePicker dpReminder;
    
    @FXML
    private JFXComboBox cbPriority;
    
    @FXML //Event handles for the exit button
    private void handleCancelBtnAction(ActionEvent event) throws IOException {
    System.out.println("Exit Clicked!");
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("todoapp/MainView.fxml"));
    Scene mainViewScene = new Scene(root);
    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    appStage.setScene(mainViewScene);
    appStage.show();
}
    
    @FXML //Event handler for the New Group selection
    private void handleAddGroupSelection(ActionEvent e) throws IOException {
        if("New Group".equals(cbGroupName.getValue().toString())) {
            btnAddGroup.setVisible(true);
            cpColor.setVisible(true);
            tfGroupName.setVisible(true);
            lbGroupName.setVisible(true);
        }
    }
    
    @FXML //Event handler for the Add Group button and reload the scene
    public void handleAddGroup(ActionEvent e) throws IOException, SQLException {
        try { //Connection to database
            CategoryDataAccessor cda = new CategoryDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
            cda.addCategory(tfGroupName.getText(), cpColor.getValue().toString(), 0);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Parent addTaskParent = FXMLLoader.load(getClass().getClassLoader().getResource("addTask/AddTask.fxml"));
        Scene addTaskScene = new Scene(addTaskParent);
        Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        appStage.setScene(addTaskScene);
        appStage.show();
        
    }
    
    @FXML //Event handler for the Add Group button and reload the scene
    public void handleAddTask(ActionEvent e) throws IOException, SQLException {
        int groupId = 0;
        
        try { //Connection to database
            CategoryDataAccessor cda = new CategoryDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
            groups = cda.getGroupList();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddTaskController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
            if (null == cbGroupName.getValue()){
                groupId = 7;
            }
            
            else { 
                for(int i = 0; i < groups.size(); i++) {
                    if (cbGroupName.getValue().toString().equals(groups.get(i).getCategoryName())) {
                        groupId = groups.get(i).getId();
                    }
                }
            }
        
        try { //Connection to database
            TaskDataAccessor tda = new TaskDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
            int priority;
            if (null == cbPriority.getValue()){
                priority = 0;
            }
                    
            else if(cbPriority.getValue().equals("High")){
                priority = 1;
            }
            else if (cbPriority.getValue().equals("Medium")){
                priority = 2;
            }
            else if (cbPriority.getValue().equals("Low")){
                priority = 3;
            }
            
            else {
                priority = 0;
            }
            
            if (null == dpReminder.getValue() && null != dpDueDate.getValue()){
                tda.addTask(tfTaskName.getText(), java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(dpDueDate.getValue()), taSummary.getText(), groupId, priority, 0);
            }
            else if (null == dpReminder.getValue() && null == dpDueDate.getValue()){
                 tda.addTask(tfTaskName.getText(), java.sql.Date.valueOf(LocalDate.now()), taSummary.getText(), groupId, priority, 0);
            }
            else if (null != dpReminder.getValue() && null == dpDueDate.getValue()){
                tda.addTask(tfTaskName.getText(), java.sql.Date.valueOf(LocalDate.now()), taSummary.getText(), groupId, java.sql.Date.valueOf(dpReminder.getValue()),priority, 0);
            }
            else
            tda.addTask(tfTaskName.getText(), java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(dpDueDate.getValue()), taSummary.getText(), groupId, java.sql.Date.valueOf(dpReminder.getValue()), priority, 0);
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Parent addTaskParent = FXMLLoader.load(getClass().getClassLoader().getResource("addTask/AddTask.fxml"));
        Scene addTaskScene = new Scene(addTaskParent);
        Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        appStage.setScene(addTaskScene);
        appStage.show();
        
    }
    
    @Override  //Sets intial values for the combo boxes.
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbPriority.getItems().addAll("High", "Medium", "Low");
        
        cbGroupName.getItems().add("New Group");
        
        try { //Connection to database
            CategoryDataAccessor cda = new CategoryDataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/ListApp", "root", "Purple00");
            groups = cda.getGroupList();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddTaskController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        for(int i = 0; i < groups.size(); i++) {
            cbGroupName.getItems().addAll(groups.get(i).getCategoryName());
        }
        
    }    
    
}
