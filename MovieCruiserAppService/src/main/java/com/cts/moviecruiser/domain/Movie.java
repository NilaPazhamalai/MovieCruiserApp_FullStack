package com.cts.moviecruiser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private int id;
	
	@Column(name = "movie_id")
	@JsonProperty("movie_id")
	private int movieId;
	@Column(name = "name")
	@JsonProperty("title")
	private String name;
	@Column(name = "comments")
	@JsonProperty("comments")
	private String comments;
	@Column(name = "poster_path")
	@JsonProperty("poster_path")
	private String posterPath;
	@Column(name = "release_date")
	@JsonProperty("release_date")
	private String releaseDate;
	
	@Column(name="user_id")
	@JsonProperty("user_id")
	private String userId;

	
	
	@Column(name = "overview")
	@JsonProperty("overview")
	private String overview;
	
	
	

	public Movie(int id, int movieId, String name, String comments, String posterPath, String releaseDate,
			String userId,String overview) {
		super();
		this.id = id;
		this.movieId = movieId;
		this.name = name;
		this.comments = comments;
		this.posterPath = posterPath;
		this.releaseDate = releaseDate;
		this.userId = userId;
		this.overview = overview;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", movieId=" + movieId + ", name=" + name + ", comments=" + comments
				+ ", posterPath=" + posterPath + ", releaseDate=" + releaseDate + ", userId=" + userId + "]";
	}
	
	

}
