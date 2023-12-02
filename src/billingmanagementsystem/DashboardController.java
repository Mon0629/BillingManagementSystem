
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import databaseSQL.DatabaseManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DashboardController implements Initializable {

    @FXML
    private Label totalorders;
    @FXML
    private Label totalrevenue;
    @FXML
    private Label totalproducts;
    @FXML
    private Label totalcustomer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        COUNTTOTALPRODUCTS();
        COUNTTOTALCUSTOMER();
    }    
    
    private int COUNTTOTALPRODUCTS() {
        int count = 0;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM products");

            if (resultSet.next()) {
                String total;
                total = resultSet.getString("COUNT(*)");
                totalproducts.setText(total);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }

        return count;
    }
    
    private int COUNTTOTALCUSTOMER() {
        int count = 0;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");

            if (resultSet.next()) {
                String total;
                total = resultSet.getString("COUNT(*)");
                totalproducts.setText(total);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }

        return count;
    }
    
}
