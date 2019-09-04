
public class Customer {

	private char type; //W- Wholesale R - Retail
	private String CustomerName;
	private String Contact;
	private String Address;
	private double Debt;
	
	public Customer(String name,String contact, String Add, double debt){
		
		this.CustomerName = name;
		this.Contact = contact;
		this.Address = Add;
		this.Debt = debt;
		
	}
	
	public String getCustomerName(){
		return CustomerName;
	}
	
	public String getContact(){
		return Contact;
	}
	
	public String getAddress(){
		return Address;
	}
	
	public double getDebt(){
		return Debt;
	}
	
	public boolean isWholesaler(){
		return (type == 'W');
	}
	
	public boolean isRetailer(){
		return (type == 'R');
	}
	
	public char getType(){
		return type;
	}
	
	public void newBill(double Total){
		this.Debt += Total;
	}
	
	public void PaymentRecieved(double amount){
		this.Debt -= amount;
	}
	
	public String toString(){
		
		String obj = "Customer Name : " + CustomerName;
		obj += "\n" + "Contact : " + Contact;
		obj += "\nAddress : " + Address;
		obj += "\nDebt. : " + Debt;
		
		return obj;
		
	}
	
}
