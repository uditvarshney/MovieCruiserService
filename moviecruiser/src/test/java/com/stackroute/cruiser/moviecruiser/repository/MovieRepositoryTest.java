package com.stackroute.cruiser.moviecruiser.repository;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    Movie movie;

    @Before
    public void setUp()
    {

        movie= new Movie();
        movie.setImdbId("tt0061725");
        movie.setMovieTitle("The Graduate");
        movie.setPosterUrl("http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg");
        movie.setRating(8.1);
        movie.setYearOfRelease(1967);
    }

//    @After
//    public void tearDown(){
//
//        movieRepository.deleteAll();
//    }

    @Test
    public void testSaveMovie(){
        movieRepository.save(movie);
        Movie fetchMovie = movieRepository.findById(movie.getImdbId()).get();
        Assert.assertEquals("tt0061725", fetchMovie.getImdbId());

    }

    @Test
    public void testSaveMovieFailure(){
        Movie testMovie = new Movie("tt0061729","Spiderman",
                "http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg",
                8.2,201);
        movieRepository.save(movie);
        Movie fetchMovie = movieRepository.findById(movie.getImdbId()).get();
        Assert.assertNotSame(testMovie.getImdbId(), fetchMovie.getImdbId());
    }

    @Test
    public void testGetAllMovie(){
        Movie u = new Movie("tt0061730","Spiderman",
                "http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg",
                8.2,201);
        Movie u1 = new Movie("tt0061731","Spiderman",
                "http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg",
                8.2,201);
        movieRepository.save(u);
        movieRepository.save(u1);
        List<Movie> expectedList=new ArrayList<>();
        expectedList.add(u);
        expectedList.add(u1);
        List<Movie> list = movieRepository.findAll();
        for (int i=0;i<list.size();i++){
            Assert.assertEquals(expectedList.get(i).toString(),list.get(i).toString());
        }
    }

    @Test
    public void testSerach(){
        movieRepository.save(movie);
        Movie movie1=movieRepository.getByMovieTitle(movie.getMovieTitle()).get(0);
        Movie movie2= movieRepository.findById(movie.getImdbId()).get();
        Assert.assertEquals(movie.toString(),movie1.toString());
        Assert.assertEquals(movie.toString(),movie2.toString());
    }

    @Test
    public void testDeleteMovie(){
        movieRepository.save(movie);
        movieRepository.delete(movie);
        Assert.assertEquals(Optional.empty(),movieRepository.findById(movie.getImdbId()));
    }

}
