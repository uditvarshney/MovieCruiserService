package com.stackroute.cruiser.service;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import com.stackroute.cruiser.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service("movieservice2")
public class MovieServiceImpl2 implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl2(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException {
        if (movieRepository.existsById(movie.getImdbId())){
            throw new MovieAlreadyExistsException("Movie Already exists");
        }
        Movie saveMovie= movieRepository.save(movie);
        if (saveMovie==null){
            throw new MovieAlreadyExistsException("Movie Alredy exists");
        }
        System.out.println("movie2 called");
        return saveMovie;
    }

    @Override
    public Movie searchMovieById(String id) throws MovieNotFoundException {
        if (!movieRepository.existsById(id)){
            throw new MovieNotFoundException("No such movie is found");
        }
        Optional<Movie> movie= movieRepository.findById(id);
        if (movie==null){
            throw new MovieNotFoundException("No such movie is found");
        }
        return movie.get();
    }

    @Override
    public boolean deleteMovieById(String id) throws MovieNotFoundException {
        if (movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            if (movieRepository.existsById(id))
                return false;
            return true;
        }else {
            throw new MovieNotFoundException("No such movie is found");
        }
    }

    @Override
    public Movie updateMovieById(String id, Movie movie1) throws MovieNotFoundException {
        Movie movie=searchMovieById(id);
        movie.setMovieTitle(movie1.getMovieTitle());
        movie.setPosterUrl(movie1.getPosterUrl());
        movie.setRating(movie1.getRating());
        movie.setYearOfRelease(movie1.getYearOfRelease());
        Movie movie2=movieRepository.save(movie);
        return movie2;
    }

    @Override
    public List<Movie> searchByMovieName(String movieName) throws MovieNotFoundException {
        List<Movie> movie=movieRepository.getByMovieTitle(movieName);
        if (movie==null){
            throw new MovieNotFoundException("No movies found");
        }
        return movie;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
