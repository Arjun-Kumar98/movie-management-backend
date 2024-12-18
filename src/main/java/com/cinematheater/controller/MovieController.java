package com.cinematheater.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import com.cinematheater.model.*;
import com.cinematheater.service.*;
import com.cinematheater.dto.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import java.util.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/movie")
public class MovieController {
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
	@Autowired
	MovieService movieService;

@CrossOrigin(origins = "http://localhost:3000")
@PostMapping("/registerMovie")
public ResponseEntity<String> registerMovieDetails( @RequestParam("title") String title,
        @RequestParam(value ="year",required=true) Integer year,
        @RequestParam(value = "image" ,required=true) MultipartFile image,
        @RequestParam(value = "userId",required=true) Integer userId){
	MovieRequestDTO movie = new MovieRequestDTO();
	movie.setTitle(title);
	movie.setYear(year);
	movie.setUserId(userId);
	movie.setImage(image);
String msg =  movieService.saveMovieDetails(movie);
return ResponseEntity.ok(msg);
}

@CrossOrigin(origins = "http://localhost:3000")
@PutMapping("/updateMovie")
public ResponseEntity<String> updateMovieDetails(@RequestParam(value = "movieId",required = true) Integer movieId,
		@RequestParam(value = "year",required = true) Integer year, @RequestParam(required=false) MultipartFile image,@RequestParam(value = "title",required = true) String title){
	MovieRequestDTO movie = new MovieRequestDTO();
	movie.setTitle(title);
	movie.setYear(year);
	movie.setImage(image);

	String msg = movieService.updateMovieDetails(movie,movieId);
	return ResponseEntity.ok(msg);
}

@CrossOrigin(origins = "http://localhost:3000")
@GetMapping("/getMovieList")
public ResponseEntity<Page<MovieResponseDTO>> getMovieList(@RequestParam(value ="userId",required = true) Integer userId, @RequestParam(value ="page",required = true) Integer page, @RequestParam(value = "size",required = true) Integer size){
	Page<MovieResponseDTO> moviePage = movieService.getMovieList(userId, page, size);
	return ResponseEntity.ok(moviePage);
}
@CrossOrigin(origins = "http://localhost:3000")
@GetMapping("/getMovie")
public ResponseEntity<MovieResponseDTO> getMovie(@RequestParam(value = "movieId",required = true) Integer movieId){
	MovieResponseDTO movie = movieService.getMovieDetails(movieId);
	return ResponseEntity.ok(movie);
}

}