package com.stackroute.cruiser.repository;

import com.stackroute.cruiser.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie,String> {

    //@Q("SELECT movie FROM Movie movie where movie.movieTitle = :movieTitle")
    List<Movie> getByMovieTitle(@Param("movieTitle") String movieTitle);
}
