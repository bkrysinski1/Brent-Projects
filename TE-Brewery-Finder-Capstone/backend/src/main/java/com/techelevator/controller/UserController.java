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

import com.techelevator.dao.UserDAO;
import com.techelevator.model.Brewery;
import com.techelevator.model.User;

@RestController
@CrossOrigin
public class UserController {

	private UserDAO userDAO;
	
	public UserController(UserDAO userDAO ) {
		this.userDAO = userDAO;
	}

	@ResponseStatus(HttpStatus.OK) 
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> userList() {
		List<User> userList = userDAO.findAll();
		return userList;
    }
	
	@ResponseStatus(HttpStatus.OK) 
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public Long userReviewScore(@PathVariable Long id) {
		Long userScore = userDAO.findScore(id);
		return userScore;
    }
	
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path="users/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) { 
    	userDAO.delete(id);
    }
    
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public User updateUserRole(@PathVariable Long id,@Valid @RequestBody String strRole) {
		String role = strRole.split(":")[1];
		role = role.substring(1,role.length()-2);
		return userDAO.giveRole(role, id);
	}
}