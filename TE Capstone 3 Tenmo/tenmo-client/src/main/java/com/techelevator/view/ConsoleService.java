package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import com.techelevator.tenmo.models.Transfer;

public class ConsoleService {
	double transferAmount;
	String strTransfer;
	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}
	
	public Transfer makeApprovalArray(List<Transfer> pending) {
		Integer[] transferIds = new Integer[pending.size()];
		for (int i = 0; i < pending.size(); i++) {
			transferIds[i] = pending.get(i).getTransferId();
			}
		int chosenTransferId = (int) getChoiceFromOptions(transferIds);
		//i have the iD i want but now i need to match it to the right transfer
		for (Transfer t : pending) {
			if(chosenTransferId == t.getTransferId()) {
				return t;
			}
		}
		Transfer menuBroke = new Transfer();
		return menuBroke;
	}
	
	
	public double getTransferAmount() {
		transferAmount = 0;
		System.out.println("How much would you like to transfer?");
		strTransfer = in.nextLine();
		try {
			transferAmount = Double.parseDouble(strTransfer);
			
		} catch (NumberFormatException e) {
			System.out.println("That isn't a number ");
			getTransferAmount();
		} 
		if(transferAmount < 0) {
			System.out.println("Enter a positive amount.");
			getTransferAmount();
		}
		return transferAmount;
	}
	
	public int getTransferId() {
		int transferId = 0;
		System.out.println("What transfer ID would you like to search?");
		String userInput = in.nextLine();
		try {
			transferId = Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			System.out.println("That isn't a valid number ");
			getTransferId();
		} 
		return transferId;
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}
}
