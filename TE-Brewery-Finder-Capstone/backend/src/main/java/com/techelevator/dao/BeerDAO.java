package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Beer;

public interface BeerDAO {
	
	List<Beer> findAll();
	
	public List<Beer> getBeerByBreweryId(long breweryId);
	
	Beer getBeerById (long beerId);
	
	Beer findByBeerName (String beerName);
	
	int findIdByBeerName (String beerName);
	
	public Beer create(Beer newBeer);
	
	public void save(Beer newBeer);
	
	public Beer update(Beer beer, Long id);
	
	public void delete(long id);

}
