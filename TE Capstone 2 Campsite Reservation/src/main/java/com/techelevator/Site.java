package com.techelevator;

public class Site {

	private int siteID;
	private int campgroundID;
	private int siteNum;
	private int maxOccupancy;
	private Boolean accessible;
	private int maxRvLength;
	private Boolean utilities;
	private String Dailyfee;
	
	
	public String getDailyfee() {
		return Dailyfee;
	}
	public void setDailyfee(String dailyfee) {
		Dailyfee = dailyfee;
	}
	public int getSiteID() {
		return siteID;
	}
	public void setSiteID(int siteID) {
		this.siteID = siteID;
	}
	public int getCampgroundID() {
		return campgroundID;
	}
	public void setCampgroundID(int campgroundID) {
		this.campgroundID = campgroundID;
	}
	public int getSiteNum() {
		return siteNum;
	}
	public void setSiteNum(int siteNum) {
		this.siteNum = siteNum;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRvLength() {
		return maxRvLength;
	}
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	
	
	
}
