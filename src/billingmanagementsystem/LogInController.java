/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LogInController implements Initializable {

	@FXML
	private PasswordField passwordField;

	@FXML
	private ImageView showPasswordIcon;

	@FXML
	private Text showPasswordText;
	
	@FXML
	private Label forget_password;

	boolean isShow = false;
	/**
	 * Initializes the controller class.
	 */
	@Override

	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	void showPassword(MouseEvent event) {
		
		if (!isShow) {
			showPasswordText.setText(passwordField.getText());
			passwordField.setVisible(false);
			isShow = true;
		}
		
		passwordField.setVisible(true);
		isShow = false;
		
	}

	@FXML
	private void login(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Admin_Dashboard.fxml"));
			Stage primaryStage = new Stage(); 
			Scene scene = new Scene(root);
			primaryStage.setTitle("Billing Management System");
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();



			((Node)(event.getSource())).getScene().getWindow().hide();
		} catch (IOException ex) {
			Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error loading Admin_Dashboard.fxml", ex);
		}
	}


	@FXML
	private void forgetpassword(MouseEvent event) {

	}

}
