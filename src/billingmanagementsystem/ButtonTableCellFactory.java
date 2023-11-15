package billingmanagementsystem;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import customer.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import customer.CustomerDAOImpl;

public class ButtonTableCellFactory implements Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> {
    
	CustomerDAOImpl customerDAO = new CustomerDAOImpl();
	
	@Override
    public TableCell<Customer, Boolean> call(TableColumn<Customer, Boolean> param) {
        return new ButtonTableCell();
    }

    private class ButtonTableCell extends TableCell<Customer, Boolean> {
        final Button deleteButton = new Button("Delete");

        ButtonTableCell() {
            deleteButton.setOnAction(event -> {
                Customer customer = getTableView().getItems().get(getIndex());
                // Call a method to handle the delete operation
                handleDelete(customer);
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(deleteButton);
            } else {
                setGraphic(null);
            }
        }

        private void handleDelete(Customer customer) {
        	customerDAO.deleteCustomer(customer.getCustomerId());
            getTableView().getItems().remove(customer);
        }
    }
}
