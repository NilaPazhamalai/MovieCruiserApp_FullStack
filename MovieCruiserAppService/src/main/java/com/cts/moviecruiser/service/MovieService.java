package com.cts.moviecruiser.service;

import java.util.List;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;

public interface MovieService {
	
	boolean saveMovie(Movie movie) throws MovieAlreadyExistsException;
	Movie updateMovie(Movie movie) throws MovieNotFoundException;
	Movie retrieveMovieById(int id) throws MovieNotFoundException;
	boolean deleteById(int id) throws MovieNotFoundException;
	List<Movie> retrieveMyMovies(String userId) throws Exception;

}
