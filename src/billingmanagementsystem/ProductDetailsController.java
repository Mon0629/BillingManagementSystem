/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import com.mysql.cj.jdbc.Blob;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private TextField prodRemarks;
    @FXML
    private TextField prodPrice;
    @FXML
    private TextArea prodDesc;
    @FXML
    private ImageView prodImage;
    @FXML
    private Label msgconfirmation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private boolean isEditMode = false;
    private ProductData existingProductData;
    
   public void setProductData(ProductData productData) {
       
        if (productData != null) {
            // Populate your UI components with the data from productData
            prodID.setText(String.valueOf(productData.getProductID()));
            prodName.setText(productData.getProductName());
            prodPrice.setText(String.valueOf(productData.getPrice()));
            prodRemarks.setText(productData.getRemarks());
            prodDesc.setText(productData.getDescription());
            
            Image image = convertBlobToImage(productData.getImage());
                if (image != null) {
                prodImage.setImage(image);
                }
                
            existingProductData = productData;
            isEditMode = true;
            
        } else {
            // Clear UI components if it's in add mode
            prodID.clear();
            prodName.clear();
            prodPrice.clear();
            prodRemarks.clear();
            prodDesc.clear();
            prodImage.setImage(null);
            isEditMode = false; // Set flag to false since it's in add mode
        }
       
       
    
        prodID.setText(String.valueOf(productData.getProductID()));
        prodName.setText(productData.getProductName());
        prodPrice.setText(String.valueOf(productData.getPrice()));
        prodRemarks.setText(productData.getRemarks());
        prodDesc.setText(productData.getDescription());
      
        
       
       
    }
   
   private Image convertBlobToImage(Blob blob) {
        if (blob != null) {
            try (InputStream inputStream = blob.getBinaryStream()) {
                return new Image(inputStream);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
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
   
     private void insertProductInDatabase(String ProductName, double Price, String Description, String Remarks, byte[] Image) throws SQLException {
       
        Connection connection = DatabaseManager.getConnection();

        String query = "INSERT INTO products (ProductName, Price, Description, Remarks, Image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ProductName);
            preparedStatement.setDouble(2, Price);
            preparedStatement.setString(3, Description);
            preparedStatement.setString(4, Remarks);
            preparedStatement.setBytes(5, Image);

            preparedStatement.executeUpdate();
        }
    }
     
    private void updateProductInDatabase(int productId, String productName, double price, String description, String remarks, byte[] image) throws SQLException {
        Connection connection = DatabaseManager.getConnection();

        String query = "UPDATE products SET ProductName=?, Price=?, Description=?, Remarks=?, Image=? WHERE ProductID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, remarks);
            preparedStatement.setBytes(5, image);
            preparedStatement.setInt(6, productId);

        preparedStatement.executeUpdate();
    }
}

     private void ClearFields(){
         prodID.setText(null);
         prodName.setText(null);
         prodPrice.setText(null);
         prodRemarks.setText(null);
         prodDesc.setText(null);
         prodImage.setImage(null);
     
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
        String remarks = prodRemarks.getText();
        String description = prodDesc.getText();
        byte[] prodImageBytes = ImageToByteArray();

        if (productName.isEmpty() || priceText.isEmpty() || remarks.isEmpty() || description.isEmpty() || isImageViewEmpty(prodImage)) {
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
                updateProductInDatabase(existingProductData.getProductID(), productName, price, remarks, description, prodImageBytes);
                msgconfirmation.setText(" Product updated successfully");
                msgconfirmation.setStyle("-fx-text-fill: green;");
                ClearFields();
                
            } else {
                insertProductInDatabase(productName, price, remarks, description, prodImageBytes);
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
   
    
}
