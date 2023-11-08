package customer;

import java.sql.*;

public class Customer {
	private int customerId;
	private Timestamp creationDate;
	private String name;
	private String email;
	private String address;
	private String town;
	private String country;
	private int postal;
	
	Customer(int customerId, Timestamp creationDate, String name, String email, String address, String town,
			String country, int postal) {
		this.customerId = customerId;
		this.creationDate = creationDate;
		this.name = name;
		this.email = email;
		this.address = address;
		this.town = town;
		this.country = country;
		this.postal = postal;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getPostal() {
		return postal;
	}
	public void setPostal(int postal) {
		this.postal = postal;
	}
	
	
	
	
	
	
}
