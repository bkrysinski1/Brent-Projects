package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	private ParkDAO parkDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private CampgroundDAO campgroundDAO;
	
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		parkDAO = new JDBCParkDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
	}

	public void run() { //start program with header & main menu
		displayHeading();
		displayMainMenu();
	}
	
	private void displayHeading() {
		System.out.println("*********************************");
		System.out.println("* Welcome to the National Parks *");
		System.out.println("****** Reservation System *******");
		System.out.println("*********************************");
		System.out.println();
	}
	
	private void displayMainMenu() {
	
		System.out.println("---------- Main Menu -----------");
		System.out.println();
		System.out.println("Select which park you would like to visit:");
		System.out.println();
		System.out.println("[1] " + this.parkDAO.getParkByParkID(1).getName()); //search for park name by park ID
		System.out.println("[2] " + this.parkDAO.getParkByParkID(2).getName());
		System.out.println("[3] " + this.parkDAO.getParkByParkID(3).getName());
		System.out.println("[Q] Quit");
		System.out.println();
		System.out.println("Please make selection: ");
		String userChoice = scanner.nextLine();
		System.out.println();
		if("Q".equalsIgnoreCase(userChoice)) {
			System.out.println();
			System.out.println("Thanks for using the National Parks Reservation System");
			System.out.println("We hope to see you again soon!");
			System.exit(0);
		}

		if ("1".equalsIgnoreCase(userChoice) || "2".equalsIgnoreCase(userChoice) || "3".equalsIgnoreCase(userChoice)) {
			int newChoice = Integer.parseInt(userChoice);
			Park parkCamp = new Park();
			parkCamp = this.parkDAO.getParkByParkID(newChoice);
			//display park info
			System.out.println("--- Park Information Screen ---");
			System.out.println();
			System.out.println(parkCamp.getName() + " National Park");
			System.out.println("Location:           " + parkCamp.getLocation());
			System.out.println("Established:        " + parkCamp.getEstablishDate());
			System.out.println("Area:               " + parkCamp.getArea() + " sq km");
			System.out.println("Annual Visitors     " + parkCamp.getVisitors());
			System.out.println();
			System.out.println(parkCamp.getDescription().subSequence(0, 80));
			System.out.println(parkCamp.getDescription().subSequence(80, 160));
			System.out.println(parkCamp.getDescription().subSequence(160, 240));
			System.out.println(parkCamp.getDescription().subSequence(240, parkCamp.getDescription().length()));

			campgroundsMenu(newChoice);
		}
		else { // type anything else it will bring back to main menu
			System.out.println("Not a valid selection");
			System.out.println();
			displayMainMenu();
		}
	
	}		

	public void campgroundsMenu(int campMenu) {
		System.out.println();
		System.out.println("------- Campgrounds Menu ------- ");
		System.out.println();
		System.out.println("[1] View campgrounds");
		System.out.println("[2] Search for reservation");
		System.out.println("[3] Return to main menu");
		System.out.println();
		
		System.out.println("What would you like to do? ");
		
		String userChoice = scanner.nextLine();
		
		if ("1".equals(userChoice)) {
			displayCampgrounds(campMenu);
			
			this.reservationDAO.displayReservationMenu(); // show reservation menu from jdbcreservation
			System.out.println("What would you like to do? ");
			String newChoice = scanner.nextLine();
			
			if("1".equals(newChoice)) {
				displayCampChoices(); //show campgrounds, search by park id
				System.out.println();
				displayMainMenu(); //bring back to main menu

			}
			if("2".equals(newChoice)) {
				campgroundsMenu(campMenu); 
			}
		}
		if ("2".equals(userChoice)) {
			displayCampgrounds(campMenu);
			displayCampChoices();
			System.out.println();
			displayMainMenu();
		}
		if ("3".equals(userChoice)) {
			displayMainMenu();
		}
		else {
			System.out.println("----- Incorrect selection -----");
			campgroundsMenu(campMenu);
		}
		
	}

	private void displayCampgrounds(int selectedCamp) { //campgrounds based on user input for which park
		List <Campground> camp = new LinkedList<>();
		camp = this.campgroundDAO.getCampByParkId(selectedCamp);
		System.out.println();
		for (int i=0; i<camp.size(); i++) {
			System.out.println("#" + camp.get(i).getCampgroundId() + "\t" + camp.get(i).getName() + "\tOpen: " + camp.get(i).getOpenDate() + "\tClose: " + camp.get(i).getCloseDate() + "\tDaily Fee: $" + camp.get(i).getDailyfee());
		}
		System.out.println();
	}
	
	
	private void displayCampChoices() {
		

		System.out.println();
		System.out.println("Which campground? (Enter 0 to go back to Main Menu)? ");
		String campChoice = scanner.nextLine();
		if("0".equals(campChoice)) {
			displayMainMenu();
		}
		System.out.println("What is the arrival date? (yyyy-mm-dd) "); //has to be exact...
		String arriveDate = scanner.nextLine();
		System.out.println("What is the departure date? (yyyy-mm-dd) ");
		String departDate = scanner.nextLine();
		System.out.println();
		
		long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(arriveDate),LocalDate.parse(departDate)); //calculate days in between arrive & depart date
		BigDecimal newBD = new BigDecimal(daysBetween); //make daysBetween into BigDecimal
		
		int campChoiceInt = Integer.parseInt(campChoice); // make campChoice an Int
		
		List<Campground> feeList = new LinkedList<>();
		feeList = this.campgroundDAO.getCampByCampId(campChoiceInt); //accepts int, searches camps by camp id, need for daily_fee
		
		List<Site> availableCamps = new LinkedList<Site>(); //accepts local dates, need for site attributes
		availableCamps = this.siteDAO.reservationSearch(LocalDate.parse(arriveDate), LocalDate.parse(departDate), campChoiceInt);
		
		if (availableCamps.size() == 0) {
			System.out.println("------- There are no campsites available -------");
			System.out.println("--- Please search for a different date range ---");
			displayCampChoices();
		}
		else {
		System.out.println();
		System.out.println("Sites Matching Your Search Criteria:");
		System.out.println();
		}
		
		for(int i=0; i<availableCamps.size(); i++) { // prints out list of sites ordered by popularity
			for(int j = 0; j<feeList.size(); j++) {

				System.out.println("Site No: " + availableCamps.get(i).getSiteID() 
						+ "\tMax Occup: " + availableCamps.get(i).getMaxOccupancy() 
						+ "\tMax RV Length: " + (availableCamps.get(i).getMaxRvLength() > 0 ? availableCamps.get(i).getMaxRvLength() : "N/A")
						+ "\tTotal Fee: $ " + feeList.get(j).getDailyfee().multiply(newBD) 
						+ "\tAccessible: " + (availableCamps.get(i).isAccessible() ? "Yes" : "No")
						+ "\tUtilities: " + (availableCamps.get(i).isUtilities() ? "Yes" : "No"));
				}
		}
		
		System.out.println();
		System.out.println("Which site should be reserved (0 to cancel): ");
		String siteNumber = scanner.nextLine();
		if ("0".equals(siteNumber)) {
			displayMainMenu();
		}
		System.out.println("What name should the reservation be made under: ");
		String reserveName = scanner.nextLine();
		//set Reservation attributes & call on createReservation method to generate new reservation into database
		LocalDate reserveArriveDate = LocalDate.parse(arriveDate);
		LocalDate reserveDepartDate = LocalDate.parse(departDate);
		LocalDate todaysDate = LocalDate.now();
		int siteId = Integer.parseInt(siteNumber);
		Reservation newReservation = new Reservation();
		newReservation.setCreateDate(todaysDate);
		newReservation.setEndDate(reserveDepartDate);
		newReservation.setStartDate(reserveArriveDate);
		newReservation.setName(reserveName);
		newReservation.setSiteID(siteId);
		newReservation = reservationDAO.createReservation(newReservation);
		System.out.println();
		System.out.println("Your reservation has been made under: <" + newReservation.getName() + "> and your reservation number is: <" + newReservation.getReservationID() + ">");

		
	}
}
