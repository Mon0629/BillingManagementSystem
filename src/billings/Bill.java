package billings;

import java.sql.Date;
import java.sql.Timestamp;

public class Bill {
	
	public enum DocType{
		RECEIPT, INVOICE, BILL
	}	
	
	private int billID;
	private int customerID;
	private int shipCustomerID;
	private Timestamp issueDate;
	private Date dueDate;
	private DocType doctype;
	
	public DocType getDoctype() {
		return doctype;
	}
	public void setDoctype(DocType doctype) {
		this.doctype = doctype;
	}
	public int getBillID() {
		return billID;
	}
	public void setBillID(int billID) {
		this.billID = billID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getShipCustomerID() {
		return shipCustomerID;
	}
	public void setShipCustomerID(int shipCustomerID) {
		this.shipCustomerID = shipCustomerID;
	}
	public Timestamp getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Timestamp issueDate) {
		this.issueDate = issueDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
}
