package com.techelevator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;



public class JDBCParkDAOIntegrationTest {
	
	
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO parkDao;
	
	private static int TEST_PARK_ID = 5;
	
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
		
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_park_by_id() {
		Park jurassicPark = parkDao.getParkByParkID(TEST_PARK_ID);
		Assert.assertEquals("Jurassic Park", jurassicPark.getName());
		Assert.assertEquals("A park employee attempts to steal dinosaur embryos, critical security systems are shut down and it now becomes a race for survival with dinosaurs roaming freely over the island.", jurassicPark.getDescription());
	}
	
}
