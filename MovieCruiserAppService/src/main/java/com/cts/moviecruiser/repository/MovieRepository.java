/**
 * 
 */
package com.cts.moviecruiser.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.moviecruiser.domain.Movie;

/**
 * @author ubuntu
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	public List<Movie> findByUserId(String userId);
	public Optional<Movie> findByMovieIdAndUserId(int movieId, String userId);
}
