package com.techelevator.tenmo.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Transfer does not exist.")
public class TransferNotFoundException extends Exception{

	public TransferNotFoundException() {
		// TODO Auto-generated constructor stub
	}

}
