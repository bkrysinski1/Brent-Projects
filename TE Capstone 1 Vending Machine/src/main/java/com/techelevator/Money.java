package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Money {
	
	private int currentBalance = 0;
	private int moneyProvided;
	private boolean isComplete = false;
	private boolean transactionValid = true;
	
//creates feedMoney method for user to input money in allowed bill amounts	
	public String feedMoney(int moneyProvided) {
//if the user amount is valid, accepts, prints amount, and updates balance		
		if(moneyProvided == 1 || moneyProvided == 2 || moneyProvided == 5 || moneyProvided == 10) {
			moneyProvided = moneyProvided * 100;
			currentBalance += moneyProvided ;
			System.out.println("$" + moneyProvided / 100 + " accepted");
			DecimalFormat df = new DecimalFormat("###.00");
//return is what prints to log.txt			
			return "$" + df.format((double)currentBalance / 100);

//if the user amount is invalid, let's them know, doesn't update balance in log
		} else {
			transactionValid = false;
			System.out.println("A problem occurred accepting your money. Please use whole bill amounts up to $10.00");
			return "";
		}
	}

//gets currentBalance * 100
	public int getCurrentBalance() {
		return currentBalance;
	}
	
	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}

//formats balance
	public String getBalanceInDollars() {
		DecimalFormat df = new DecimalFormat("###.00");
		return "$" + df.format((double)currentBalance / 100);
	}

	public int getMoneyProvided() {
		return moneyProvided;
	}

	public void setMoneyProvided(int moneyProvided) {
		this.moneyProvided = moneyProvided;
	}
	
	public void transactionComplete() {
		isComplete = true;
	}
	
	public void transactionIncomplete() {
		isComplete = false;
	}
	
	public boolean transactionValid() {
		return transactionValid;
	}

	public boolean isComplete() {
		return isComplete;
	}

//calculates change given to user	
	public String getChange(int currentBalance) {
		int nickels = 0;
		int dimes = 0;
		int quarters = 0;
		
		while (currentBalance >= 25) {
			quarters++;
			currentBalance -= 25;
		}
		while (currentBalance >= 10) {
			dimes++;
			currentBalance -= 10;
		}
		while (currentBalance >= 5) {
			nickels++;
			currentBalance -= 5;
		}
		return quarters + " quarter(s) " + dimes + " dime(s) " + nickels + " nickel(s)";
	}
	
//creates writeTransactionsLog method for log.txt	
	public void writeTransactionLog(String transactionName, String dollarAmount, String dollarBalance) {
	File logFile = new File("Log.txt");
//creates new logFile if file doesn't exist	
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
//once file exists, writes transactions to file		
		try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))){
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm:ss a");
				pw.print(formatter.format(dateTime) + " ");
				pw.print(transactionName + " ");
				pw.print(dollarAmount + " ");
				pw.println(dollarBalance);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
