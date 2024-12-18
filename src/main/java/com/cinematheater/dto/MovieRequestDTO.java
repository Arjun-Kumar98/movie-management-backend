package com.cinematheater.dto;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.*;
import javax.validation.Valid;
public class MovieRequestDTO {
@NotNull(message="title cannot be null")
private String title;
@NotNull(message="year cannot be null")
private Integer year;
@NotNull(message="image cannot be null")
private MultipartFile image;
@NotNull(message="user Id cannot be null")
private Integer userId;

public String getTitle() {
	return title;
}

public Integer getYear() {
	return year;
}

public MultipartFile getImage() {
	return image;
}

public Integer getUserId() {
	return userId;
}

public void setTitle(String title) {
	this.title = title;
}

public void setYear(Integer year) {
	this.year = year;
}

public void setImage(MultipartFile Image) {
	this.image = Image;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}
}
