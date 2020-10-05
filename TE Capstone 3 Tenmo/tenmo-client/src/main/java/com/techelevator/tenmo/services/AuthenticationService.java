package com.techelevator.tenmo.services;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;

public class AuthenticationService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public AuthenticationService(String url) {
        this.BASE_URL = url;
    }

    public AuthenticatedUser login(UserCredentials credentials) throws AuthenticationServiceException {
        HttpEntity<UserCredentials> entity = createRequestEntity(credentials);
        return sendLoginRequest(entity);
    }

    public void register(UserCredentials credentials) throws AuthenticationServiceException {
    	HttpEntity<UserCredentials> entity = createRequestEntity(credentials);
        sendRegistrationRequest(entity);
    }
    
	private HttpEntity<UserCredentials> createRequestEntity(UserCredentials credentials) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<UserCredentials> entity = new HttpEntity<>(credentials, headers);
    	return entity;
    }

	private AuthenticatedUser sendLoginRequest(HttpEntity<UserCredentials> entity) throws AuthenticationServiceException {
		try {	
			ResponseEntity<AuthenticatedUser> response = restTemplate.exchange(BASE_URL + "login", HttpMethod.POST, entity, AuthenticatedUser.class);
			return response.getBody(); 
		} catch(RestClientResponseException ex) {
			String message = createLoginExceptionMessage(ex);
			throw new AuthenticationServiceException(message);
        }
	}

    private ResponseEntity<Map> sendRegistrationRequest(HttpEntity<UserCredentials> entity) throws AuthenticationServiceException {
    	try {
			return restTemplate.exchange(BASE_URL + "register", HttpMethod.POST, entity, Map.class);
		} catch(RestClientResponseException ex) {
			String message = createRegisterExceptionMessage(ex);
			throw new AuthenticationServiceException(message);
        }
	}

	private String createLoginExceptionMessage(RestClientResponseException ex) {
		String message = null;
		if (ex.getRawStatusCode() == 401 && ex.getResponseBodyAsString().length() == 0) {
		    message = ex.getRawStatusCode() + " : {\"timestamp\":\"" + LocalDateTime.now() + "+00:00\",\"status\":401,\"error\":\"Invalid credentials\",\"message\":\"Login failed: Invalid username or password\",\"path\":\"/login\"}";
		}
		else {
		    message = ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString();
		}
		return message;
	}
	
	private String createRegisterExceptionMessage(RestClientResponseException ex) {
		String message = null;
		if (ex.getRawStatusCode() == 400 && ex.getResponseBodyAsString().length() == 0) {
		    message = ex.getRawStatusCode() + " : {\"timestamp\":\"" + LocalDateTime.now() + "+00:00\",\"status\":400,\"error\":\"Invalid credentials\",\"message\":\"Registration failed: Invalid username or password\",\"path\":\"/register\"}";
		}
		else {
		    message = ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString();
		}
		return message;
	}
	
	public User[] getAllUsers(AuthenticatedUser user){
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	HttpEntity entity = new HttpEntity<>(headers);
    	ResponseEntity<User[]> response = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, entity, User[].class);
    	return response.getBody();
	}
	
	public int getUserByUsername(AuthenticatedUser user, String username) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<User> entity = new HttpEntity<>(headers);
    	ResponseEntity<User[]> response = restTemplate.exchange(BASE_URL + "users?username=" + username, HttpMethod.GET, entity, User[].class);
    	User[] oneUser = response.getBody();
    	return oneUser[0].getId();
	}
	
	
	public double getUserBalance(AuthenticatedUser user) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<User> entity = new HttpEntity<>(headers);
    	ResponseEntity<User> response = restTemplate.exchange(BASE_URL + "balance", HttpMethod.GET, entity, User.class);
    	//this is a JSON... so we need to turn it into an object? or just get the balance out of it?
    	double balance = response.getBody().getBalance();
		return balance;
	}
	public double sendTransfer(AuthenticatedUser user, Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
    	Transfer response = restTemplate.postForObject(BASE_URL + "send", entity, Transfer.class);
    	return response.getAmount();
	}
	public void finalizeRequest(AuthenticatedUser user, Transfer transfer) throws AuthenticationServiceException {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
    	try {
    	restTemplate.put(BASE_URL + "request/" + transfer.getTransferId(), entity);
    	} catch (RestClientResponseException e) {
    		throw new AuthenticationServiceException("Transfer does not exist.");
    	}
    	
	}
	public Transfer sendRequest(AuthenticatedUser user, Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
    	Transfer response = restTemplate.postForObject(BASE_URL + "request", entity, Transfer.class);
    	return response;
	}
	public Transfer[] getPendingArray(AuthenticatedUser user) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Transfer[]> entity = new HttpEntity<>(headers);
    	ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "request", HttpMethod.GET, entity, Transfer[].class);
    	return response.getBody();
	}
	
	public Transfer[] getMyTransfers(AuthenticatedUser user){
		HttpHeaders headers = new HttpHeaders();
	    	headers.setBearerAuth(user.getToken());
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Transfer> entity = new HttpEntity<>(headers);
	    	ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "mytransfers", HttpMethod.GET, entity, Transfer[].class);
	    	return response.getBody();
		}
//Alexa added this	
	public Transfer requestTransferInfoById(AuthenticatedUser user, int id) throws AuthenticationServiceException {
		HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Transfer> entity = new HttpEntity<>(headers);
	try {
    	ResponseEntity<Transfer> response = restTemplate.exchange(BASE_URL + "mytransfers/" + id, HttpMethod.GET, entity, Transfer.class);
		return response.getBody();
	} catch (RestClientResponseException e) {
		throw new AuthenticationServiceException("transfer does not exist.");
	}


	
	}
}
