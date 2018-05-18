package com.cts.moviecruiser.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.repository.MovieRepository;

@RunWith(SpringRunner.class)
public class MovieServiceTest {
	@Test
	public void simpleTest(){
		assertTrue(true);
	}
	
	

	@TestConfiguration
	static class MovieServiceTestConfiguaton {
		@Bean
		public MovieService movieService() {
			return new MovieServiceImpl();
		}
	}

	@Autowired
	private MovieService movieService;
	@MockBean
	private MovieRepository movieRepository;

	Movie movie1 = new Movie(1,123, "AAA", "nice one", "path.google.com", "2011-12-12","user123","overview");
	Movie movie2 = new Movie(2,124, "BBB", "nice one", "path.google.com", "1988-12-12","user123","overview");
	Movie movie3 = new Movie(3,123, "AAA", "nice one", "path.google.com", "2011-12-12","user124","overview");
	Movie movieAlreadyFound = new Movie(4,122, "AAA", "nice one", "path.google.com", "2011-12-12","user1255","overview");
	Movie movieNotFound = new Movie(99,999, "zzz", "nice one", "path.google.com", "2011-12-12","eee124","overview");
	@Before
	public void setUp() {
		//save
		Mockito.when(movieRepository.findByMovieIdAndUserId(movie1.getMovieId(),movie1.getUserId())).thenReturn(Optional.empty());
		Mockito.when(movieRepository.save(movie1)).thenReturn(movie1);
		
		//save - fail
		Mockito.when(movieRepository.findByMovieIdAndUserId(movieAlreadyFound.getMovieId(),movieAlreadyFound.getUserId())).thenReturn(Optional.of(movieAlreadyFound));
		
		//view
		Mockito.when(movieRepository.findById(movie3.getId())).thenReturn(Optional.of(movie3));
		//view fail
		Mockito.when(movieRepository.findById(movieNotFound.getId())).thenReturn(Optional.empty());
		
		// retrieve
		Mockito.when(movieRepository.findByUserId(movie1.getUserId())).thenReturn(Arrays.asList(movie1, movie2));
		Mockito.when(movieRepository.findByUserId(movie3.getUserId())).thenReturn(Arrays.asList(movie3));
		Mockito.when(movieRepository.findByUserId("xxx")).thenReturn(java.util.Collections.EMPTY_LIST);
		//update
		Mockito.when(movieRepository.save(movie3)).thenReturn(movie3);
	}

	@Test
	public void whenSaveMovie_thenReturnMovie_onSuccess() throws MovieAlreadyExistsException {
		boolean savedMovie = false;
		savedMovie = movieService.saveMovie(movie1);
		assertThat(savedMovie).isEqualTo(true);
	}


	@Test
	public void whenSaveMovie_thenThrowException_IfMovieAlreadyAvailable() {
		Exception ex = null;
		boolean savedMovie = false;
		try {
			savedMovie = movieService.saveMovie(movieAlreadyFound);
		} catch (MovieAlreadyExistsException e) {
			ex = e;
		}
		assertThat(savedMovie).isFalse();
		assertThat(ex.toString())
				.isEqualTo(new MovieAlreadyExistsException("movie already available", movieAlreadyFound.getName()).toString());

	}


	@Test
	public void whenViewMovie_thenReturnMovie_onSuccess() throws MovieNotFoundException {
		Movie viewMovie;
		viewMovie = movieService.retrieveMovieById(movie3.getId());
		assertThat(viewMovie).isNotNull();
		assertThat(viewMovie.getName()).isEqualTo(movie3.getName());

	}

	

	@Test
	public void whenViewMovie_thenThrowException_onFailure() {
		Movie viewMovie;
		Exception ex = null;
		try {
			viewMovie = movieService.retrieveMovieById(movieNotFound.getId());
		} catch (MovieNotFoundException e) {
			ex = e;
		}
		assertThat(ex).isNotNull();
		assertThat(ex.toString()).isEqualTo(new MovieNotFoundException("movie not found", movieNotFound.getId()).toString());

	}

	

	@Test
	public void whenDeleteMovie_thenReturnSucess_onSuccess() throws MovieNotFoundException {
		boolean delMovie = false;
		delMovie = movieService.deleteById(movie3.getId());
		assertThat(delMovie).isEqualTo(true);

	}

	

	@Test
	public void whenDeleteMovie_thenThrowException_IfMovieNotFound() {
		Exception ex = null;
		boolean delMovie = false;
		try {
			delMovie = movieService.deleteById(movieNotFound.getId());
		} catch (MovieNotFoundException e) {
			ex = e;
		}
		assertThat(ex).isNotNull();
		assertThat(ex.toString()).isEqualTo(new MovieNotFoundException("movie not found", movieNotFound.getId()).toString());

	}


	@Test
	public void whenRetrieveMovie_thenReturnMovies() throws Exception {
		List movies = movieService.retrieveMyMovies(movie1.getUserId());
		assertThat(movies.size()).isEqualTo(2);

	}
	@Test
	public void whenRetrieveMovie_thenReturnOneMovie() throws Exception {
		List movies = movieService.retrieveMyMovies(movie3.getUserId());
		assertThat(movies.size()).isEqualTo(1);

	}

	@Test
	public void whenRetrieveMovie_thenReturnEmptyList() throws Exception {
		List movies = movieService.retrieveMyMovies("xxx");
		assertThat(movies.size() == 0);
		assertThat(movies).isNotNull();
	}


	@Test
	public void whenUpdateMovie_thenReturnMovie_OnSuccess() throws MovieNotFoundException {
		String comments = "nice one and drama";
		movie3.setComments(comments);
		Movie updMovie = movieService.updateMovie(movie3);
		assertThat(comments).isEqualTo(updMovie.getComments());
	}

	

	@Test
	public void whenUpdateMovie_thenReturnMovie_OnFailure() {
		Movie updMovie;
		Exception ex = null;
		try {
			updMovie = movieService.updateMovie(movieNotFound);
		} catch (MovieNotFoundException e) {
			ex = e;
		}
		assertThat(ex).isNotNull();
		assertThat(ex.toString()).isEqualTo(new MovieNotFoundException("movie not found", movieNotFound.getId()).toString());

	}

}
