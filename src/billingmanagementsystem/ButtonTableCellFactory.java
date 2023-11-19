	package billingmanagementsystem;

import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import customer.Customer;
import javafx.util.Callback;

import customer.CustomerDAOImpl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ButtonTableCellFactory implements Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> {
    
	CustomerDAOImpl customerDAO = new CustomerDAOImpl();
	
	@Override
    public TableCell<Customer, Boolean> call(TableColumn<Customer, Boolean> param) {
        return new ButtonTableCell();
    }

    private class ButtonTableCell extends TableCell<Customer, Boolean> {
    final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/Graphics/circle-trash.png")));
    final ImageView updateIcon = new ImageView(new Image(getClass().getResourceAsStream("/Graphics/pen-circle.png")));

    ButtonTableCell() {
        // Handle delete icon action
        deleteIcon.setOnMouseClicked(event -> {
            Customer customer = getTableView().getItems().get(getIndex());
            // Call a method to handle the delete operation
            handleDelete(customer);
        });

        // Handle update icon action
        updateIcon.setOnMouseClicked(event -> {
            Customer customer = getTableView().getItems().get(getIndex());
            // Call a method to handle the update operation
            handleUpdate(customer);
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

    private void handleDelete(Customer customer) {
        customerDAO.deleteCustomer(customer.getCustomerId());
        getTableView().getItems().remove(customer);
    }

    private void handleUpdate(Customer customer) {
        // Add your logic to handle the update operation
        // You can open a dialog or navigate to another view for updating
        System.out.println("Update icon clicked for customer: " + customer.getCustomerId());
    }
}

}
