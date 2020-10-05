package com.techelevator;

import java.math.BigDecimal;

public class Campground {
	
	private int campgroundId;
	private String name;
	private String openDate;
	private String closeDate;
	private BigDecimal dailyfee;
	
	
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public BigDecimal getDailyfee() {
		return dailyfee;
	}
	public void setDailyfee(BigDecimal dailyfee) {
		this.dailyfee = dailyfee;
	}
	
	

}
