package billings;

import java.sql.Date;
import java.sql.Timestamp;

public class Bill {
	private int billID;
	private int customerID;
	private int shipCustomerID;
	private Timestamp issueDate;
	private Date dueDate;
	private enum docType{
		Receipt, Invoice, Bill
	}
}
