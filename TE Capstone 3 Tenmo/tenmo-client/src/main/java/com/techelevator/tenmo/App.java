package com.techelevator.tenmo;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_VIEW_A_TRANSFER = "View one of your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_VIEW_A_TRANSFER, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String YES_NO_MENU_YES = "Yes";
	private static final String YES_NO_MENU_NO = "No";
	private static final String APPROVAL_MENU_APPROVE = "Approve";
	private static final String APPROVAL_MENU_REJECT = "Reject";
	private static final String[] YES_NO_MENU = {YES_NO_MENU_YES, YES_NO_MENU_NO};
	private static final String[] APPROVAL_MENU = {APPROVAL_MENU_APPROVE, APPROVAL_MENU_REJECT};
	
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (MAIN_MENU_OPTION_VIEW_A_TRANSFER.equals(choice)) {
				viewATransfer();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		System.out.println("------------------------------------");
		System.out.println(
				"Your current balance is $" + String.format("%.2f", authenticationService.getUserBalance(currentUser)));
		System.out.println("------------------------------------");
	}

	private void viewTransferHistory() {
		Transfer[] myTransfers = authenticationService.getMyTransfers(currentUser);
		User[] allUsers = authenticationService.getAllUsers(currentUser);
		
		if(myTransfers.length < 1) {
			System.out.println();
			System.out.println("You have no transfer history! Send some money.");
			mainMenu();
		}
		System.out.println(sFormat("ID", 10) + sFormat("sent/requested", 20) + sFormat("status", 15) + sFormat("amount", 15)
				+ sFormat("sender", 20) + "receiver");
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Transfer t : myTransfers) {
			System.out.println(sFormat(Integer.toString(t.getTransferId()), 10) + sFormat(transferTypeToString(t.getTransferTypeId()), 20)
					+ sFormat(transferStatusToString(t.getTransferStatusId()), 15) + "$"
					+ sFormat(String.format("%.2f", t.getAmount()), 15)
					+ sFormat(getUsername(allUsers, t.getAccountFrom()), 20) + getUsername(allUsers, t.getAccountTo()));
		}
		System.out.println("-----------------------------------------------------------------------------------------");
	}

	//Transfer[] 
	private void viewPendingRequests() {
		//make two lists to differentiate
		List<Transfer> sentPending = new ArrayList<>();
		List<Transfer> incomingPending = new ArrayList<>();
		User[] allUsers = authenticationService.getAllUsers(currentUser);
		Transfer updateTransfer = new Transfer();
		int myId = currentUser.getUser().getId();
		
		//get the transfer Array.  
		Transfer[] myPendings = authenticationService.getMyTransfers(currentUser);
		//for loop to go through and establish which one is in sent-list and received-list
		for(Transfer t : myPendings) {
			if (t.getAccountFrom() == myId && t.getTransferStatusId() == 1) {
				incomingPending.add(t);
			} else if (t.getAccountTo() == myId && t.getTransferStatusId() == 1) {
				sentPending.add(t);
			}
		}
		//print
		if (sentPending.size() > 0) {
			System.out.println("Here are the pending requests you sent:");
			System.out.println("---------------------------------------");
			// print header and details of each
			System.out.println(sFormat("Transfer ID", 15) + sFormat("amount", 15) + "sender");
			for (Transfer sentT : sentPending) {
				System.out.println(sFormat(Integer.toString(sentT.getTransferId()), 15) + "$"
						+ sFormat(String.format("%.2f", sentT.getAmount()), 15)
						+ getUsername(allUsers, sentT.getAccountFrom()));
			}
		} else {
			System.out.println("You have 0 sent requests that are pending.");
		}
		if (incomingPending.size() > 0) {
			System.out.println("Here are the pending requests you have received:");
			System.out.println("------------------------------------------------");
			// print header and details of each
			System.out.println(sFormat("Transfer ID", 15) + sFormat("amount", 15) + "Requester");
			for (Transfer incT : incomingPending) {
				System.out.println(sFormat(Integer.toString(incT.getTransferId()), 15) + "$"
						+ sFormat(String.format("%.2f", incT.getAmount()), 15)
						+ getUsername(allUsers, incT.getAccountTo()));

			}
			// if the received list isnt 0, console serv. ask if they want to approve one (Y/N), then send to console service method to pick which one to approve

			System.out.println("Would you like to process any of these requests right now?");
			String decision = (String) console.getChoiceFromOptions(YES_NO_MENU);
			if (decision.equals(YES_NO_MENU_YES)) {
				System.out.println("Okay. Which request ID would you like to accept or reject?");

				Transfer transferToUpdate = console.makeApprovalArray(incomingPending);
				System.out.println("Approve or Reject this request?");
				String approveOrRejectChoice = (String) console.getChoiceFromOptions(APPROVAL_MENU);
				double myBalance = authenticationService.getUserBalance(currentUser);
				if (approveOrRejectChoice.equals(APPROVAL_MENU_APPROVE)
						&& myBalance < transferToUpdate.getAmount()) {
					System.out.println("Sorry, you don't have the funds to complete this request.");
					mainMenu();
				}
				int transferStatusToUpdate = transferStatusToInt(approveOrRejectChoice);
				transferToUpdate.setTransferStatusId(transferStatusToUpdate);
				try {
					authenticationService.finalizeRequest(currentUser, transferToUpdate);
					System.out.println("Process complete! Thank you.");
				} catch (AuthenticationServiceException e) {
					System.out.println(e.getMessage());
				}
			}

		} else {
			System.out.println("You have 0 incoming pending requests.");
		}		
	}
	
	private void viewATransfer() {
		//get all users
		User[] allUsers = authenticationService.getAllUsers(currentUser);
		//get all transactions
		Transfer[] myTransfers = authenticationService.getMyTransfers(currentUser);
		List<Integer> myTransferIds = new ArrayList<>();
		for(Transfer transfer : myTransfers) {
			myTransferIds.add(transfer.getTransferId());
		}
		
		//ask for id 
		int transferId = console.getTransferId();
		//WE NEED TO check if id is valid ... later
		if(!myTransferIds.contains(transferId)) {
			System.out.println("This is an invalid transfer ID.");
			mainMenu();
		}
		//call that method with that id
		System.out.println();
		System.out.println("-----------------------------------");
		
		Transfer t = new Transfer();;
		try {
			t = authenticationService.requestTransferInfoById(currentUser, transferId);
			System.out.println("Transfer id: " + t.getTransferId());
			System.out.println("From: " + getUsername(allUsers, t.getAccountFrom()));
			System.out.println("To: " + getUsername(allUsers, t.getAccountTo()));
			System.out.println("Type: " + transferTypeToString(t.getTransferTypeId()));
			System.out.println("Status: " + transferStatusToString(t.getTransferStatusId()));
			System.out.println("Amount: $" + String.format("%.2f", t.getAmount()));
		} catch (AuthenticationServiceException e) {
			System.out.println(e);
		}
		System.out.println("-----------------------------------");
	}

	private void sendBucks() {
		System.out.println("---------------------------------------------------------------");
		System.out.println("Who would you like to send TE bucks to? Please choose a number.");
		User[] allUsers = authenticationService.getAllUsers(currentUser);
		String[] usernames = new String[allUsers.length - 1];
		double myBalance = authenticationService.getUserBalance(currentUser);
		
		//dont want self in the list of usernames
		int myId = currentUser.getUser().getId();
		String myUsername =  currentUser.getUser().getUsername();
		// get every username except your own
		// i is new array , j is old array,  only increase i if we add to it, always increase j
		int j = 0;
		for(int i = 0; i < usernames.length; j++) {
			if (!allUsers[j].getUsername().equals(myUsername)) {
				usernames[i] = allUsers[j].getUsername();
				i++;
			}	
		}
		
		String choice = (String)console.getChoiceFromOptions(usernames);
		// make sure they have enough
		double transferAmount = console.getTransferAmount();
		if(transferAmount > myBalance) {
			System.out.println("You have insufficient funds for this transaction.");
			mainMenu();
		}
		//get user where name is "choice"
		
		Transfer newTransfer = new Transfer();
		newTransfer.setAmount(transferAmount);
		newTransfer.setTransferTypeId(2);
		newTransfer.setAccountFrom(myId);
		newTransfer.setAccountTo(authenticationService.getUserByUsername(currentUser, choice));
		newTransfer.setTransferStatusId(2);
		
		double receipt = authenticationService.sendTransfer(currentUser, newTransfer);
		System.out.println();
		System.out.println("--------------------------------------------------------");
		System.out.println("Confirmation: $" + String.format("%.2f", receipt) + " was sent to " + choice);
		System.out.println("--------------------------------------------------------");
	}

	private void requestBucks() {
		User[] allUsers = authenticationService.getAllUsers(currentUser);
		String[] usernames = new String[allUsers.length - 1];
		//dont want self in the list of usernames
		int myId = currentUser.getUser().getId();
		String myUsername =  currentUser.getUser().getUsername();
		// get every username except your own
		// i is new array , j is old array,  only increase i if we add to it, alway increase j
		int j = 0;
		for(int i = 0; i < usernames.length; j++) {
			if (!allUsers[j].getUsername().equals(myUsername)) {
				usernames[i] = allUsers[j].getUsername();
				i++;
			}	
		}
		String choice = (String)console.getChoiceFromOptions(usernames);
		double transferAmount = console.getTransferAmount();
		Transfer newTransfer = new Transfer();
		newTransfer.setAmount(transferAmount);
		newTransfer.setTransferTypeId(1);
		newTransfer.setAccountFrom(authenticationService.getUserByUsername(currentUser, choice));
		newTransfer.setAccountTo(myId);
		newTransfer.setTransferStatusId(1);
		
		Transfer receipt = authenticationService.sendRequest(currentUser, newTransfer);
		System.out.println();
		System.out.println("--------------------------------------------------------");
		System.out.println("Pending request: $" + String.format("%.2f", receipt.getAmount()) + " from " + choice);
		System.out.println("--------------------------------------------------------");
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println();
            	System.out.println("-------------------------------------------");
            	System.out.println("Registration successful. You can now login.");
            	System.out.println("-------------------------------------------");
            } catch(AuthenticationServiceException e) {
            	System.out.println();
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
            	System.out.println();
				System.out.println("Please attempt to register again.");
            }
        }
	}
	

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println();
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println();
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
	
	public String getUsername(User[] allUsers, int id){
		for (User user : allUsers){
			if(user.getId() == id){
				return user.getUsername();
			}
		}
		return "private";
	}
	
	private String transferStatusToString(int status) {
		if (status == 1){
			 return "pending";
		} if (status == 2) {
			return "approved";
		} else { 
			return "rejected!";
		}
	}
	public String sFormat(String word, int finalLength) {
		return (word + "                           ").substring(0, finalLength);
	}
	
	public int transferStatusToInt(String decision) {
		if (decision.equals(APPROVAL_MENU_APPROVE)) {
			return 2;
		}
		if (decision.equals(APPROVAL_MENU_REJECT)) {
			return 3;
		}
		else {
			return 1;
		}
	}
	
	
	public String transferTypeToString(int typeid){
		if (typeid == 1){
		 return "request";
	} else { 
		return "sent";
	}
	}

}
