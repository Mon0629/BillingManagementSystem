package billingmanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;

import customer.Customer;
import customer.CustomerDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CustomerDetailsController implements Initializable {

    @FXML
    private TextField addressFid;

    @FXML
    private Button clearButton;

    @FXML
    private TextField contactFid;

    @FXML
    private TextField countryFid;

    @FXML
    private TextField emailFid;

    @FXML
    private TextField firstNameFid;

    @FXML
    private TextField lastNameFid;

    @FXML
    private TextField postalFid;

    @FXML
    private Button saveButton;

    @FXML
    private TextField townFid;

    CustomerDAOImpl CustomerDAO = new CustomerDAOImpl();
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    @FXML
    void clear(MouseEvent event) {
    	
    }

    @FXML
    void save(MouseEvent event) {
    	validateTextFields(firstNameFid, lastNameFid, contactFid, emailFid, addressFid, townFid, countryFid, postalFid);
    
    	Customer customer = new Customer(
    			firstNameFid.getText(),
    			lastNameFid.getText(),
    			Integer.parseInt(contactFid.getText()),
    			emailFid.getText(),
    			addressFid.getText(), 
    			townFid.getText(), 
    			countryFid.getText(), 
    			Integer.parseInt(postalFid.getText())
    			);
    	
    	CustomerDAO.addCustomer(customer);
    	
    	
    }

    private void validateTextFields(TextField... textFields) {
        boolean allFieldsFilled = true;

        for (TextField textField : textFields) {
            if (textField.getText().trim().isBlank()) {
                allFieldsFilled = false;
                break;
            }
        }

        if(!allFieldsFilled){
        	  Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setHeaderText(null);
              alert.setContentText("Please Fill All DATA");
              alert.showAndWait();
        }
    }

}
