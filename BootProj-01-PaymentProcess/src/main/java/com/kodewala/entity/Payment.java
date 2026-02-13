package com.kodewala.entity;

public class Payment {

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Payment() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "Payment [amount=" + amount + ", description=" + description + ", bank=" + bank + ", paymentType="
				+ paymentType + "]";
	}


	private Double amount;
	private String description;
	private String bank;
	private String paymentType;
	
	
}
