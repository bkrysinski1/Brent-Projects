package com.techelevator.dao;

import com.techelevator.model.Brewery;
import com.techelevator.model.User;

import java.util.List;

public interface BreweryDAO {

    List<Brewery> findAll();

    Brewery getBreweryById(Long userId);

    Brewery findByBreweryName(String username);

    public Brewery create(Brewery newBrewery);
    
    public Brewery update(Brewery brewery, Long id);
    
    public void delete(Long id);
    
	public int findIdByBreweryName(String breweryName);
	
	public Brewery updateBrewer(Long breweryId, Long brewerId);
}
