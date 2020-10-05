package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Brewery {

   private Long id;
   private String breweryName;
   private String contactInfo;
   private Long userId;
   private String address;
   private String history;
   private String openTime;
   private String closeTime;
   private boolean isOpen;

   public Brewery() { }

   public Brewery(Long id, String breweryName, String contactInfo, String address, String history, String openTime, String closeTime, Boolean isOpen) {
      this.id = id;
      this.breweryName = breweryName;
      this.contactInfo = contactInfo;
      this.address = address;
      this.openTime = openTime;
      this.closeTime = closeTime;
      this.history = history;
      this.isOpen = true;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getBreweryName() {
      return breweryName;
   }

   public void setBreweryName(String breweryName) {
      this.breweryName = breweryName;
   }

   public String getContactInfo() {
	return contactInfo;
	}
	
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getHistory() {
		return history;
	}
	
	public void setHistory(String history) {
		this.history = history;
	}
	
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
   @Override
   public String toString() {
      return "Brewery{" +
              "id=" + id +
              ", breweryName='" + breweryName +
              ", userId=" + userId +
              ", address=" + address +
              ", history=" + history +
              ", isOpen=" + isOpen +
              '}';
   }
}