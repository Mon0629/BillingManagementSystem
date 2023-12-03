/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * FXML Controller class
 *
 * @author User
 */
public class InvoiceController implements Initializable {

    @FXML
    private AnchorPane InvoicePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RenderToPDF(ActionEvent event) {
        
        try {
	
				 PDDocument document = new PDDocument();
				 PDPage page = new PDPage();
				 document.addPage(page);
	
				 PDPageContentStream contentStream = new PDPageContentStream(document, page);
	
				 //eto yung i kokonvert mo sa image na node
				 AnchorPane invoicePane = InvoicePane;
	
				 WritableImage snapshot = invoicePane.snapshot(new SnapshotParameters(), null);
				 BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
				 PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
	
				 // suakt nya sa pdf file like location nung image
				 float pageWidth = 612; 
				 float pageHeight = 792; 
	
				 float imageWidth = 7.5f * 72; 
				 float imageHeight = 10f * 72;
	
				 // para nasa gitna yungimage
				 float x = (pageWidth - imageWidth) / 2; 
				 float y = (pageHeight - imageHeight) / 2; 
				 contentStream.drawImage(pdImage, x, y, imageWidth, imageHeight);
	
				 contentStream.close();
	
				 // kung saan mo isesave na folder
				 String outputPath = "C:\\Users\\User\\Downloads/Invoice.pdf";
				 document.save(outputPath);
	
				 document.close();
	
				 System.out.println("PDF created successfully!");
	
			 } catch (IOException e) {
				 e.printStackTrace();
		 }
    }
    
}
