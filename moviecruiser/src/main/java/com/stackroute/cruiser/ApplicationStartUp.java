/*

package com.stackroute.cruiser;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private MovieService movieService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Movie movie=new Movie();
        movie.setImdbId("tt0061724");
        movie.setMovieTitle("The Graduate");
        movie.setPosterUrl("http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg");
        movie.setRating(8.1);
        movie.setYearOfRelease(1967);
        try {
            movieService.saveMovie(movie);
            System.out.println("seed is added");
        } catch (MovieAlreadyExistsException e) {
            e.printStackTrace();
        }
        return;
    }
}
*/
