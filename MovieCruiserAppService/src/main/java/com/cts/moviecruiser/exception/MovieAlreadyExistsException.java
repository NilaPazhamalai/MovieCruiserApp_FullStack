package com.cts.moviecruiser.exception;

public class MovieAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 483522054754824858L;

	public MovieAlreadyExistsException(String message, String movieName) {
		super(message);
		this.movieName = movieName;
		this.message = message;
		
	}
	
	private String message;
	private String movieName;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "MovieAlreadyExistsException [message=" + message + "] [movie=" + movieName + "]";
	}
	
	


	
	
	

}
