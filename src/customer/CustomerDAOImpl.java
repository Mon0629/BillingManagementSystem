package customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import databaseSQL.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CustomerDAOImpl implements CustomerDAO {
	
	private ObservableList <Customer> customersData = FXCollections.observableArrayList();

	public ObservableList<Customer> getCustomersData() {
		return customersData;
	}

	@Override
	public void createCustomer(int customerID, Timestamp creationDate, String firstName, String lastName, String email, String address, String town, String country, String postal) {
		
		
	}

	@Override
	public void fetchAllCustomers() {
		customersData.clear();
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
	        		resultSet.getString("contactNumber"),
	        		resultSet.getString("email"),
	        		resultSet.getString("town"),
	        		resultSet.getString("address"),
	        		resultSet.getString("country"),
	        		resultSet.getString("postal")
	        		);
	        	customersData.add(customer);
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

	@Override
	public void addCustomer(Customer customer) {
		String insertQuery = "INSERT INTO customers (firstName,lastName,contactNumber,email,address,town,country,postal) values(?,?,?,?,?,?,?,?)";
		try (
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getContactNumber());
			statement.setString(4, customer.getEmail());
			statement.setString(5, customer.getAddress());
			statement.setString(6, customer.getTown());
			statement.setString(7, customer.getCountry());
			statement.setString(8, customer.getPostal());

			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
