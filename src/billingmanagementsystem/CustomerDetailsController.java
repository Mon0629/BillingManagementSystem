package billingmanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;

import customer.Customer;
import customer.CustomerDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    
    @FXML
    private Text messageBox;
    
    CustomerDAOImpl CustomerDAO = new CustomerDAOImpl();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    @FXML
    void clear(MouseEvent event) {
    	clearTextFields(firstNameFid, lastNameFid, contactFid, emailFid, addressFid, townFid, countryFid, postalFid);
    }

    @FXML
    void save(MouseEvent event) {
    	boolean canSave = validateTextFields(firstNameFid, lastNameFid, contactFid, emailFid, addressFid, townFid, countryFid, postalFid);
    	
    	if (canSave) {
    		Customer customer = new Customer(
        			firstNameFid.getText(),
        			lastNameFid.getText(),
        			contactFid.getText(),
        			emailFid.getText(),
        			addressFid.getText(), 
        			townFid.getText(), 
        			countryFid.getText(), 
        			postalFid.getText()
        			);
        	
        	CustomerDAO.addCustomer(customer);
        	messageBox.setText("Successfully added "+ customer.getFirstName() +" "+ customer.getLastName());
        	clearTextFields(firstNameFid, lastNameFid, contactFid, emailFid, addressFid, townFid, countryFid, postalFid);
    	}
    }

    private void clearTextFields(TextField... textFields) {
    	for (TextField textField : textFields) {
    		textField.clear();
        }
    }
    
    private boolean validateTextFields(TextField... textFields) {
        boolean allFieldsFilled = true;

        for (TextField textField : textFields) {
            if (textField.getText().trim().isBlank()) {
                allFieldsFilled = false;
                break;
            }
        }

        if(!allFieldsFilled){
			messageBox.setText("Please fill All Fields");
			return false;
        }
        return true;
    }

}
