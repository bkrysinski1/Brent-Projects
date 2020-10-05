package com.techelevator.model;
import com.techelevator.model.Review;

public class Review {
	
	   private Long reviewId;
	   private Long beerId;
	   private String title;
	   private int reviewStars;
	   private String reviewBody;


	   public Review() { }

	   public Review(Long reviewId, Long beerId, String title, int reviewStars, String reviewBody) {
	      this.reviewId = reviewId;
	      this.beerId = beerId;
	      this.title = title;
	      this.reviewStars = reviewStars;
	      this.reviewBody = reviewBody;
	   }

	public Long getId() {
		return reviewId;
	}

	public void setId(Long id) {
		this.reviewId = id;
	}

	public Long getBeerId() {
		return beerId;
	}

	public void setBeerId(Long beerId) {
		this.beerId = beerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReviewStars() {
		return reviewStars;
	}

	public void setReviewStars(int reviewStars) {
		this.reviewStars = reviewStars;
	}

	public String getReviewBody() {
		return reviewBody;
	}

	public void setReviewBody(String reviewBody) {
		this.reviewBody = reviewBody;
	}
	   
	   

	  
	}

	
	

