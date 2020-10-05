package com.techelevator.dao;

import java.util.List;
import com.techelevator.model.Review;
import com.techelevator.model.User;


public interface ReviewDAO {
	
	Review createReview(Review newReview);
	
	List<Review> findAllReviews();
	
	Review getReviewById(Long reviewID);
	
	List<Review> findAllUsersReviews(Long userID);
	
	List<Review> findReviewsByBeer(Long beerId);
	
	List<Review> findReviewByBrewery(Long breweryId);
	
	void deleteReview(Long id);

}
