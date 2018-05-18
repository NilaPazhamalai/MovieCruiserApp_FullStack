package com.cts.moviecruiser.exception;

public class MovieNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 583522054754824858L;

	public MovieNotFoundException(String message, int movieId) {
		super(message);
		this.movieId = movieId;
		this.message = message;
	}
	
	private String message;
	private int movieId;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "MovieNotFoundException [message=" + message + "] [movieId=" + movieId + "]";
	}
	
	


	
	
	

}
