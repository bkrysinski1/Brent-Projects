package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.BeerDAO;
import com.techelevator.model.Beer;

@RestController
@CrossOrigin
public class BeerController {
	
	private BeerDAO beerDAO;
	
	public BeerController(BeerDAO beerDAO) {
		this.beerDAO = beerDAO;
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'BREWER')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "beer/registration", method = RequestMethod.POST)
	public Beer createBeer(@RequestBody Beer beer) {
		return beerDAO.create(beer);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/beer", method = RequestMethod.GET)
	public List<Beer> beerList() {
		List<Beer> beerList = beerDAO.findAll();
		return beerList;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "brewery/{breweryId}/beer")
	public List<Beer> beerListByBrewery(@PathVariable Long breweryId) {
		 return beerDAO.getBeerByBreweryId(breweryId);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/beer/{beerId}", method = RequestMethod.GET) 
	public Beer updateBeerInfo(@PathVariable Long beerId) {
		Beer beer = beerDAO.getBeerById(beerId);
		return beer;
	}
	
	@RequestMapping(value = "/beer/{beerId}", method = RequestMethod.POST)
	public void createNewBeer(@Valid @RequestBody Beer bAdd, @PathVariable Long beerId) {
		beerDAO.create(bAdd);
	}
	
	@RequestMapping(value = "/beer/{beerId}", method = RequestMethod.PUT)
	public Beer updateBeerInfo(@PathVariable Long beerId, @Valid @RequestBody Beer bUpdate) {
		return beerDAO.update(bUpdate, beerId);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "beer/{beerId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long beerId) {
		beerDAO.delete(beerId);
	}

}
