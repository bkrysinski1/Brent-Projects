package com.techelevator;

import java.util.Scanner;

public class MainMenu {

	public static void main(String[] args) {
		
//creates scanner for input and instantiates VendingMachine and Money Objects		
		Scanner inputScanner = new Scanner(System.in);
		VendingMachine vm = new VendingMachine();
		Money m = new Money();
		
		
//prints main menu
		//main menu continues displaying until user chooses to exit program
		while(true) {
		System.out.println("***********************************");
		System.out.println("Welcome to the Vendo-Matic 800!");
		System.out.println("***********************************");
		System.out.println("Main Menu");
		System.out.println("(1) Display Vending Machine Items");
		System.out.println("(2) Purchase");
		System.out.println("(3) Exit");
		System.out.println("***********************************");
		
		String menuChoice = inputScanner.nextLine();
//displays inventory		
		if ("1".equals(menuChoice)) {
				for (String s : vm.displayInventory()) {
					System.out.println(s);
				}
				System.out.println();
//brings up purchase menu				
		} else if ("2".equals(menuChoice)) {
			//while the transaction is NOT complete, program continues to display purchase menu	
			m.transactionIncomplete();
			m.setCurrentBalance(0);
			while(!m.isComplete()) {
//creates purchase menu			
			System.out.println("***********************************");
			System.out.println("Purchase Menu");
			System.out.println("(1) Feed Money");
			System.out.println("(2) Select Product");
			System.out.println("(3) Finish Transaction");
			System.out.println();
			System.out.println("Current money provided: " + m.getBalanceInDollars());
			System.out.println("***********************************");
			String purchaseChoice = inputScanner.nextLine();
//user enters money amount			
			if ("1".equals(purchaseChoice)) {
				System.out.println("Enter the amount of money you wish to feed in whole bill amounts:");
				String moneyFed = inputScanner.nextLine();
//writes feedMoney transaction to log.txt				
				m.writeTransactionLog("FEED MONEY", "$" + moneyFed + ".00", m.feedMoney(Integer.parseInt(moneyFed)));
				System.out.println();
				System.out.println();
//brings up selection menu							
			} else if("2".equals(purchaseChoice)) {
				for (String s : vm.selectionDisplay()) {
					System.out.println(s);
				}
//users inputs code				
				System.out.println();
				System.out.println("Enter product code: ");
				
				String productCodeChoice = inputScanner.nextLine();
				vm.buyProduct(m, productCodeChoice);
//if the transaction is valid, writes buyProduct to log.txt				
				if(vm.isValid()) {
					m.writeTransactionLog("ITEM SLOT " + productCodeChoice,  "   ",  " " + m.getBalanceInDollars());
				}
//exits purchase menu and gives change to user				
			} else if("3".equals(purchaseChoice)) {	
//writes getChange transaction to log.txt				
				m.writeTransactionLog("GIVE CHANGE", m.getBalanceInDollars(), "$0.00");
				System.out.println("Change dispensed: " + m.getChange(m.getCurrentBalance()));
				m.transactionComplete();
			}
			}
//exits KAB program			
		} else if ("3".equals(menuChoice)) {
			System.exit(0);
			inputScanner.close();
		}
		}
		
	}
	
}
