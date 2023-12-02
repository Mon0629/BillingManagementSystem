
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import databaseSQL.DatabaseManager;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.sql.Timestamp;
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
    @FXML
    private AreaChart<Date, Number> chart1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        COUNTTOTALPRODUCTS();
        COUNTTOTALCUSTOMER();
        PopulateChart1();
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
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customers");

            if (resultSet.next()) {
                String total;
                total = resultSet.getString("COUNT(*)");
                totalcustomer.setText(total);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }

        return count;
    }
    
    private XYChart.Series<Date, Number> PopulateChart1() {
    XYChart.Series<Date, Number> series = new XYChart.Series<>();

    try {
        // Establish a connection to your SQL database
        Connection connection = DatabaseManager.getConnection();

        // Create a statement to execute SQL queries
        Statement statement = connection.createStatement();

        // Execute the query to retrieve the data
        ResultSet resultSet = statement.executeQuery("SELECT DATETIME(creationDate), COUNT(*) FROM customers GROUP BY creationDate");

        // Iterate over the result set and add data to the series
        while (resultSet.next()) {
            Timestamp date = resultSet.getTimestamp("DATETIME(creationDate)");
            int count = resultSet.getInt("COUNT(*)");

            series.getData().add(new XYChart.Data<>(date, count));
        }

        chart1.getData().add(series);
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return series;
}

    
}
