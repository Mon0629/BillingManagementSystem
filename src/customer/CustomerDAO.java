package customer;

import java.sql.Timestamp;
import java.util.List;




public interface CustomerDAO {
	void createCustomer(
		int customerID, 
		Timestamp creationDate, 
		String firstName, 
		String lastName, 
		String email, 
		String address, 
		String town, 
		String country, 
		int postal);
	void fetchAllCustomers();
	Customer getCustomerByID();
	void updateCustomer();
	void deleteCustomer();
	
	
}
