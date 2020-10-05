package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

	public class VendingMachine {
//imports inventory file	
	private boolean isValid = true;
	List <Inventory> inventoryList = new ArrayList<Inventory>();
	String path = "vendingmachine.csv";
	File textFile = new File(path);
	
	
	public VendingMachine() {
//puts file into inventoryArray lines, separated by |
//and adds the feature to each inventory object by index in inventoryArray		
		try (Scanner fileScanner = new Scanner(textFile)){
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String [] inventoryArray = line.split("\\|");
				int price = (int) (Double.parseDouble(inventoryArray[2]) * 100);
				inventoryList.add(new Inventory(inventoryArray[0], inventoryArray[1], price, 5));
			}
		} catch (Exception e) {
			
		}
	}
	
//creates displayInventory method to display inventory names, id, and quantity or SOLD OUT on the main menu
	public List<String> displayInventory(){
		
		List <String> displayList = new ArrayList<String>();
		
			for (Inventory i : inventoryList) {
				if (i.isSoldOut()) {
					displayList.add(i.getSlotId() + " " + i.getName() + " | SOLD OUT");
				} else {
				displayList.add(i.getSlotId() + " " + i.getName() + " | Quantity Remaining: " + i.getQuantity());
				}
			}
		return displayList;
	}

//creates selectionDisplay method to display inventory slot id, names, and price on the purchase menu
	public List<String> selectionDisplay(){
		List <String> selectionList = new ArrayList<String>();
		
			for (Inventory i : inventoryList) {
				selectionList.add(i.getSlotId() + " " + i.getName() + " " + i.getPrice());
				
			}
		return selectionList;
	}
	
//tells transaction log.txt to print or not print based on whether the transaction is valid	
	public boolean isValid() {
		return isValid;
	}
	
//creates buyProduct method for user to purchase products	
	public void buyProduct(Money m, String slotId) {
//checks to see if user enters an invalid product code		
		if (!slotId.equals("A1") && !slotId.equals("A2") && !slotId.equals("A3") && !slotId.equals("A4") && !slotId.equals("B1") && !slotId.equals("B2") && !slotId.equals("B3") && !slotId.equals("B4") && !slotId.equals("C1") && !slotId.equals("C2") && !slotId.equals("C3") && !slotId.equals("C4") && !slotId.equals("D1") && !slotId.equals("D2") && !slotId.equals("D3") && !slotId.equals("D4")) {
			System.out.println();
			System.out.println("Please enter a valid product code");
			isValid = false;
		}

//compares user input code to inventoryList code
		Inventory currentItem;
		for (Inventory i : inventoryList) {
			 if (i.getSlotId().equals(slotId)) {
				 currentItem = i;
			
//stops transaction if item is sold out				 
				if (currentItem.isSoldOut()) {
					isValid = false;
				
					System.out.println();
					System.out.println("This item is sold out");
					System.out.println();
//stops transaction if insufficient funds					
				} else if (currentItem.getPriceInt() > m.getCurrentBalance()) {
					isValid = false;
					
					System.out.println();
					System.out.println("Insufficient Funds");
					System.out.println();
//begins purchase transaction and decrementQuantity				
				} else if (currentItem.getPriceInt() <= m.getCurrentBalance()) {
					isValid = true;
					currentItem.decrementQuantity();
					m.setCurrentBalance(m.getCurrentBalance() - currentItem.getPriceInt());
					System.out.println();
					System.out.println("Item Purchased: " + currentItem.getName() + " " + currentItem.getPrice());
					System.out.println("Remaining Balance: " + m.getBalanceInDollars());
//prints the message for the type of item user chooses				
					if (currentItem.getSlotId().contains("A")) {
						System.out.println("Crunch Crunch, Yum!");
						System.out.println();
					} else if (currentItem.getSlotId().contains("B")) {
						System.out.println("Munch Munch, Yum!");
						System.out.println();
					} else if (currentItem.getSlotId().contains("C")) {
						System.out.println("Glug Glug, Yum!");
						System.out.println();
					} else if (currentItem.getSlotId().contains("D")) {
						System.out.println("Chew Chew, Yum!");
						System.out.println();
					}
				
				}	
	
			} 

		}
	
	}

}	
