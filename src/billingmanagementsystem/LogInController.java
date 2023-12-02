/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import databaseSQL.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

	private Text showPasswordText;
	
	@FXML
	private Label forget_password;

	boolean isShow = false;
    @FXML
    private TextField usernameField;
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
        String username = usernameField.getText(); // Assuming you have a usernameField in your FXML
        String password = passwordField.getText(); // Assuming you have a passwordField in your FXML

        // Your SQL query to check username and password
        String query = "SELECT * FROM Login WHERE username=? AND password=?";
        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement pst = connection.prepareStatement(query)) {

                 pst.setString(1, username);
                 pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Successful login, open Admin Dashboard
                    Parent root = FXMLLoader.load(getClass().getResource("Admin_Dashboard.fxml"));
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("Billing Management System");
                    primaryStage.setScene(scene);
                    primaryStage.initStyle(StageStyle.UNDECORATED);
                    primaryStage.show();

                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } else {
                    System.out.println("Incorrect username or password");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error loading Admin_Dashboard.fxml", ex);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
	}


	@FXML
	private void forgetpassword(MouseEvent event) {

	}

}
