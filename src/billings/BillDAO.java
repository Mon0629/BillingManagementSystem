package billings;

public interface BillDAO {
	
	Bill createBill();
	void addBill(Bill bill);
	void fetchBill();
//	void updateBill();
	void deleteBill();
	
}
