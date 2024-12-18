package com.cinematheater.test;

import com.cinematheater.dto.MovieRequestDTO;
import com.cinematheater.dto.MovieResponseDTO;
import com.cinematheater.model.MovieEntity;
import com.cinematheater.repository.MovieRepository;
import com.cinematheater.service.MovieService;
import com.cinematheater.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    private MovieRequestDTO movieRequestDTO;
    private MovieEntity movieEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        movieRequestDTO = new MovieRequestDTO();
        movieRequestDTO.setTitle("Inception");
        movieRequestDTO.setYear(2010);
        movieRequestDTO.setUserId(1);

        movieEntity = new MovieEntity();
        movieEntity.setMovieTitle("Inception");
        movieEntity.setReleaseYear(2010);
        movieEntity.setUserId(1);
    }

    @Test
    public void testSaveMovieDetails() throws IOException {
     
        byte[] imageBytes = "image".getBytes();
        movieRequestDTO.setImage(new MockMultipartFile("image", imageBytes));
        
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(movieEntity);
        
        String response = movieService.saveMovieDetails(movieRequestDTO);
        
        assertEquals("The movie has been registered successfully", response);
        verify(movieRepository, times(1)).save(any(MovieEntity.class));
    }



    @Test
    public void testUpdateMovieDetails() throws IOException {
        Integer movieId = 1;
        
   
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieEntity));

     
        byte[] imageBytes = "updatedImage".getBytes();
        movieRequestDTO.setImage(new MockMultipartFile("image", imageBytes));

        String response = movieService.updateMovieDetails(movieRequestDTO, movieId);
        
        assertEquals("The movie details have been updated successfully", response);
        verify(movieRepository, times(1)).save(any(MovieEntity.class));
    }



    @Test
    public void testGetMovieList() {
        Integer userId = 1;
        PageRequest pageRequest = PageRequest.of(0, 5);
        

        MovieEntity movie1 = new MovieEntity();
        movie1.setMovieTitle("Movie 1");
        movie1.setReleaseYear(2020);
        
        MovieEntity movie2 = new MovieEntity();
        movie2.setMovieTitle("Movie 2");
        movie2.setReleaseYear(2021);
        
        Page<MovieEntity> moviePage = new PageImpl<>(List.of(movie1, movie2), pageRequest, 2);
        
        when(movieRepository.findByUserIdUserId(userId, pageRequest)).thenReturn(moviePage);
        
        Page<MovieResponseDTO> response = movieService.getMovieList(userId, 0, 5);
        
        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals("Movie 1", response.getContent().get(0).getTitle());
    }




}
