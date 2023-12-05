/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.sql.*;
import databaseSQL.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProductDetailsController implements Initializable {

    @FXML
    private TextField prodID;
    @FXML
    private TextField prodName;
    @FXML
    private TextField prodPrice;
    @FXML
    private TextArea prodDesc;
    @FXML
    private ImageView prodImage;
    @FXML
    private Label msgconfirmation;
    @FXML
    private Spinner<Integer> NoOfStocks;
    @FXML
    private ComboBox<String> prodParentType;
    @FXML
    private ComboBox<String> prodSubcategory;
    @FXML
    private ComboBox<String> prodBrand;
    @FXML
    private ComboBox<String> otherAtt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       SUPERCATEGORY();
       otherAttributes();
       Spinner();
    }    
    
    private boolean isEditMode = false;
    private ProductData existingProductData;
    
    
    
    
    
   public void setProductData(ProductData productData) {
       
        if (productData != null) {
            // Populate your UI components with the data from productData
            prodID.setText(String.valueOf(productData.getProductID()));
            prodName.setText(productData.getProductName());
            prodPrice.setText(String.valueOf(productData.getPrice()));
            prodDesc.setText(productData.getDescription());
            
            
                prodImage.setImage(productData.getImage());
                
                
            existingProductData = productData;
            isEditMode = true;
            
        } else {
            // Clear UI components if it's in add mode
            prodID.clear();
            prodName.clear();
            prodPrice.clear();
            prodDesc.clear();
            prodImage.setImage(null);
            isEditMode = false; 
        }
       
       
    
        prodID.setText(String.valueOf(productData.getProductID()));
        prodName.setText(productData.getProductName());
        prodPrice.setText(String.valueOf(productData.getPrice()));
        prodDesc.setText(productData.getDescription());
      
        
       
       
    }
   
   private Image convertBlobToImage(Blob blob) {
        if (blob != null) {
            try (InputStream inputStream = blob.getBinaryStream()) {
                return new Image(inputStream);
            } catch (SQLException | IOException e) {
            }
        }
        return null;
    }
   
   //para sa pagconvert ng imgae to byyte
     private byte[] ImageToByteArray() throws IOException {
 
        Image image = prodImage.getImage();

        if (image != null) {
            File tempFile = File.createTempFile("temp", null);
            tempFile.deleteOnExit();

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", tempFile);
            return Files.readAllBytes(tempFile.toPath());
        }
        return null;
    }
   
     private int insertProductInDatabase(String ProductName, double Price, int Stocks, String Description, byte[] Image, String parentType, String type, String Brand, String otherAttributes) throws SQLException {
    Connection connection = DatabaseManager.getConnection();

    String query = "INSERT INTO products (ProductName, Price, Stocks, Description, Image) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setString(1, ProductName);
        preparedStatement.setDouble(2, Price);
        preparedStatement.setInt(3, Stocks);
        preparedStatement.setString(4, Description);
        preparedStatement.setBytes(5, Image);

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    int generatedProductID = generatedKeys.getInt(1);

                    String query1 = "INSERT INTO productcategory (ProductID, parentType, type, Brand, otherAttributes) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {
                        preparedStatement1.setInt(1, generatedProductID);
                        preparedStatement1.setString(2, parentType);
                        preparedStatement1.setString(3, type);
                        preparedStatement1.setString(4, Brand);
                        preparedStatement1.setString(5, otherAttributes);

                        preparedStatement1.executeUpdate();
                    }
                    ClearFields();
                    msgconfirmation.setText("Product Saved");
                     msgconfirmation.setStyle("-fx-text-fill: green;");
                     Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    msgconfirmation.setText(null);
                }
                 }));
                     timeline.play();
                    return generatedProductID;
                } else {
                    throw new SQLException("Failed to retrieve auto-generated ProductID.");
                }
            }
        } else {
            throw new SQLException("Inserting product failed, no rows affected.");
        }
    }
}

     
    private void updateProductInDatabase(int productId, String productName, double price,int Stocks, String description, byte[] image) throws SQLException {
        Connection connection = DatabaseManager.getConnection();

        String query = "UPDATE products SET ProductName=?, Price=?, Description=?, Remarks=?, Image=? WHERE ProductID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, Stocks);
            preparedStatement.setString(4, description);
            preparedStatement.setBytes(5, image);
            preparedStatement.setInt(6, productId);

        preparedStatement.executeUpdate();
    }
       try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, Stocks);
            preparedStatement.setString(4, description);
            preparedStatement.setBytes(5, image);
            preparedStatement.setInt(6, productId);

        preparedStatement.executeUpdate();
    } 
        
}

     private void ClearFields(){
         prodID.setText(null);
         prodName.setText(null);
         prodPrice.setText(null);
         prodDesc.setText(null);
         prodImage.setImage(null);
         NoOfStocks.getValueFactory().setValue(0);
         prodParentType.setValue(null);
         prodSubcategory.setValue(null);
         prodBrand.setValue(null);
         otherAtt.setValue(null);
     }
     
    private boolean isImageViewEmpty(ImageView imageView) {
    return imageView.getImage() == null;
    }
   
    private ProductController productController;
    
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
    
    @FXML
    private void imageChooser(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(new Stage());
        
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            prodImage.setImage(image);
        }
    }

    

    @FXML
    private void saveproduct(ActionEvent event) {
    try {
        // Extract values from UI components
        String productName = prodName.getText();
        String priceText = prodPrice.getText();
        int spinnervalue = NoOfStocks.getValue();
        String description = prodDesc.getText();
        String pt = prodParentType.getValue();
        String sb = prodSubcategory.getValue();
        String brand = prodBrand.getValue();
        String oa = otherAtt.getValue();
        byte[] prodImageBytes = ImageToByteArray();
        

        if (productName.isEmpty() || priceText.isEmpty()  || description.isEmpty() || isImageViewEmpty(prodImage)) {
            msgconfirmation.setText("Please fill up all fields");
            msgconfirmation.setStyle("-fx-text-fill: red;");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    msgconfirmation.setText(null);
                }
            }));
            timeline.play();
        } else {
            double price = Double.parseDouble(priceText);

            if (isEditMode) {
                updateProductInDatabase(existingProductData.getProductID(), productName, price, spinnervalue, description, prodImageBytes);
                msgconfirmation.setText(" Product updated successfully");
                msgconfirmation.setStyle("-fx-text-fill: green;");
                ClearFields();
                
            } else {
                insertProductInDatabase(productName, price, spinnervalue, description, prodImageBytes, pt, sb, brand, oa);
                msgconfirmation.setText(productName + " added successfully");
                msgconfirmation.setStyle("-fx-text-fill: green;");
                ClearFields();
                productController.refresh();
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    msgconfirmation.setText(null);
                }
            }));
            timeline.play();
        }

    } catch (IOException | SQLException | NumberFormatException ex) {
        msgconfirmation.setText("Error: Invalid input");
        msgconfirmation.setStyle("-fx-text-fill: red;");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                msgconfirmation.setText(null);
            }
        }));
        timeline.play();
        Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @FXML
    private void clearfields(ActionEvent event) {
        ClearFields();
    }
    
    
    private void SUPERCATEGORY(){
        ObservableList<String> parentType = FXCollections.observableArrayList("Footwear","Clothing", "Accessories");
        prodParentType.setItems(parentType);
        
        prodParentType.setOnAction(event -> handleSubCategory());

}
    private void handleSubCategory(){
    String selectedSuperCategory = prodParentType.getValue();
    ObservableList<String> subcat = getSubcategory(selectedSuperCategory);
   prodSubcategory.setItems(subcat);
   prodSubcategory.setOnAction(event-> handleBrand());
    }
   private ObservableList<String> getSubcategory(String category) {
        switch (category) {
            case "Footwear":
                return FXCollections.observableArrayList("Shoes", "Flops", "Sneakers");
            case "Clothing":
                return FXCollections.observableArrayList("Shorts", "Tops", "Underwear");
            case "Accessories":
                
                return FXCollections.observableArrayList("Pads", "Hats", "Gloves");
            default:
                return FXCollections.observableArrayList();
        }
        
    }
   private void handleBrand(){
    String selectedSubcat = prodSubcategory.getValue();
    ObservableList<String> brand = getBrand(selectedSubcat);
   prodBrand.setItems(brand);
    }
   private ObservableList<String> getBrand(String Brand) {
        switch (Brand) {
            case "Shoes":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
               
            case "Flops":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Sneakers":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Shorts":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Tops":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Underwear":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Hats":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Pads":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            case "Gloves":
                return FXCollections.observableArrayList("Nike", "Converse", "Vans");
            default:
                return FXCollections.observableArrayList();
        }
        
    }
   
        private void otherAttributes(){
        ObservableList<String> others = FXCollections.observableArrayList("Men","Women", "Kids");
        otherAtt.setItems(others);

}
        private void Spinner(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        NoOfStocks.setValueFactory(valueFactory);
        NoOfStocks.setOnScroll(event -> {
            int currentValue = NoOfStocks.getValue();
            if (event.getDeltaY() > 0) {
                NoOfStocks.getValueFactory().setValue(currentValue + 1);
            } else {
                NoOfStocks.getValueFactory().setValue(currentValue - 1);
            }
        });
        
        }
   
    
}
