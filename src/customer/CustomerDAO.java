package customer;

public interface CustomerDAO {
	void createCustomer(
		int customerID, 
		String creationDate, 
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
	void updateCustomer(Customer customer);
	void deleteCustomer(int customerID);
}
