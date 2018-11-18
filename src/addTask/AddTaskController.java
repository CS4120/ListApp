
package addTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;


public class AddTaskController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
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
    private JFXTextField tfCategoryName;
    
    @FXML 
    private DatePicker dpReminder;
    
    @FXML
    private JFXComboBox cbPriority;
    
    @FXML
    private void handleCancelBtnAction(ActionEvent event) throws IOException {
    System.out.println("Cancel Clicked!");
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("todoapp/MainView.fxml"));
    Scene mainViewScene = new Scene(root);
    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    appStage.setScene(mainViewScene);
    appStage.show();
}
        @FXML
    private void handleSubmitBtnAction(ActionEvent event) throws IOException {
    System.out.println("Submit Clicked!");
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("todoapp/MainView.fxml"));
    Scene mainViewScene = new Scene(root);
    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    appStage.setScene(mainViewScene);
    appStage.show();
}
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> options = FXCollections.observableArrayList(
                "High",
                "Medium",
                "Low"
        );
        cbPriority.getItems().addAll(options);
        
    }    
    
}
