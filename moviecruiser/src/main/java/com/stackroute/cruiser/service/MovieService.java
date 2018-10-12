package com.stackroute.cruiser.service;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
public interface MovieService {
    Movie saveMovie(Movie movie) throws MovieAlreadyExistsException;
    Movie searchMovieById(String id) throws MovieNotFoundException;
    boolean deleteMovieById(String id) throws MovieNotFoundException;
    Movie updateMovieById(String id,Movie movie) throws MovieNotFoundException;
    List<Movie> searchByMovieName(String movieName) throws MovieNotFoundException;
    List<Movie> getAllMovies();
}
