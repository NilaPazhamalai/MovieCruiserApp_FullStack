package com.cts.moviecruiser.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.hamcrest.Matchers;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.moviecruiser.domain.Movie;
import com.cts.moviecruiser.exception.MovieAlreadyExistsException;
import com.cts.moviecruiser.exception.MovieNotFoundException;
import com.cts.moviecruiser.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
public class MovieControllerTest {

	@Test
	public void simpleTest() {
		assertTrue(true);
		;
	}

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MovieService movieService;

	Movie movie1 = new Movie(1, 123, "AAA", "nice one", "path.google.com", "2011-12-12", "aa111", "overview");
	Movie movie2 = new Movie(2, 124, "BBB", "nice one", "path.google.com", "1988-12-12", "aa111","overview");
	Movie movie3 = new Movie(3, 123, "AAA", "nice one", "path.google.com", "2011-12-12", "bb111","overview");
	Movie movieAlreadyFound = new Movie(4, 122, "AAA", "nice one", "path.google.com", "2011-12-12", "user1255","overview");
	Movie movieNotFound = new Movie(99, 999, "zzz", "nice one", "path.google.com", "2011-12-12", "eee124","overview");

	List<Movie> movieList = Arrays.asList(movie1, movie2);
	List<Movie> movieList124 = Arrays.asList(movie3);
	final static String URI = "/api/v1/movies";

	@Test
	public void whenRetrieveMyMovies_ReturnAllMyMovieList_AsJSONArray() throws Exception {
		when(movieService.retrieveMyMovies(movie1.getUserId())).thenReturn(movieList);

		mvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON).header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)));
		verify(movieService, times(1)).retrieveMyMovies(movie1.getUserId());
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenRetrieveMyMovies_ReturnAllMyMovieListForbb111_AsJSONArray() throws Exception {
		when(movieService.retrieveMyMovies(movie3.getUserId())).thenReturn(movieList124);

		mvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON).header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYjExMSIsImlhdCI6MTUyNTcwMjc1M30.8UegI2Qvqy1kV5WLneGZ4M8JAF2aJ461h40Ua7NrSJE"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)));
		verify(movieService, times(1)).retrieveMyMovies(movie3.getUserId());
		verifyNoMoreInteractions(movieService);
	}

	
	 /** @Test public void
	 * whenRetrieveMyMovies_ReturnAllMyMovieListForUnknownUserId_AsJSONArray()
	 * throws Exception{
	 * when(movieService.retrieveMyMovies("xxx")).thenReturn(movieList124);
	 * 
	 * mvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON)
	 * .header("Authorization",
	 * "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYjExMSIsImlhdCI6MTUyNTcwMjc1M30.8UegI2Qvqy1kV5WLneGZ4M8JAF2aJ461h40Ua7NrSJE"
	 * )) .andExpect(status().isOk())
	 * .andExpect(jsonPath("$",Matchers.hasSize(0))); verify(movieService,
	 * times(1)).retrieveMyMovies("xxx");
	 * verifyNoMoreInteractions(movieService); }*/
	 

	@Test
	public void whenRetrieveMovies_ReturnException() throws Exception {

		try {
			mvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON));
		} catch (ServletException ex) {
			assertTrue(ex != null);
		}

	}

	// save
	@Test
	public void whenSaveNewMovie_ReturnMovie_AsJSON() throws Exception {

		when(movieService.saveMovie(movie1)).thenReturn(true);
		mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ")
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(movie1))).andExpect(status().isCreated());
		// .andExpect(jsonPath("$",Matchers.hasEntry("id", "4")));
		verify(movieService, times(1)).saveMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenSaveNewMovie_ReturnError() throws Exception {

		when(movieService.saveMovie(Mockito.any(Movie.class)))
				.thenThrow(new MovieAlreadyExistsException("movie already available", ""));
		mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ")
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(movieAlreadyFound)))
				.andExpect(status().isConflict())
				// .andDo(print())
				.andExpect(jsonPath("$.message").value("movie already available"));

		verify(movieService, times(1)).saveMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenGetById_ReturnCorrespondingMovie_IfAvailable() throws Exception {
		when(movieService.retrieveMovieById(movie3.getId())).thenReturn(movie3);
		mvc.perform(get(URI + "/{id}", movie3.getId()).contentType(MediaType.APPLICATION_JSON).header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYjExMSIsImlhdCI6MTUyNTcwMjc1M30.8UegI2Qvqy1kV5WLneGZ4M8JAF2aJ461h40Ua7NrSJE"))
				.andExpect(status().isOk());

		verify(movieService, times(1)).retrieveMovieById(movie3.getId());
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenGetById_ThrowException_IfNotAvailable() throws Exception {
		when(movieService.retrieveMovieById(movieNotFound.getId()))
				.thenThrow(new MovieNotFoundException("movie not found", movieNotFound.getId()));
		mvc.perform(get(URI + "/{id}", movieNotFound.getId()).contentType(MediaType.APPLICATION_JSON).header(
				"Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYjExMSIsImlhdCI6MTUyNTcwMjc1M30.8UegI2Qvqy1kV5WLneGZ4M8JAF2aJ461h40Ua7NrSJE"))
				.andExpect(status().isNotFound());

		verify(movieService, times(1)).retrieveMovieById(movieNotFound.getId());
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenDeleteById_DeleteCorrespondingMovie_IfAvailable() throws Exception {
		when(movieService.deleteById(movie2.getId())).thenReturn(true);
		mvc.perform(
				delete(URI + "/{id}", movie2.getId()).contentType(MediaType.APPLICATION_JSON).header("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"))
				.andExpect(status().isOk());

		verify(movieService, times(1)).deleteById(movie2.getId());
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenDeleteById_ThrowException_IfNotAvailable() throws Exception {
		when(movieService.deleteById(movieNotFound.getId()))
				.thenThrow(new MovieNotFoundException("movie not found", movieNotFound.getId()));
		mvc.perform(delete(URI + "/{id}", movieNotFound.getId()).contentType(MediaType.APPLICATION_JSON).header(
				"Authorization",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"))
				.andExpect(status().isNotFound());

		verify(movieService, times(1)).deleteById(movieNotFound.getId());
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenUpdateMovie_ReturnMovie_AsJSON() throws Exception {
		String comments = "nice one and drama";
		movie2.setComments(comments);
		when(movieService.updateMovie(Mockito.any(Movie.class))).thenReturn(movie2);
		mvc.perform(put(URI + "/{id}", movie2.getId()).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYjExMSIsImlhdCI6MTUyNTcwMjc1M30.8UegI2Qvqy1kV5WLneGZ4M8JAF2aJ461h40Ua7NrSJE")
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(movie2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.comments").value(comments));

		verify(movieService, times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void whenUpdateMovie_ReturnError() throws Exception {
		when(movieService.updateMovie(Mockito.any(Movie.class)))
				.thenThrow(new MovieNotFoundException("movie not found", movieNotFound.getId()));
		mvc.perform(put(URI + "/{id}", movieNotFound.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYjExMSIsImlhdCI6MTUyNTcwMjc1M30.8UegI2Qvqy1kV5WLneGZ4M8JAF2aJ461h40Ua7NrSJE")
				
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(movieNotFound)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("movie not found"));

		verify(movieService, times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);
	}

	public static String jsonToString(Movie movie) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(movie);
		} catch (JsonProcessingException e) {
			return "[]";
		}
	}

}
