package customer;

import java.security.Timestamp;
import java.util.List;

import javafx.collections.ObservableList;


public interface CustomerDAO {
	void createCustomer(int customerID, Timestamp creationDate, String name, String email, String address, String town, String country, int postal);
	void fetchAllCustomers();
	ObservableList<Customer> getAllCustomer();
	Customer getCustomerByID();
	void updateCustomer();
	void deleteCustomer();
	
	
}
