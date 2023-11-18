package customer;

import java.sql.Timestamp;

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
		String postal);
	void fetchAllCustomers();
	void addCustomer(Customer customer);
	Customer getCustomerByID();
	void updateCustomer();
	void deleteCustomer(int customerID);
}
