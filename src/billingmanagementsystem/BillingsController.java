/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.*;
import billings.Bill;
import billings.BillDAOImpl;
import customer.Customer;
import customer.CustomerDAOImpl;
import databaseSQL.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lineItems.LineItem;
import lineItems.LineItemDAOImpl;
import pdfGeneration.PDFGenerator;
/**
 * FXML Controller class
 *
 * @author User
 */


public class BillingsController implements Initializable {

	@FXML
	private ComboBox<Bill.DocType> docTypeComboBox;
	@FXML
	private DatePicker current_datepicker;
	@FXML
	private DatePicker due_datepicker;
	@FXML
	private AnchorPane product_pane;
	@FXML
	private TextField textField1;
	@FXML
	private TextField textField2;
	@FXML
	private TextField textField5;
	@FXML
	private TextField textField3;
	@FXML
	private TableView<OrderList> OrderListTable;
	@FXML
	private TableColumn<OrderList, Integer> order_product_id;
	@FXML
	private TableColumn<OrderList, String> order_product_name;
	@FXML
	private TableColumn<OrderList, Double> order_price;
	@FXML
	private TableColumn<OrderList, Double> order_qty;
	@FXML
	private TableColumn<OrderList, Double> order_amount;
	@FXML
	private Label totalamount;
	@FXML
	private TextField prodID;
	@FXML
	private Label warningtext;
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private TextField address;
	@FXML
	private TextField cnumber;
	@FXML
	private TextField email;
	@FXML
	private TextField town;
	@FXML
	private TextField country;
	@FXML
	private TextField postal;
	@FXML
	private TextField telephone;
	@FXML
	private CheckBox VAT;
	@FXML
	private AnchorPane billingpane;
	@FXML
	private TextField firstNameShip, lastNameShip, contactShip, emailShip, addressShip, townShip, countryShip, postalShip;
	@FXML
	private Button selectCustomerBtn, selectShipCustomerBtn, confirmButton; 
	@FXML
	private Text confirmMessage;
	@FXML
	private Pane confirmMessagePane;

	CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    @FXML
    private TextField telephoneShip;
    @FXML
    private TableColumn<ManageInvoiceData, Boolean> actionColumn;
    @FXML
    private TableColumn<ManageInvoiceData, Integer> customerIDColumn;
    @FXML
    private TableColumn<ManageInvoiceData, Integer> billIDColumn;
    @FXML
    private TableColumn<ManageInvoiceData, String> firstNameColumn;
    @FXML
    private TableColumn<ManageInvoiceData, String> lastNameColumn;
    @FXML
    private TableColumn<ManageInvoiceData, Double> totalAmountColumn;
    @FXML
    private TableColumn<ManageInvoiceData, String> noteColumn;
    @FXML
    private TableColumn<ManageInvoiceData, File> invoiceColumn;
    @FXML
    private TableView<ManageInvoiceData> InvoiceTable;

     private ManageInvoiceData manageInvoiceData;
     
    @FXML
    private ImageView productImageView;
    @FXML
    private TableColumn<OrderList, Boolean> actionColumnOrder;
    @FXML
    private TabPane tabPane;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setCurrentDate();
		setDueDate();
          
                
            
		confirmButton.setDisable(true);;
		docTypeComboBox.getItems().addAll(Bill.DocType.RECEIPT, Bill.DocType.BILL, Bill.DocType.INVOICE);
                InvoiceTable();
	}    

	//For current and due date
	private void setCurrentDate(){
		current_datepicker.setValue(LocalDate.now());
	}
	private void setDueDate(){
		LocalDate currentDate = LocalDate.now();
		LocalDate dueDate = currentDate.plusDays(30);
		due_datepicker.setValue(dueDate);
	}

        
        private void InvoiceTable(){
        
            try
            {
                InvoiceTable.getItems().setAll(retrieveProductsFromDatabase());
            } catch (SQLException ex)
            {
                Logger.getLogger(BillingsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID")); 
            billIDColumn.setCellValueFactory(new PropertyValueFactory<>("billID"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("lineItemTotal"));
            ButtonCellFactoryInvoice cellFactory = new ButtonCellFactoryInvoice(this);
            actionColumn.setCellFactory(cellFactory);
    }
        
        //retrieve grouped data para maayos ang itsura sa table view
        private ObservableList<ManageInvoiceData> retrieveProductsFromDatabase() throws SQLException {
        ObservableList<ManageInvoiceData> InvoiceList = FXCollections.observableArrayList();
        
        String sqlQuery = "SELECT * FROM Customers c JOIN bills b ON c.customerID = b.customerID JOIN lineItems l ON b.billID = l.billID GROUP BY b.billID";
        try (Connection con = DatabaseManager.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sqlQuery)) {

            while (rs.next()) {
                InvoiceList.add(new ManageInvoiceData(
                        rs.getInt("customerID"),
                        rs.getTimestamp("creationDate"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("contactNumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("town"),
                        rs.getString("country"),
                        rs.getInt("postal"),
                        rs.getInt("billID"),
                        rs.getInt("shipCustomerID"),
                        rs.getDate("issueDate"),
                        rs.getDate("dueDate"),
                        rs.getString("docType"),
                        rs.getTimestamp("transactionAdded"),
                        rs.getInt("lineItemID"),
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("unitPrice"),
                        rs.getDouble("lineItemTotal")
                ));
            }
        }
       return InvoiceList;
}
        
        //to retrieve detailed or not grouped data 
       private ObservableList<ManageInvoiceData> retrieveProductsFromDatabaseDetailed(int billID) throws SQLException {
    ObservableList<ManageInvoiceData> InvoiceList = FXCollections.observableArrayList();

    String sqlQuery = "SELECT * FROM Customers c JOIN bills b ON c.customerID = b.customerID JOIN lineItems l ON b.billID = l.billID WHERE b.billID = ?";
    try (Connection con = DatabaseManager.getConnection();
         PreparedStatement pst = con.prepareStatement(sqlQuery)) {
        pst.setInt(1, billID);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                InvoiceList.add(new ManageInvoiceData(
                        rs.getInt("customerID"),
                        rs.getTimestamp("creationDate"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("contactNumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("town"),
                        rs.getString("country"),
                        rs.getInt("postal"),
                        rs.getInt("billID"),
                        rs.getInt("shipCustomerID"),
                        rs.getDate("issueDate"),
                        rs.getDate("dueDate"),
                        rs.getString("docType"),
                        rs.getTimestamp("transactionAdded"),
                        rs.getInt("lineItemID"),
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("unitPrice"),
                        rs.getDouble("lineItemTotal")
                ));
            }
        }
    }
    // Now you can use InvoiceList as needed
    for (ManageInvoiceData invoiceData : InvoiceList) {
        System.out.println(invoiceData);
    }
    return InvoiceList;
}
       
       /*private void InvoiceTableUpdate(){
        
            try
            {
                ManageInvoiceData selectedData = InvoiceTable.getSelectionModel().getSelectedItem();
                int billid = selectedData.getBillID();
                OrderListTable.getItems().setAll(retrieveProductsFromDatabaseDetailed(selectedData));
                
                order_product_id.setCellValueFactory(new PropertyValueFactory<>("productID"));
                order_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
                order_price.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                order_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                order_amount.setCellValueFactory(new PropertyValueFactory<>("lineItemTotal"));
                actionColumnOrder.setCellFactory(new ButtonFactoryOrderList(OrderListTable, totalamount));
            } catch (SQLException ex)
            {
                Logger.getLogger(BillingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }*/
        

	@FXML
	private void opencustomertable(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerTable.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();

			CustomerTableController customerTableController = loader.getController();
			customerTableController.setBillingsController(this);
			if (event.getSource() == selectCustomerBtn)customerTableController.customerFlag = true;
			else customerTableController.customerFlag = false;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCustomerData(Customer customerData) {
		fname.setText(customerData.getFirstName());
		lname.setText(customerData.getLastName());
		cnumber.setText(customerData.getContactNumber());
		email.setText(customerData.getEmail());
		address.setText(customerData.getAddress());
		town.setText(customerData.getTown());
		country.setText(customerData.getCountry());
		postal.setText(customerData.getPostal());
	}

	 public void setShipCustomerData(Customer customer) {
		 firstNameShip.setText(customer.getFirstName());
		 lastNameShip.setText(customer.getLastName());
		 contactShip.setText(customer.getContactNumber());
		 emailShip.setText(customer.getEmail());
		 addressShip.setText(customer.getAddress());
		 townShip.setText(customer.getTown());
		 countryShip.setText(customer.getCountry());
		 postalShip.setText(customer.getPostal());
	 }
         
         public void setInvoiceData(ManageInvoiceData invoicedata){
            try
            {
                retrieveProductsFromDatabaseDetailed(invoicedata.getBillID());
                
            } catch (SQLException ex)
            {
                Logger.getLogger(BillingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fname.setText(invoicedata.getFirstName());
        lname.setText(invoicedata.getLastName());
        cnumber.setText(invoicedata.getContactNumber());
        email.setText(invoicedata.getEmail());
        address.setText(invoicedata.getAddress());
        town.setText(invoicedata.getTown());
        country.setText(invoicedata.getCountry());
        postal.setText(String.valueOf(invoicedata.getPostal()));
         
         }
         
         
    private void ClearFields(){
        docTypeComboBox.getSelectionModel().select(null);
        fname.setText(null);
        lname.setText(null);
        cnumber.setText(null);
        email.setText(null);
        address.setText(null);
        town.setText(null);
        country.setText(null);
        postal.setText(null);
        telephone.setText(null);
        firstNameShip.setText(null);
        lastNameShip.setText(null);
        contactShip.setText(null);
        emailShip.setText(null);
        addressShip.setText(null);
        townShip.setText(null);
        countryShip.setText(null);
        postalShip.setText(null);
        telephoneShip.setText(null);
        OrderListTable.getItems().clear();
        productImageView.setImage(null);
        updateTotalAmountLabel();
    }
  
    
    
         //for tab pane
         public TabPane getTabPane() {
        return tabPane;
    }

	@FXML
	private void enterprice(KeyEvent event) {
		String price = textField2.getText();
		String qty = textField3.getText();

		if (!price.isEmpty() && !qty.isEmpty()) {
			double subtotal = Double.parseDouble(price) * Double.parseDouble(qty);
			textField5.setText(String.valueOf(subtotal));
			warningtext.setText("");
		}else if (!price.isEmpty() && qty.isEmpty()){
			warningtext.setText("Enter Quantity");
		} else {
			warningtext.setText("Enter Price");
			textField5.setText("");
		}
	}

	@FXML
	private void subtotal(KeyEvent event) {
		String price = textField2.getText();
		String qty = textField3.getText();

		if (!price.isEmpty() && !qty.isEmpty()) {
			double subtotal = Double.parseDouble(price) * Double.parseDouble(qty);
			textField5.setText(String.valueOf(subtotal));
			warningtext.setText("");
		}else if (price.isEmpty() && !qty.isEmpty()){
			warningtext.setText("Enter Price");
		} else {
			warningtext.setText("Enter Quantity");
			textField5.setText("");
		}
	}

            // when you sleect product then add it to order
	private ObservableList<OrderList> tableItems = FXCollections.observableArrayList();
        
	@FXML
	private void addtoorder(ActionEvent event) {
		String productID = prodID.getText();
		String productName = textField1.getText();
		String price = textField2.getText();
		String quantity = textField3.getText();
		String amount = textField5.getText();
                if(checkProductStocks()){
//		System.out.println("Adding to order - Product ID: " + productID + ", Product Name: " + productName +
//				", Price: " + price + ", Quantity: " + quantity + ", Amount: " + amount);

		if (!productID.isEmpty() && !productName.isEmpty() && !price.isEmpty() && !quantity.isEmpty() && !amount.isEmpty()) {
			OrderList orderlist = new OrderList(productID, productName, Double.parseDouble(price), Double.parseDouble(quantity), Double.parseDouble(amount));

			order_product_id.setCellValueFactory(new PropertyValueFactory<>("productID"));
			order_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
			order_price.setCellValueFactory(new PropertyValueFactory<>("price"));
			order_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			order_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
                        actionColumnOrder.setCellFactory(new ButtonFactoryOrderList(OrderListTable, totalamount));
                        
                        

			tableItems.add(orderlist);
			OrderListTable.setItems(tableItems);
                        
			updateTotalAmountLabel();
                        
                        warningtext.setText("Added to orderlist");
                       
                        warningtext.setTextFill(Color.GREEN);
                        
                       Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                         warningtext.setText(null);
                }
            }));
                         timeline.play();
                        
			prodID.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField5.clear();
			productImageView.setImage(null);
			confirmButton.setDisable(false);
		} else {
			System.out.println("One or more fields are empty. Not adding to order.");
                        warningtext.setText("Fill up necessary fields");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    warningtext.setText(null);
                }
            }));
            timeline.play();
		}
                }
	}
        
        //total amount 
	private void updateTotalAmountLabel() {
		double totalAmount = 0.0;

		for (OrderList orderItem : tableItems) {
			totalAmount += orderItem.getAmount();
		}

		totalamount.setText(String.valueOf(totalAmount));
	}


	@FXML
	private void openproducttable(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductTable.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			ProductTableController productController = loader.getController();
			productController.setBillingsController(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setProductData(ProductData productData) {
		// Update initial text fields
		prodID.setText(String.valueOf(productData.getProductID()));
		textField1.setText(productData.getProductName());
		textField2.setText(String.valueOf(productData.getPrice()));
                productImageView.setImage(productData.getImage());

	}

	@FXML
	private void VATButton(ActionEvent event) {
		double vat = Double.parseDouble(totalamount.getText());
		double tax = vat * 0.12;
		if (VAT.isSelected()){ 
			double taxes = vat - tax;
			String taxed = String.valueOf(taxes);
			totalamount.setText(taxed);

		}else {
			updateTotalAmountLabel();
		}
	}

	private void customerDataChecker() {
		Customer customer = new Customer(
				fname.getText(),
				lname.getText(),
				cnumber.getText(),
				email.getText(),
				address.getText(),
				town.getText(),
				country.getText(),
				postal.getText()
		);
		
		Customer shipCustomer = new Customer(
				firstNameShip.getText(),
				lastNameShip.getText(),
				contactShip.getText(),
				emailShip.getText(),
				addressShip.getText(),
				townShip.getText(),
				countryShip.getText(),
				postalShip.getText()
		);
		
		if (!customerDAO.checkIfCustomerExists(customer)) customerDAO.addCustomer(customer);
		if (!customerDAO.checkIfCustomerExists(shipCustomer)) customerDAO.addCustomer(shipCustomer);
		
	}
	
	private Bill buildBill() {
		//Getting data for new bill
		Customer customer = customerDAO.getCustomerByName(fname.getText(), lname.getText());
		Customer shipCustomer = customerDAO.getCustomerByName(firstNameShip.getText(), lastNameShip.getText());
		Date issueDate = Date.valueOf(current_datepicker.getValue());
		Date dueDate = Date.valueOf(due_datepicker.getValue());

		//New Bill Constructor
		Bill bill = new Bill(
				customer.getCustomerId(),
				shipCustomer.getCustomerId(),
				issueDate,
				dueDate,
				docTypeComboBox.getValue()
				);
		
		return bill;
	}
	
	private List<LineItem> lineItemListBuilder(Bill bill) {
		
		List<LineItem> lineItemList = new ArrayList<>();
		
		for (OrderList orderList : tableItems) {
			int quantity= (int) (orderList.getQuantity());
			LineItem lineItem = new LineItem(
					bill.getBillID(),
					Integer.valueOf(orderList.getProductID()),
					orderList.getProductName(),
					quantity,
					BigDecimal.valueOf(orderList.getPrice()),
					BigDecimal.valueOf(orderList.getAmount())
					);
			lineItemList.add(lineItem);
		}
		
		return lineItemList;
	}
	
	private void openFile(String path) {
		try {

			File pdfFile = new File(path);
			if (pdfFile.exists()) {

				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}

			} else {
				System.out.println("File does not exists!");
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void CREATEINVOICE(ActionEvent event) throws IOException {
		BillDAOImpl billDAO = new BillDAOImpl();
		LineItemDAOImpl lineItemDAO = new LineItemDAOImpl();
		
		//Check if customers are already exists
		customerDataChecker();
		Bill bill = buildBill();
		
		//Insert bill to database
		if (docTypeComboBox.getValue() == null) {
			confirmMessage.setText("Select Document Type");
			confirmMessage.setStyle("-fx-fill: #D33434;");
			confirmMessagePane.setVisible(true);
		}
		else {
			confirmMessage.setText(null);
			billDAO.addBill(bill);
		
			//Populate lineItemList for adding lineItems to database
			Bill createdBill = billDAO.getLastBill();
			List<LineItem> lineItemList = lineItemListBuilder(createdBill);
			
			//Adding lineItems
			lineItemDAO.addLineItems(lineItemList);
			updateProductStocks(lineItemList);
                        
			PDFGenerator pdfGenerator = new PDFGenerator(
					createdBill, 
					customerDAO.getCustomerByID(createdBill.getCustomerID()), 
					customerDAO.getCustomerByID(createdBill.getShipCustomerID()), 
					lineItemList);
			
			pdfGenerator.createPDF();
			
			confirmMessage.setStyle("-fx-fill: #435585;");
			confirmMessage.setText(createdBill.getDoctype() + " Created");
			confirmMessagePane.setVisible(true);
                        ClearFields();
			openFile(pdfGenerator.getPath());
                        
                        
                        
            /*           FXMLLoader loader = new FXMLLoader(getClass().getResource("Invoice.fxml"));
        Parent root = loader.load();
        
        InvoiceController controller = loader.getController();
        controller.setInvoiceData(bill);

        Stage previewStage = new Stage();
        previewStage.setTitle("Preview");
        previewStage.setScene(new Scene(root));
        previewStage.initStyle(StageStyle.UNDECORATED);

        previewStage.show();*/
                       
  
                        
                        
		}
	}
        
        
        //for updating stocks in database
        
        private void updateProductStocks(List<LineItem> lineItemList) {
        try (Connection con = DatabaseManager.getConnection()) {
            for (LineItem lineItem : lineItemList) {
                int productId = lineItem.getProductID();
                int quantity = lineItem.getQuantity();
                
                // Retrieve current stock
                int currentStock = getCurrentStock(con, productId);

                if (quantity > currentStock){
                
                warningtext.setText(currentStock + " left");
                     Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    warningtext.setText(null);
                }
            }));
            timeline.play();
            
                }else{
                int updatedStock = currentStock - quantity;
                updateStockInDatabase(con, productId, updatedStock);
                }
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

        
        
    private int getCurrentStock(Connection con, int productId) throws SQLException {
        String query = "SELECT stocks FROM Products WHERE productID = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, productId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stocks");
                }
            }
        }
        return 0;
    }

    private void updateStockInDatabase(Connection con, int productId, int updatedStock) throws SQLException {
        String query = "UPDATE Products SET Stocks = ? WHERE ProductID = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, updatedStock);
            pst.setInt(2, productId);
            pst.executeUpdate();
        }
    }

        

    @FXML
    private void changetoTab2(Event event) {
        System.out.println(tabPane.getSelectionModel().getSelectedIndex());
        InvoiceTable();
    }

    
    private boolean checkProductStocks(){
        boolean haveStock = false;
            try
            {
                DatabaseManager db = new DatabaseManager();
                Connection con = db.getConnection();
                
                int quantity = Integer.valueOf(textField3.getText());
                
                int stocks = getCurrentStock(con, Integer.valueOf(prodID.getText()));
                if(quantity > stocks){
                    warningtext.setText(stocks + " stock/s left");
                     Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    warningtext.setText(null);
                }
            }));
            timeline.play();
            
                 haveStock = false;   
                }
                else{
                    haveStock = true;
                }} catch (SQLException ex)
            {
                Logger.getLogger(BillingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return haveStock;
    }
    
    
    private void deductStocks(int productID){
    
            try
            {
                Connection con = DatabaseManager.getConnection();
                int quantity = Integer.valueOf(textField3.getText());
               
                PreparedStatement ps = con.prepareStatement("Update products SET Stocks = " + quantity + " WHERE productID = " +productID );
               ps.executeUpdate();
            } catch (SQLException ex)
            {
                Logger.getLogger(BillingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    
    
}		
