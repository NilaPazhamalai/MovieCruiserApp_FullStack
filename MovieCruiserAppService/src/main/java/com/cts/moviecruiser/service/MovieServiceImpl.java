package com.cts.moviecruiser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public boolean saveMovie(Movie movie) throws MovieAlreadyExistsException {
		Optional<Movie> updMovie = movieRepository.findByMovieIdAndUserId(movie.getMovieId(), movie.getUserId());
		if(updMovie.isPresent()){
			throw new MovieAlreadyExistsException("movie already available", movie.getName());
		}
		movieRepository.save(movie);
		return true;
	}

	@Override
	public Movie updateMovie(Movie movie) throws MovieNotFoundException {
		Optional<Movie> updMovie = movieRepository.findById(movie.getId());
		if(!updMovie.isPresent()){
			throw new MovieNotFoundException("movie not found", movie.getId());
		}
		return movieRepository.save(movie);
	}

	@Override
	public Movie retrieveMovieById(int id) throws MovieNotFoundException {
		Optional<Movie> movie = movieRepository.findById(id);
		if(!movie.isPresent()){
			throw new MovieNotFoundException("movie not found", id);
		}
		return movie.get();
	}

	@Override
	public boolean deleteById(int id) throws MovieNotFoundException {
		Optional<Movie> movie = movieRepository.findById(id);
		if(!movie.isPresent()){
			throw new MovieNotFoundException("movie not found", id);
		}
		movieRepository.deleteById(id);
		return true;
	}

	

	@Override
	public List<Movie> retrieveMyMovies(String userId) throws Exception {
		return movieRepository.findByUserId(userId);
	}

}
