package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Review;

@Component
public class ReviewSqlDAO implements ReviewDAO {
	
	 private JdbcTemplate jdbcTemplate;
	
	public ReviewSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public List<Review> findAllReviews() {
		  List<Review> reviews = new ArrayList<>();
	        String sql = "SELECT * FROM reviews";

	        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
	        while(results.next()) {
	            Review review = mapRowToReview(results);
	            reviews.add(review);
	        }

	        return reviews;
	}
	
	@Override
	public Review createReview(Review newReview) {
	        String insertReview = "INSERT into reviews (review_id, beer_id, title, stars, body) "
	        					+ "values(?,?,?,?,?)";
	        newReview.setId(getNextId());
	        		jdbcTemplate.update(insertReview, newReview.getId(), newReview.getBeerId(), newReview.getTitle(), newReview.getReviewStars(), newReview.getReviewBody());
	        return newReview;
	}
	

	@Override
	public Review getReviewById(Long reviewId) {
		String sql = "SELECT * FROM reviews WHERE review_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
		if(results.next()) {
			return mapRowToReview(results);
		} else {
			throw new RuntimeException("Review( ID "+reviewId+" ) was not found.");
		}
	}

	@Override
	public List<Review> findReviewsByBeer(Long beerId) {
		List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE beer_id= ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, beerId);
        while(results.next()) {
            Review review = mapRowToReview(results);
            reviews.add(review);
        }
        return reviews;
	}

	@Override
	public List<Review> findReviewByBrewery(Long breweryId) {
		List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews " + 
        		"JOIN beer ON reviews.beer_id = beer.beer_id " + 
        		"WHERE beer.brewery_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, breweryId);
        while(results.next()) {
            Review review = mapRowToReview(results);
            reviews.add(review);
        }
        return reviews;
	}
	
	   private Review mapRowToReview(SqlRowSet rs) {
	        Review review = new Review();
	        review.setId(rs.getLong("review_id"));
	        review.setBeerId(rs.getLong("beer_id"));
	        review.setTitle(rs.getString("title"));
	        review.setReviewStars(rs.getInt("stars"));
	        review.setReviewBody(rs.getString("body"));
	        
	        return review;
	    }
	   
		private Long getNextId() {
			SqlRowSet nextId = jdbcTemplate.queryForRowSet("SELECT nextval ('seq_review_id')");
			if (nextId.next()) {
				return nextId.getLong(1);
			} else {
				throw new RuntimeException("Error unable to get next id");
			}
		}

		@Override
		public void deleteReview(Long id) {
			String deleteReview = "DELETE FROM reviews WHERE brewery_id = ?";
			jdbcTemplate.update(deleteReview, id);
			
		}
		
		@Override
		public List<Review> findAllUsersReviews(Long userId) {
			List<Review> reviews = new ArrayList<>();
	        String sql = "SELECT * FROM reviews WHERE reviewer_id = ?";

	        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
	        while(results.next()) {
	            Review review = mapRowToReview(results);
	            reviews.add(review);
	        }

	        return reviews;
		}



	

}
