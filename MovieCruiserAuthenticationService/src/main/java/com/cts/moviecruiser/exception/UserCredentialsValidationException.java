package com.cts.moviecruiser.exception;

public class UserCredentialsValidationException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 583522054754824858L;

	public UserCredentialsValidationException(String message) {
		super();
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "Validation Exception [message=" + message + "] [userId=" +message + "]";
	}
	
	


	
}
