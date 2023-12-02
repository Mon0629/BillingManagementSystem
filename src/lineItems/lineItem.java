package lineItems;

import java.math.BigDecimal;

public class lineItem {
	
	private int lineItemID;
	private int billID;
	private int productID;
	private int quantity;
	private BigDecimal unitPrice;
	private BigDecimal lineItemTotal;
	
	
	
	public int getLineItemID() {
		return lineItemID;
	}
	public void setLineItemID(int lineItemID) {
		this.lineItemID = lineItemID;
	}
	public int getBillID() {
		return billID;
	}
	public void setBillID(int billID) {
		this.billID = billID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getLineItemTotal() {
		return lineItemTotal;
	}
	public void setLineItemTotal(BigDecimal lineItemTotal) {
		this.lineItemTotal = lineItemTotal;
	}
	
	
}
