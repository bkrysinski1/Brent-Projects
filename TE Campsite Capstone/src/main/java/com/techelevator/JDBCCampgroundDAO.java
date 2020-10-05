package com.techelevator;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);	
	}

	public List <Campground> getCampByParkId(int parkId) { //find camps by park id
		
		Campground selectCamp = null;
		List <Campground> campList = new LinkedList<>();
		String sqlGetParkId = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee FROM campground WHERE park_id = ? ORDER BY campground_id";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkId, parkId);
		
		while (results.next()) {
			selectCamp = mapRowToCamp(results);
			campList.add(selectCamp);
		}
		return campList;
	}
	
	public List <Campground> getCampByCampId(int campId) { //find camps by camp id
		List <Campground> newList = new LinkedList<>();
		Campground selectCamp = null;
		String sqlGetCampId = "SELECT * FROM campground WHERE campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampId, campId);
		
		while (results.next()) {
			selectCamp = mapRowToCamp(results);
			newList.add(selectCamp);
		}
		return newList;
	}

	private Campground mapRowToCamp(SqlRowSet results) {
		Campground theCamp = new Campground();
		theCamp.setCampgroundId(results.getInt("campground_id"));
		theCamp.setName(results.getString("name"));
		theCamp.setOpenDate(results.getString("open_from_mm"));
		theCamp.setCloseDate(results.getString("open_to_mm"));
		theCamp.setDailyfee(results.getBigDecimal("daily_fee"));
		return theCamp;
	}
}
