package com.cinematheater.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.cinematheater.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="movie_list",
indexes = @Index(name="idx_movie",columnList = "movie_id"))
public class MovieEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="movie_id")
private Integer movieId;

@ManyToOne
@JoinColumn(name="user_id")
private UserEntity userId;

@Column(name="movie_title",nullable=false)
@JsonProperty("movieTitle")
private String movieTitle;
	
@Column(name="release_year",nullable = false)
@JsonProperty("releaseYear")
private Integer releaseYear;

@Column(name="movie_poster",nullable = false)
@JsonProperty("moviePoster")
private byte[] moviePoster;


public Integer getMovieId() {
	return movieId;
}

public void setMovieId(Integer movieId) {
	this.movieId = movieId;
}

public String getMovieTitle() {
	return movieTitle;
}

public Integer getUserId() {
	return userId!=null?userId.getUserId():null;
}

public void setUserId(Integer userId) {
	if(userId!=null) {
		UserEntity user = new UserEntity();
		user.setUserId(userId);
		this.userId = user;
	}else {
		this.userId = null;
	}
}

public void setMovieTitle(String movieTitle) {
	this.movieTitle = movieTitle;
}

public Integer getReleaseYear() {
	return releaseYear;
}

public void setReleaseYear(Integer releaseYear) {
	this.releaseYear = releaseYear;
}

public byte[] getMoviePoster() {
	return moviePoster;
}

public void setMoviePoster(byte[] moviePoster) {
	this.moviePoster = moviePoster;
}


}
