package com.cts.moviecruiser.exception;

public class UserNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 583522054754824858L;

	public UserNotFoundException(String message, String userId) {
		super(message);
		this.userId = userId;
		this.message = message;
	}
	
	private String message;
	private String userId;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "UserNotFoundException [message=" + message + "] [userId=" + userId + "]";
	}
	
	


	
	
	

}
