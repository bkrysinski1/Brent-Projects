package com.techelevator;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	public Park getParkByParkID(int parkID) { //search for park by park id

		Park selectedPark = null;
		String sqlGetParkId = "SELECT name, location, establish_date, area, visitors, description " + "FROM park " + "WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkId, parkID);
		while(results.next()) {
			selectedPark = mapRowToPark(results);
		}
		return selectedPark;
	}

	private Park mapRowToPark(SqlRowSet results) { //set attributes to Park
		Park thePark = new Park();
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}
