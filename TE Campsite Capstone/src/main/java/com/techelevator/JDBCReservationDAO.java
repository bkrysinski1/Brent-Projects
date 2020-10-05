package com.techelevator;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void displayReservationMenu() { //displays reservation menu
		System.out.println("--- Reservation Menu ---");
		System.out.println();
		System.out.println("[1] Search for Available Reservation");
		System.out.println("[2] Return to campgrounds menu");
		System.out.println();
	}
	
	public Reservation createReservation(Reservation r) { //creates new reservation
		String sqlCreateReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date) VALUES (?,?,?,?,?,?)";
		r.setReservationID(getNewReservationId());
		jdbcTemplate.update(sqlCreateReservation, r.getReservationID(), r.getSiteID(), r.getName(), r.getStartDate(), r.getEndDate(), r.getCreateDate());
		return r;
	}
	
	private int getNewReservationId() { //generates new reservation id in sql
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if(nextIdResult.next()) {
		return nextIdResult.getInt(1);
		}else {
			throw new RuntimeException ("Something went wrong while getting an ID for the new reservation");
		}
	}
}