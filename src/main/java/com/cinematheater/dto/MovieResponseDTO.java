package com.cinematheater.dto;

public class MovieResponseDTO {

private String title;
private Integer year;
private String image;
private Integer movieId;

public MovieResponseDTO(String title,Integer year,String image,Integer movieId) {
	this.title = title;
	this.year = year;
	this.image = image;
	this.movieId = movieId;
}

public String getImage() {
	return image;
}

public void setImage(String image) {
	this.image = image;
}
public String getTitle() {
	return title;
}
public Integer getMovieId() {
	return movieId;
}

public void setMovieId(Integer movieId) {
	
	this.movieId = movieId;
}
public void setTitle(String title) {
	this.title = title;
}

public void setYear(Integer year) {
	this.year = year;
}
public Integer getYear() {
	return year;
}


}
