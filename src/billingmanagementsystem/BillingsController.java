/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class BillingsController implements Initializable {

    @FXML
    private DatePicker current_datepicker;
    @FXML
    private DatePicker due_datepicker;
    @FXML
    private AnchorPane product_pane;
    @FXML
    private ImageView add_product;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCurrentDate();
        setDueDate();
       
    }    
 
    
    
    //For curretn and due date
    private void setCurrentDate(){
        current_datepicker.setValue(LocalDate.now());
    }
   private void setDueDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = currentDate.plusDays(30);
        due_datepicker.setValue(dueDate);
   }

   
   private int textFieldCount = 0;
    @FXML
    private void add_product(MouseEvent event) {
        
        TextField firstTextField = createNewTextField(248, 25, 48, 62);
        product_pane.getChildren().add(firstTextField);

        TextField secondTextField = createNewTextField(61, 25, 423, 62);
        product_pane.getChildren().add(secondTextField);

        TextField thirdTextField = createNewTextField(341, 21, 522, 62);
        product_pane.getChildren().add(thirdTextField);

        TextField fourthTextField = createNewTextField(83, 25, 898, 62);
        product_pane.getChildren().add(fourthTextField);

        double yOffset = 50;
        for (int i = 0; i < 4; i++) {
            TextField newFirstTextField = createNewTextField(248, 25, 48, 62 + (textFieldCount + 1) * yOffset);
            product_pane.getChildren().add(newFirstTextField);

            TextField newSecondTextField = createNewTextField(61, 25, 423, 62 + (textFieldCount + 1) * yOffset);
            product_pane.getChildren().add(newSecondTextField);

            TextField newThirdTextField = createNewTextField(341, 21, 522, 62 + (textFieldCount + 1) * yOffset);
            product_pane.getChildren().add(newThirdTextField);

            TextField newFourthTextField = createNewTextField(83, 25, 898, 62 + (textFieldCount + 1) * yOffset);
            product_pane.getChildren().add(newFourthTextField);

            textFieldCount++;
        }
        
    }
    
    private TextField createNewTextField(double width, double height, double layoutX, double layoutY) {
        TextField textField = new TextField();
        textField.setPrefSize(width, height);
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        return textField;
    }
    
    
}

