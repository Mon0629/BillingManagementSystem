package billingmanagementsystem;


import java.sql.*;
import databaseSQL.DatabaseManager;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class ButtonCellFactoryInvoice implements Callback<TableColumn<ManageInvoiceData, Boolean>, TableCell<ManageInvoiceData, Boolean>> {

    @Override
    public TableCell<ManageInvoiceData, Boolean> call(TableColumn<ManageInvoiceData, Boolean> param) {
         return new ButtonTableCell();
    }
    
    private class ButtonTableCell extends TableCell<ManageInvoiceData, Boolean> {
        final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/Graphics/bin.png")));
        final ImageView updateIcon = new ImageView(new Image(getClass().getResourceAsStream("/Graphics/pen.png")));

        ButtonTableCell() {
           
            deleteIcon.setOnMouseClicked(event -> {
                ManageInvoiceData invoicedata = getTableView().getItems().get(getIndex());
                
                handleDelete(invoicedata);
            
            });

            
            updateIcon.setOnMouseClicked(event -> {
                ManageInvoiceData invoicedata = getTableView().getItems().get(getIndex());
                
                handleUpdate(invoicedata);
            });

            // Set icons side by side
            HBox buttonHBox = new HBox(deleteIcon, updateIcon);
            HBox.setMargin(deleteIcon, new Insets(0, 5, 0, 0));
            setGraphic(buttonHBox);
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(new HBox(deleteIcon, updateIcon));
            } else {
                setGraphic(null);
            }
        }

     private void handleDelete(ManageInvoiceData invoicedata) {
    try {
        int custID = invoicedata.getCustomerID();
        int billID = invoicedata.getBillID(); // Assume you have a method to get billID
        Connection connection = DatabaseManager.getConnection();


        try (PreparedStatement deleteLineItemsStatement = connection.prepareStatement("DELETE FROM lineItems WHERE billID = ?")) {
            deleteLineItemsStatement.setInt(1, billID);
            deleteLineItemsStatement.executeUpdate();
        }

        try (PreparedStatement deleteBillsStatement = connection.prepareStatement("DELETE FROM bills WHERE customerID = ? AND billID = ?")) {
            deleteBillsStatement.setInt(1, custID);
            deleteBillsStatement.setInt(2, billID);
            deleteBillsStatement.executeUpdate();
        }


        getTableView().getItems().remove(invoicedata);

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



      private void handleUpdate(ManageInvoiceData invoicedata) {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ProductDetails.fxml"));
                
                
                Parent parent = loader.load();
                BillingsController billingsController = loader.getController();
                billingsController.setInvoiceData(invoicedata);
                
                
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            } catch (IOException ex)
            {
                java.util.logging.Logger.getLogger(ButtonCellFactoryInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
    
    
}
    }
}
