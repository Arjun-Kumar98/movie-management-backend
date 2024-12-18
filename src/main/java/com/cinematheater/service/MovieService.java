package com.cinematheater.service;

import com.cinematheater.jwt.*;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cinematheater.controller.MovieController;
import com.cinematheater.controller.UserController;

import java.io.IOException;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cinematheater.repository.*;
import com.cinematheater.dto.*;
import com.cinematheater.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.cinematheater.service.PasswordService;

@Service
public class MovieService {
	private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
	
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private <T> T fetchEntityById(Optional<T> optionalEntity, String entityName) {
		return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + "not found"));
	}

	
	public String saveMovieDetails(MovieRequestDTO movieDetails) {
		logger.info("this function is called");
	MovieEntity movie = new MovieEntity();
	byte[] imageBytes = null;
	try {
		imageBytes = movieDetails.getImage().getBytes();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	movie.setMovieTitle(movieDetails.getTitle());
	movie.setReleaseYear(movieDetails.getYear());
	movie.setMoviePoster(imageBytes);
	movie.setUserId(movieDetails.getUserId());
	movieRepository.save(movie);
	return "The movie has been registered successfully";
	
	}
	
	public String updateMovieDetails(MovieRequestDTO movieDetails,Integer movieId) {
		MovieEntity movie = fetchEntityById(movieRepository.findById(movieId),"movie");
		if(movieDetails.getImage()!=null) {
		byte[] imageBytes = null;
		try {
			imageBytes = movieDetails.getImage().getBytes();
		}catch(IOException e) {
			e.printStackTrace();
		}
		movie.setMoviePoster(imageBytes);
		}
		movie.setMovieTitle(movieDetails.getTitle());
		movie.setReleaseYear(movieDetails.getYear());
	
		movieRepository.save(movie);
		return "The movie details have been updated successfully";
	}
	
	public Page<MovieResponseDTO> getMovieList(Integer userId,Integer page,Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		
		Page<MovieEntity> movieList = movieRepository.findByUserIdUserId(userId, pageable);
		return movieList.map(movie->{
			String base64Image = movie.getMoviePoster()!=null?
					Base64.getEncoder().encodeToString(movie.getMoviePoster()):null;
			   return new MovieResponseDTO(movie.getMovieTitle(),movie.getReleaseYear(),base64Image,movie.getMovieId());
		}
				);
	}
	
	public MovieResponseDTO getMovieDetails(Integer movieId) {
		MovieEntity movie = fetchEntityById(movieRepository.findById(movieId),"movie");
		String base64Image = Base64.getEncoder().encodeToString(movie.getMoviePoster());
		return new MovieResponseDTO(movie.getMovieTitle(),movie.getReleaseYear(),base64Image,movieId);
	}

}
