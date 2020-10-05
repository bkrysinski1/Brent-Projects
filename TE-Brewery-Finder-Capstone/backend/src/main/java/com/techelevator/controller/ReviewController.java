package com.techelevator.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.ReviewDAO;
import com.techelevator.model.Beer;
import com.techelevator.model.Review;

@RestController
@CrossOrigin

public class ReviewController {
	
	private ReviewDAO reviewDao;
	
	public ReviewController(ReviewDAO reviewDao ) {
		this.reviewDao = reviewDao;
	}
	// get all reviews
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "reviews", method = RequestMethod.GET)
	public List<Review> reviewList() {
		List<Review> reviewList = reviewDao.findAllReviews();
		return reviewList;
    }
	//path to create a review
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "reviews/add", method = RequestMethod.POST)
	public Review createReview(@RequestBody Review review) {
		return reviewDao.createReview(review);
	}
	/*
	 * 	@RequestMapping(value = "/beer/{beerId}", method = RequestMethod.POST)
	public void createNewBeer(@Valid @RequestBody Beer bAdd, @PathVariable Long beerId) {
		beerDAO.create(bAdd);
	}
	 */
	
	//look up review by id
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "reviews/{id}", method = RequestMethod.GET)
	public Review lookupReview(@PathVariable Long id) {
		Review review = reviewDao.getReviewById(id);
		return review;
	}
	//look up review by beer id
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "reviews/beer/{beerId}", method = RequestMethod.GET)
	public List<Review> reviewsByBeer(@PathVariable Long beerId) {
		List<Review> reviewList = reviewDao.findReviewsByBeer(beerId);
		 return reviewList;	
	}

	//delete a review
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	    @RequestMapping(path="reviews/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable Long id) { 
	    	reviewDao.deleteReview(id);
	    }
	 
	 //look up reviews by brewery path
		@ResponseStatus(HttpStatus.OK)
		@RequestMapping(value = "brewery/{breweryId}/reviews", method = RequestMethod.GET)
		public List<Review> breweryReviewList(@PathVariable Long breweryId) {
			List<Review> reviewList = reviewDao.findReviewByBrewery(breweryId);
			return reviewList;
		}

}
