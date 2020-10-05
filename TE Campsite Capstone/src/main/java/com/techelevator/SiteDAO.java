package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

	public List<Site> reservationSearch(LocalDate fromDate, LocalDate toDate, int campId);

}
