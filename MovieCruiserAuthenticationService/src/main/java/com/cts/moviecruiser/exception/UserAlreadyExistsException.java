package com.cts.moviecruiser.exception;

public class UserAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 483522054754824858L;

	public UserAlreadyExistsException(String message, String userId) {
		super(message);
		this.userId = userId;
		this.message = message;
	}
	
	private String userId;
	private String message;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "MovieAlreadyExistsException [message=" + message + "] [userId=" + userId + "]";
	}
	
	


	
	
	

}
