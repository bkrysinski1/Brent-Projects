package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.BreweryDAO;
import com.techelevator.model.Brewery;

@RestController
@CrossOrigin
public class BreweryController {

	private BreweryDAO breweryDAO;
	
	public BreweryController(BreweryDAO breweryDAO ) {
		this.breweryDAO = breweryDAO;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "brewery/registration", method = RequestMethod.POST)
	public Brewery createBrewery(@RequestBody Brewery brewery) {
		return breweryDAO.create(brewery);
	}
	
	//Get List of Breweries
	@ResponseStatus(HttpStatus.OK) 
	@RequestMapping(value = "/brewery", method = RequestMethod.GET)
	public List<Brewery> breweryList() {
		List<Brewery> breweryList = breweryDAO.findAll();
		return breweryList;
    }
	
	//Get One Brewery by ID
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/brewery/{id}", method = RequestMethod.GET)
	public Brewery getBreweryInfo(@PathVariable Long id) {
		Brewery brewery = breweryDAO.getBreweryById(id);
		return brewery;
	}
	
	//Adds new Brewery to Table
	@RequestMapping(value = "/brewery/{breweryId}", method = RequestMethod.POST)
	public void createNewBrewery(@Valid @RequestBody Brewery bAdd, @PathVariable Long breweryId) {
		breweryDAO.create(bAdd);
	}
	
	//Update Brewery info
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/brewery/{breweryId}", method = RequestMethod.PUT)
	public Brewery updateBreweryInfo(@PathVariable Long breweryId,@Valid @RequestBody Brewery bUpdate) {
		return breweryDAO.update(bUpdate, breweryId);
	}
	
	//Update Brewery info
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/brewery/{breweryId}/brewer/{brewerId}", method = RequestMethod.PUT)
	public Brewery updateBreweryInfo(@PathVariable Long breweryId, @PathVariable Long brewerId) {
		return breweryDAO.updateBrewer(breweryId,brewerId);
	}
	
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path="brewery/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) { 
    	breweryDAO.delete(id);
    }

}
