package Gym.Management.papers;

import java.time.LocalDate;


public class Subscription{

	private static int id = 0;
	private int subscriptionID = 0;
	private String subscriptionType = "";
	private LocalDate startDate = null;
	private LocalDate endtDate = null;
	private double price = 0.0;
	private double discount = 0.0;
	
	////////////Constructor///////////////
	public Subscription(String subscriptionType) 
	{
		this.setId();
		setSubscriptionID(this.getId());
		this.setSubscriptionType(subscriptionType);
		this.setStartDate(LocalDate.now());
		switch (subscriptionType.toLowerCase()) {
		case "bronze": {
			this.setEndtDate(this.getStartDate().plusDays(30));
			break;
		}
		case "silver": {
			this.setEndtDate(this.getStartDate().plusDays(60));
			break;
		}
		case "gold": {
			this.setEndtDate(this.getStartDate().plusDays(90));
			break;
		}
		default:
			System.out.println("Unexpected value: " + subscriptionType);
		}
	}

	////////////Getters and setters////////////
	public int getId() {
		return id;
	}

	public void setId() {
		Subscription.id ++;
	}
	
	public int getSubscriptionID() {
		return subscriptionID;
	}

	private void setSubscriptionID(int subID) {
		this.subscriptionID ++;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		if(subscriptionType == null || subscriptionType == "") {
			System.out.println("Invalid Subscription Type");
		}
		else {
			this.subscriptionType = subscriptionType;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if(startDate == null) {
			System.out.println("Invalid Start Date");
		}
		else {
			this.startDate = startDate;
		}
	}

	public LocalDate getEndtDate() {
		return endtDate;
	}

	public void setEndtDate(LocalDate endtDate) {
		if(endtDate == null) {
			System.out.println("Invalid End Date");
		}
		else {
			this.endtDate = endtDate;
		}
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if(price <= 0) {
			System.out.println("Invalid value for price");
		}
		else {
			this.price = price;
		}
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		if(discount <= 0.0 || discount >= 0.5) {
			System.out.println("Invalid value for discount");
		}
		else {
			this.discount = discount;
		}
	}

	

	///////////Additional Methods///////////////
}
