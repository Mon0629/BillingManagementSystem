package billings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import databaseSQL.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillDAOImpl implements BillDAO{

	private ObservableList <Bill> billsData = FXCollections.observableArrayList();

	public ObservableList<Bill> getBillsData() {
		return billsData;
	}

	@Override
	public Bill createBill() {
		return null;
	}
	
	@Override
	public void addBill(Bill bill) {
		String insertQuery = "INSERT INTO bills (customerID, shipCustomerID, issueDate, dueDate, docType) values(?,?,?,?,?)";
		try (
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, bill.getCustomerID());
			statement.setInt(2, bill.getShipCustomerID());
			statement.setDate(3, bill.getIssueDate());
			statement.setDate(4, bill.getDueDate());
			statement.setString(5, String.valueOf(bill.getDoctype()));

			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void fetchBill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBill() {
		// TODO Auto-generated method stub
		
	}
	

}
