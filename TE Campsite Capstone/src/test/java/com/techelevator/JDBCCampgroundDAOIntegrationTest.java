package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCCampgroundDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO dao;
	private JDBCParkDAO parkDao;
	private static int TEST_CAMP_ID = 15;
	private static int TEST_PARK_ID = 5;
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
		
		
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void returns_camp_by_park_id() {
		Campground testCampground = getCampground(TEST_CAMP_ID, TEST_PARK_ID, TEST_CAMP_NAME, TEST_OPEN_DATE, TEST_CLOSE_DATE, TEST_MONEY);
		List<Campground> results = dao.getCampByParkId(TEST_PARK_ID);
		assertEquals(1,results.size());
		Campground test = results.get(0);
		assertCampgroundsAreEqual(testCampground, test);
	}	
	
	@Test
	public void returns_camp_by_camp_id() {
		Campground testCampground = getCampground(TEST_CAMP_ID, TEST_PARK_ID, TEST_CAMP_NAME, TEST_OPEN_DATE, TEST_CLOSE_DATE, TEST_MONEY);
		List<Campground> results = dao.getCampByCampId(TEST_CAMP_ID);
		assertNotNull(results);
		Campground test = results.get(0);
		assertCampgroundsAreEqual(testCampground, test);
	}
	
	private void assertCampgroundsAreEqual(Campground expected, Campground actual) {
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getOpenDate(), actual.getOpenDate());
		assertEquals(expected.getCloseDate(), actual.getCloseDate());
	}
	
	private Campground getCampground(int campground_id, int park_id, String name, String open_from_mm, String open_to_mm, BigDecimal daily_fee) {
		Campground testCamp = new Campground();
		testCamp.setCampgroundId(campground_id);
		testCamp.setCloseDate(open_to_mm);
		testCamp.setDailyfee(daily_fee);
		testCamp.setName(name);
		testCamp.setOpenDate(open_from_mm);
		return testCamp;
	}
	private Park getPark(int park_id, String name, String location, LocalDate establish_date, int area, int visitors, String description) {
		Park testPark = new Park();
		testPark.setParkID(park_id);
		testPark.setName(name);
		testPark.setLocation(location);
		testPark.setEstablishDate(establish_date);
		testPark.setArea(area);
		testPark.setVisitors(visitors);
		testPark.setDescription(description);
		return testPark;
	}

	
}
