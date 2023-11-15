package customer;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databaseSQL.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import customer.Customer;

public class CustomerDAOImpl implements CustomerDAO {
	
	private ObservableList <Customer> customersData = FXCollections.observableArrayList();

	public ObservableList<Customer> getCustomersData() {
		return customersData;
	}

	@Override
	public void createCustomer(int customerID, Timestamp creationDate, String firstName, String lastName, String email, String address, String town, String country, int postal) {
		
		
	}

	@Override
	public void fetchAllCustomers() {
		
		try {
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * from customers");
	        ResultSet resultSet = statement.executeQuery();
	        
	        while(resultSet.next()) {
	        	Customer customer = new Customer(
	        		resultSet.getInt("customerID"),
	        		resultSet.getTimestamp("creationDate"),
	        		resultSet.getString("firstName"),
	        		resultSet.getString("lastName"),
	        		resultSet.getString("email"),
	        		resultSet.getString("town"),
	        		resultSet.getString("address"),
	        		resultSet.getString("country"),
	        		resultSet.getInt("postal")
	        		);
	        	customersData.add(customer);
	        	System.out.println(customer.getCustomerId() + customer.getFirstName() + customer.getLastName());
	        }
	        resultSet.close();
	        statement.close();
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	public void deleteCustomer(int customerID) {
		
		try {
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE customerID = " + customerID);
			statement.executeUpdate();
			
			statement.close();
	        connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
