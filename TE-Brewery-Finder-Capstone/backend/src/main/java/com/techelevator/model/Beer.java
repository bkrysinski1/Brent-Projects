package com.techelevator.model;

public class Beer {
	
	private Long id;
	private Long breweryId;
	
	public Long getBreweryId() {
		return breweryId;
	}

	public void setBreweryId(Long breweryId) {
		this.breweryId = breweryId;
	}
	private String beerName;
	private String description;
	private String url;
	private String abv;
	private String beerType;
	
	public Beer () { }
	
	public Beer(Long id, String beerName, String description, String url, String abv, String beerType) {
		this.id = id;
		this.beerName = beerName;
		this.description = description;
		this.url = url;
		this.abv = abv;
		this.beerType = beerType;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBeerName() {
		return beerName;
	}
	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAbv() {
		return abv;
	}
	public void setAbv(String abv) {
		this.abv = abv;
	}
	public String getBeerType() {
		return beerType;
	}
	public void setBeerType(String beerType) {
		this.beerType = beerType;
	}
	
	

}
