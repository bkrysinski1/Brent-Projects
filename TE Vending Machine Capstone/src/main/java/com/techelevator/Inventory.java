package com.techelevator;

import java.text.DecimalFormat;

public class Inventory {
	
	private String slotId;
	private String name;
	private int price;
	private boolean isSoldOut = false;
	private int quantity = 5;

	public Inventory (String slotId, String name, int price, int quantity) {
		
		this.name = name;
		this.slotId = slotId;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}

	public String getSlotId() {
		return slotId;
	}

//formats price
	public String getPrice() {
		DecimalFormat df = new DecimalFormat("###.00");
		return "$" + df.format((double)price / 100);
	}
	
//gets price * 100	
	public int getPriceInt() {
		return price;
	}

	public boolean isSoldOut() {
		return isSoldOut;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
//decrementQuantity method	
	public void decrementQuantity() {
		if (quantity > 0) {
			quantity -= 1;
		} 
		if (quantity == 0) {
			isSoldOut = true;
		}
	}

	public void soldOut() {
		isSoldOut = true;
	}
	
}
