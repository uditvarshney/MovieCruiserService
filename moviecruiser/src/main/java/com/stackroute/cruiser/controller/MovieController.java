package com.stackroute.cruiser.controller;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import com.stackroute.cruiser.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController( MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie){
        ResponseEntity responseEntity;
        try{
            movieService.saveMovie(movie);
            responseEntity= new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);

        }catch (MovieAlreadyExistsException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping (value = "/movie")
    public ResponseEntity<?> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.getAllMovies(),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> searchById(@PathVariable("id") String id){
        ResponseEntity responseEntity;
        try{
            Movie movie= movieService.searchMovieById(id);
            responseEntity= new ResponseEntity<String>(movie.toString(),HttpStatus.OK);
        }catch (MovieNotFoundException e){
            responseEntity= new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("movie/{movieName}")
    public ResponseEntity<?> searchByName(@PathVariable("movieName") String movieName){
        ResponseEntity responseEntity;
        try{
            List<Movie> movie= movieService.searchByMovieName(movieName);
            responseEntity= new ResponseEntity<List<Movie>>(movie,HttpStatus.OK);
        }catch (MovieNotFoundException e){
            responseEntity= new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        ResponseEntity responseEntity;
        try{
            boolean deleted=movieService.deleteMovieById(id);
            if (deleted) {
                responseEntity = new ResponseEntity<String>("Successfully deleted", HttpStatus.OK);
            }else {
                responseEntity= new ResponseEntity<String>("Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (MovieNotFoundException e){
            responseEntity = new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        }
        return  responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMovieById(@Valid @RequestBody Movie movie ,@PathVariable("id") String id){
        ResponseEntity responseEntity;
        try {
            movieService.updateMovieById(id,movie);
            System.out.println("Hello");
            responseEntity = new ResponseEntity<String>("Successfully Updated",HttpStatus.CREATED);
        }catch (MovieNotFoundException e){
            responseEntity =new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}
