package customer;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import databaseSQL.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import customer.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public void createCustomer(int customerID, Timestamp creationDate, String name, String email, String address, String town, String country, int postal) {
		
		
	}

	@Override
	public void fetchAllCustomers() {
		
		try {
			ObservableList<Customer> customersData = FXCollections.observableArrayList();
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * from bms.customers");
	        ResultSet resultSet = statement.executeQuery();
	        
	        while(resultSet.next()) {
	        	Customer customer = new Customer(
	        		resultSet.getInt("customerID"),
	        		resultSet.getTimestamp("creationDate"),
	        		resultSet.getString("firstName"),
	        		resultSet.getString("lastName")
	        		resultSet.getString("email"),
	        		resultSet.getString("town"),
	        		resultSet.getString("address"),
	        		resultSet.getString("country"),
	        		resultSet.getInt("postal")
	        		);
	        	customersData.add(customer);
	        	System.out.println(customer.getName());
	        }
	        resultSet.close();
	        statement.close();
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ObservableList<Customer> getAllCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerByID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer() {
		// TODO Auto-generated method stub
		
	}

}
