package com.cts.moviecruiser.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.service.MovieService;

import io.jsonwebtoken.Claims;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {

	private MovieService movieService;

	@Autowired
	public MovieController(final MovieService movieService) {
		this.movieService = movieService;
	}

	@PostMapping
	public ResponseEntity<?> saveNewMovie(@RequestBody final Movie movie,
			HttpServletRequest req, HttpServletResponse res) {
		
		Claims claims = (Claims) req.getAttribute("claims");
		String userId = claims.getSubject();
		System.out.println("User Id : " + userId);
		ResponseEntity<?> response;
		System.out.println("Movie : " + movie);
		try {
			movie.setUserId(userId);
			movieService.saveMovie(movie);
			response = new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		} catch (MovieAlreadyExistsException exception) {
			System.out.println(exception.getMessage());
			System.out.println("{ \"message\" : \""+exception.getMessage()+"\"}");
			response = new ResponseEntity<String>("{ \"message\" : \""+exception.getMessage()+"\"}",
					HttpStatus.CONFLICT);
		}
		return response;
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable("id") final Integer id, @RequestBody final Movie movie) {
		ResponseEntity<?> response;
		try {
			
			Movie updMovie = movieService.updateMovie(movie);
			response = new ResponseEntity<Movie>(updMovie, HttpStatus.OK);
		} catch (MovieNotFoundException exception) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + exception.getMessage() + "\"}",
					HttpStatus.CONFLICT);
		}
		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMovie(@PathVariable("id") final Integer id) {
		ResponseEntity<?> response;
		try {
			Movie movie = movieService.retrieveMovieById(id);
			response = new ResponseEntity<Movie>(movie, HttpStatus.OK);
		} catch (MovieNotFoundException exception) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + exception.getMessage() + "\"}",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("id") final Integer id) {
		ResponseEntity<?> response;
		try {
			if (movieService.deleteById(id)) {
				response = new ResponseEntity<String>("movie deleted successfully", HttpStatus.OK);
			} else {
				response = new ResponseEntity<String>("Server issue in deleteing movie",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (MovieNotFoundException exception) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + exception.getMessage() + "\"}",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping()
	public ResponseEntity<?> getMyMovies(
			HttpServletRequest req, HttpServletResponse res) {
		ResponseEntity<?> response;
		Claims claims = (Claims) req.getAttribute("claims");
		String userId = claims.getSubject();
		System.out.println("User Id : " + userId);
		
		try {
			List<Movie>  movieList = movieService.retrieveMyMovies(userId);
			response = new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
		} catch (Exception exception) {
			response = new ResponseEntity<String>("Server issue in fetching movie",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	
	
	

}
