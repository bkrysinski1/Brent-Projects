package com.techelevator;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCReservationDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO dao;
	private JDBCParkDAO parkDao;
	private JDBCReservationDAO resDao;
	private JDBCSiteDAO siteDao;
	private static int TEST_CAMP_ID = -15;
	private static int TEST_PARK_ID = -5;
	private static String TEST_CAMP_NAME = "Test Camp";
	private static String TEST_OPEN_DATE = "Fe";
	private static String TEST_CLOSE_DATE = "Dec";
	private static BigDecimal TEST_MONEY = new BigDecimal(15.0);
	private static String TEST_PARK_NAME = "Jurassic Park";
	private static String TEST_LOCATION = "Isla Nublar";
	private static LocalDate TEST_EST_DATE = LocalDate.of(1900, Month.APRIL, 1);
	private static int TEST_AREA = 24;
	private static int TEST_VISITORS = 1;
	private static String TEST_DESCRIPTION = "A park employee attempts to steal dinosaur embryos, critical security systems are shut down and it now becomes a race for survival with dinosaurs roaming freely over the island.";
	private static int TEST_SITE_ID = -1;
	private static int TEST_SITE_NUMBER = -1;
	private static int TEST_OCCUPANCY = 5;
	private static boolean TEST_ACCESSIBLE = false;
	private static int TEST_RV = 5;
	private static boolean TEST_UTILITIES = true;
	private static int TEST_RESERVATION = -1;
	private static String TEST_RES_NAME = "Griswold";
	private static LocalDate FROM_DATE = LocalDate.of(2020, Month.APRIL, 5);
	private static LocalDate TO_DATE = LocalDate.of(2020, Month.APRIL, 8);
	private static LocalDate CREATE_DATE = LocalDate.now();
	
	
	
	
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		
		String sqlParkInsert = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) " + "VALUES (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcParkTemplate = new JdbcTemplate(dataSource);
		jdbcParkTemplate.update(sqlParkInsert, TEST_PARK_ID, TEST_PARK_NAME, TEST_LOCATION, TEST_EST_DATE, TEST_AREA, TEST_VISITORS, TEST_DESCRIPTION);
		parkDao = new JDBCParkDAO(dataSource);
		
		String sqlCampInsert  = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) " + "VALUES (?,?,?,?,?,?)";
		jdbcParkTemplate.update(sqlCampInsert, TEST_CAMP_ID, TEST_PARK_ID, TEST_CAMP_NAME, TEST_OPEN_DATE, TEST_CLOSE_DATE, TEST_MONEY);
		dao = new JDBCCampgroundDAO(dataSource);
		
		String sqlSiteInsert = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) " + "VALUES (?,?,?,?,?,?,?)";
		jdbcParkTemplate.update(sqlSiteInsert, TEST_SITE_ID, TEST_CAMP_ID, TEST_SITE_NUMBER, TEST_OCCUPANCY, TEST_ACCESSIBLE, TEST_RV, TEST_UTILITIES);
		siteDao = new JDBCSiteDAO(dataSource);
		
		String sqlReserveInsert = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date) " + "VALUES (?,?,?,?,?,?)";
		jdbcParkTemplate.update(sqlReserveInsert, TEST_RESERVATION, TEST_SITE_ID, TEST_RES_NAME, FROM_DATE, TO_DATE, CREATE_DATE);
		resDao = new JDBCReservationDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void create_new_reservation() {
		Reservation testRes = getReservation(TEST_RESERVATION, TEST_SITE_ID, TEST_RES_NAME, FROM_DATE, TO_DATE, CREATE_DATE);	
		resDao.createReservation(testRes);
		assertNotNull(testRes);
	}
	
	@Test
	public void generate_res_id() {
		Reservation testRes = getReservation2(TEST_SITE_ID, TEST_RES_NAME, FROM_DATE, TO_DATE, CREATE_DATE);
		resDao.createReservation(testRes);
		assertNotNull(testRes);
	}
	
	@Test
	public void display_res_menu() {
		resDao.displayReservationMenu();
	}
	
	private Reservation getReservation(int reservation_id, int site_id, String name, LocalDate from_date, LocalDate to_date, LocalDate create_date) {
		Reservation testRes = new Reservation();
		testRes.setCreateDate(CREATE_DATE);
		testRes.setEndDate(TO_DATE);
		testRes.setName(TEST_RES_NAME);
		testRes.setReservationID(TEST_RESERVATION);
		testRes.setSiteID(TEST_SITE_ID);
		testRes.setStartDate(FROM_DATE);
		return testRes;
	}
	private Reservation getReservation2(int site_id, String name, LocalDate from_date, LocalDate to_date, LocalDate create_date) {
		Reservation testRes = new Reservation();
		testRes.setCreateDate(CREATE_DATE);
		testRes.setEndDate(TO_DATE);
		testRes.setName(TEST_RES_NAME);
		testRes.setSiteID(TEST_SITE_ID);
		testRes.setStartDate(FROM_DATE);
		return testRes;
	}
	
	
	
	
}
