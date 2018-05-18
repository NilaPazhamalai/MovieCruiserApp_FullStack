package com.cts.moviecruiser.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cts.moviecruiser.domain.Movie;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class MovieRepositoryTest {
	@Autowired
	private transient MovieRepository movieRepository;
	public void setRepo(final MovieRepository movieRepository){
		this.movieRepository = movieRepository;
	}
	
	Movie movie1 = new Movie(1,123, "AAA", "nice one", "path.google.com", "2011-12-12","user123","overview");
	Movie movie2 = new Movie(2,124, "BBB", "nice one", "path.google.com", "1988-12-12","user123","overview");
	Movie movie3 = new Movie(3,123, "AAA", "nice one", "path.google.com", "2011-12-12","user124","overview");
	@Test
	public void testSaveNewMovie() throws Exception{
		movieRepository.save(movie1);
		final Movie movie = movieRepository.getOne(1);
		assertThat(movie.getMovieId()).isEqualTo(movie1.getMovieId());
	}
	
	@Test
	public void testFindMovieById() throws Exception{
		Movie savedMovie = movieRepository.save(movie1);
		Optional<Movie> movie = movieRepository.findById(savedMovie.getId());
		assertTrue(movie.isPresent());
		assertThat(movie.get().getMovieId()).isEqualTo(movie1.getMovieId());
	}
	

	@Test
	public void testFindMovieByUserId_user123() throws Exception{
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		List<Movie> movieList = movieRepository.findByUserId(movie1.getUserId());
		assertFalse(movieList.isEmpty());
		assertThat(movieList.size()).isEqualTo(2);
		assertThat(movieList.get(0).getMovieId()).isEqualTo(movie1.getMovieId());
		assertThat(movieList.get(1).getMovieId()).isEqualTo(movie2.getMovieId());
	}
	
	@Test
	public void testFindMovieByUserId_user124() throws Exception{
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		List<Movie> movieList = movieRepository.findByUserId(movie3.getUserId());
		assertFalse(movieList.isEmpty());
		assertThat(movieList.size()).isEqualTo(1);
		assertThat(movieList.get(0).getMovieId()).isEqualTo(movie3.getMovieId());
	}
	
	
	@Test
	public void testFindMovieByUserId_user125() throws Exception{
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		List<Movie> movieList = movieRepository.findByUserId("user125");
		assertTrue(movieList.isEmpty());
	}
	
	

	@Test
	public void testFindMovieByUserIdAndMovieId() throws Exception{
		movieRepository.save(movie1);
		Optional<Movie> movie = movieRepository.findByMovieIdAndUserId(movie1.getMovieId(), movie1.getUserId());
		assertTrue(movie.isPresent());
		assertThat(movie.get().getMovieId()).isEqualTo(movie1.getMovieId());
	}
	
	@Test
	public void testUpdateMovie() throws Exception{
		Movie savedMovie = movieRepository.save(movie3);
		final Movie movie = movieRepository.getOne(savedMovie.getId());
		assertThat(movie.getMovieId()).isEqualTo(movie3.getMovieId());
		movie1.setComments("superb");
		movieRepository.save(movie3);
		final Movie updMovie = movieRepository.getOne(movie.getId());
		assertThat(updMovie.getComments()).isEqualTo(movie3.getComments());
	}
	
	
	@Test
	public void testDeleteMovie() throws Exception{
		movieRepository.save(movie2);
		final Movie movie = movieRepository.getOne(2);
		assertThat(movie.getMovieId()).isEqualTo(movie2.getMovieId());
		movieRepository.deleteById(movie.getId());
		Optional<Movie> delMovie = movieRepository.findById(movie.getId());
		assertFalse(delMovie.isPresent());
	}
}
