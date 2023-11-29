/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import billings.Bill;
import customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private TextField textField4;
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
	private Button TEST;
	@FXML
	private AnchorPane billingpane;
	@FXML
	private TextField firstNameShip, lastNameShip, contactShip, emailShip, addressShip, townShip, countryShip, postalShip;
	@FXML
	private Button selectCustomerBtn, selectShipCustomerBtn; 


	Bill bill = new Bill();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setCurrentDate();
		setDueDate();

		docTypeComboBox.getItems().addAll(Bill.DocType.RECEIPT, Bill.DocType.BILL, Bill.DocType.INVOICE);

	}    

	//For current and due date
	private void setCurrentDate(){
		current_datepicker.setValue(LocalDate.now());
		bill.setIssueDate(Date.valueOf(current_datepicker.getValue()));
	}
	private void setDueDate(){
		LocalDate currentDate = LocalDate.now();
		LocalDate dueDate = currentDate.plusDays(30);
		due_datepicker.setValue(dueDate);
		bill.setDueDate(Date.valueOf(due_datepicker.getValue()));
	}


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

		bill.setCustomerID(customerData.getCustomerId());
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

	private ObservableList<OrderList> tableItems = FXCollections.observableArrayList();
	@FXML
	private void addtoorder(ActionEvent event) {
		String productID = prodID.getText();
		String productName = textField1.getText();
		String price = textField2.getText();
		String quantity = textField3.getText();
		String amount = textField5.getText();

		System.out.println("Adding to order - Product ID: " + productID + ", Product Name: " + productName +
				", Price: " + price + ", Quantity: " + quantity + ", Amount: " + amount);

		if (!productID.isEmpty() && !productName.isEmpty() && !price.isEmpty() && !quantity.isEmpty() && !amount.isEmpty()) {
			OrderList orderlist = new OrderList(productID, productName, Double.parseDouble(price), Double.parseDouble(quantity), Double.parseDouble(amount));

			order_product_id.setCellValueFactory(new PropertyValueFactory<>("productID"));
			order_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
			order_price.setCellValueFactory(new PropertyValueFactory<>("price"));
			order_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			order_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));


			tableItems.add(orderlist);
			OrderListTable.setItems(tableItems);
			updateTotalAmountLabel();

			prodID.clear();
			textField1.clear();
			textField2.clear();
			textField3.clear();
			textField5.clear();
		} else {
			System.out.println("One or more fields are empty. Not adding to order.");
		}
	}

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

	@FXML
	private void CREATEINVOICE(ActionEvent event) throws IOException {

		Bill bill = new Bill();

		//		 try {
		//	            // Create a PdfWriter
		//	            PdfWriter writer = new PdfWriter("example.pdf");
		//
		//	            // Create a PdfDocument
		//	            PdfDocument pdf = new PdfDocument(writer);
		//
		//	            // Create a Document
		//	            Document document = new Document(pdf);
		//
		//	            // Add content to the document
		//	            document.add(new Paragraph("Hello, iText!"));
		//
		//	            // Close the document
		//	            document.close();
		//	        } catch (FileNotFoundException e) {
		//	            e.printStackTrace();
		//	        }
	}

	//	 @FXML
	//	 private void CREATEINVOICE(ActionEvent event) {
	//		 try {
	//
	//			 PDDocument document = new PDDocument();
	//			 PDPage page = new PDPage();
	//			 document.addPage(page);
	//
	//			 PDPageContentStream contentStream = new PDPageContentStream(document, page);
	//
	//			 //eto yung i kokonvert mo sa image na node
	//			 AnchorPane billingsPane = billingpane;
	//
	//			 WritableImage snapshot = billingsPane.snapshot(new SnapshotParameters(), null);
	//			 BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
	//			 PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
	//
	//			 // suakt nya sa pdf file like location nung image
	//			 float pageWidth = 612; 
	//			 float pageHeight = 792; 
	//
	//			 float imageWidth = 7.5f * 72; 
	//			 float imageHeight = 10f * 72;
	//
	//			 // para nasa gitna yungimage
	//			 float x = (pageWidth - imageWidth) / 2; 
	//			 float y = (pageHeight - imageHeight) / 2; 
	//			 contentStream.drawImage(pdImage, x, y, imageWidth, imageHeight);
	//
	//			 contentStream.close();
	//
	//			 // kung saan mo isesave na folder
	//			 String outputPath = "C:\\Users\\User\\Downloads/"+fname.getText()+" "+ lname.getText()+" Invoice.pdf";
	//			 document.save(outputPath);
	//
	//			 document.close();
	//
	//			 System.out.println("PDF created successfully!");
	//
	//		 } catch (IOException e) {
	//			 e.printStackTrace();
	//		 }
	//	 }





}

