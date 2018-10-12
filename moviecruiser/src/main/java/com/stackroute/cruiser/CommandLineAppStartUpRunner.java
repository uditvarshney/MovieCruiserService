/*

package com.stackroute.cruiser;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartUpRunner  implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CommandLineAppStartUpRunner.class);

    @Autowired
    private MovieService movieService;

    @Override
    public void run(String... args) throws Exception {
        Movie movie=new Movie();
        movie.setImdbId("tt0061725");
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
    }
}
*/
