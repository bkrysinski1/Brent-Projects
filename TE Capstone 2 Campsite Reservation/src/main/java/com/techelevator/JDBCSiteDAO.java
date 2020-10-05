package com.techelevator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	public List<Site> reservationSearch(LocalDate fromDate, LocalDate toDate, int campId) { //search for existing reservation & filter out any conflicts before returning
		List<Site> reserveList = new LinkedList<Site>();
		Site availSite = new Site();
		String sqlGetReservations = 
				"SELECT (SELECT count(site_id) FROM reservation WHERE site_id = s.site_id) AS popular, s.campground_id, s.site_id, s.max_occupancy, s.accessible, s.max_rv_length, s.utilities, c.daily_fee " +
				"FROM site as s " +
				"JOIN campground as c ON c.campground_id = s.campground_id " +
				"WHERE s.site_id NOT IN " + // pull info that is not in sub query
				"(SELECT site_id FROM reservation AS r WHERE from_date BETWEEN ? AND ? AND to_date BETWEEN ? AND ?) " + //sub query locate dates in range that user sets
				"AND s.campground_id = ? " +
				"ORDER BY popular DESC " +
				"LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservations, fromDate, toDate, fromDate, toDate, campId);
		while(results.next()) {
			availSite = mapRowToSite(results);
			availSite.isAccessible();
			availSite.isUtilities();
			reserveList.add(availSite);
		}
		return reserveList;
	}
	
	private Site mapRowToSite(SqlRowSet results) { //set attributes to Site
		Site newSite = new Site();
		newSite.setSiteID(results.getInt("site_id"));
		newSite.setMaxOccupancy(results.getInt("max_occupancy"));
		newSite.setAccessible(results.getBoolean("accessible"));
		newSite.setMaxRvLength(results.getInt("max_rv_length"));
		newSite.setUtilities(results.getBoolean("utilities"));
		return newSite;
	}

}
