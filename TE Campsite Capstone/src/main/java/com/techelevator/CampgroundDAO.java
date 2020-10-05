package com.techelevator;

import java.util.List;

public interface CampgroundDAO {
	
	public List <Campground> getCampByParkId(int parkId);
	
	public List <Campground> getCampByCampId(int CampId);
}
