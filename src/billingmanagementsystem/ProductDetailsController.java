/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import databaseSQL.DatabaseManager;
import java.io.File;
import java.io.IOException;
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
    private void addproduct(ActionEvent event) {
        try
        {
            String ProductName = prodName.getText();
            double Price = Double.parseDouble(prodPrice.getText());
            String Remarks = prodRemarks.getText();
            String Description = prodDesc.getText();
            byte [] prodimage = ImageToByteArray();
            
            insertProductIntoDatabase(ProductName, Price, Remarks, Description, prodimage);
            
            prodName.setText(null);
            prodPrice.setText(null);
            prodRemarks.setText(null);
            prodDesc.setText(null);
            prodImage.setImage(null);
            
            msgconfirmation.setText("Product added successfully");
            
        
            
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent event) {
                msgconfirmation.setText(null);
            }
        }));
        timeline.play();
        
 
        } catch (IOException | SQLException ex)
        {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
   
     private void insertProductIntoDatabase(String ProductName, double Price, String Description, String Remarks, byte[] Image) throws SQLException {
       
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
     
   
    
}
