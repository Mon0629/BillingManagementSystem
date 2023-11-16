/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import customerPane.*;
/**
 * FXML Controller class
 *
 * @author User
 */

public class Admin_DashboardController implements Initializable {

    @FXML
    private Button dashboard_btn;
    @FXML
    private Button product_btn;
    @FXML
    private Button billing_btn;
    @FXML
    private Button about_us_btn;
    @FXML
    private Button sign_out_btn;
    @FXML
    private AnchorPane customer_pane;
    @FXML
    private AnchorPane product_pane;
    @FXML
    private AnchorPane billing_pane;
    @FXML
    private Button customer_btn;
    @FXML
    private AnchorPane dashboard_pane;
    @FXML
    private ImageView icon1;
    @FXML
    private ImageView icon2;
    @FXML
    private ImageView icon3;
    @FXML
    private ImageView icon4;
    @FXML
    private ImageView icon5;
    @FXML
    private AnchorPane about_us_pane;
    @FXML
    private ImageView icon51;
    @FXML
    private ImageView exit;
    @FXML
    private ImageView maximize;
    @FXML
    private ImageView minimize;

    /**
     * Initializes the controller class.
     */
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
       
       loadFXML("Dashboard.fxml");

    }    

    
    private void loadFXML(String SwitchFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SwitchFXML));
            Parent root = loader.load();
            dashboard_pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void Switch_Pane(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            loadFXML("Dashboard.fxml");
        }else if (event.getSource() == customer_btn){
            loadFXML("Customer.fxml");
        }else if (event.getSource() == product_btn){
            loadFXML("Product.fxml");
        }else if (event.getSource() == billing_btn) {
            loadFXML("Billings.fxml");
        }
      
    }
    


@FXML
private void changestyle(MouseEvent event) {
    dashboard_btn.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), event.getSource() == dashboard_btn);
    customer_btn.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), event.getSource() == customer_btn);
    product_btn.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), event.getSource() == product_btn);
    billing_btn.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), event.getSource() == billing_btn);
    about_us_btn.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), event.getSource() == about_us_btn);
  
}

    @FXML
    private void logout(ActionEvent event) {
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to log out?");

    // Get the user's response
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    

    }

    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void maximize(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        if(stage.isMaximized()){
        stage.setMaximized(false);
        }else{
        stage.setMaximized(true);
        }
    }

    @FXML
    private void minimize(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    
    }
    
    