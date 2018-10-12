package com.stackroute.cruiser.moviecruiser.service;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import com.stackroute.cruiser.repository.MovieRepository;
import com.stackroute.cruiser.service.MovieServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMapOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    Movie movie;

    //Create a mock for UserRepository
    @Mock//test double
    MovieRepository movieRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    MovieServiceImpl movieService;
    List<Movie> list= null;

    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setImdbId("tt0061749");
        movie.setMovieTitle("The Graduate");
        movie.setPosterUrl("http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg");
        movie.setRating(8.1);
        movie.setYearOfRelease(1967);
        list = new ArrayList<>();
        list.add(movie);
    }

    @Test
    public void saveUserTestSuccess() throws MovieAlreadyExistsException {

        when(movieRepository.save((Movie) any())).thenReturn(movie);
        Movie savedMovie = movieService.saveMovie(movie);
        Assert.assertEquals(movie,savedMovie);

        //verify here verifies that userRepository save method is only called once
        verify(movieRepository,times(1)).save(movie);

    }

    @Test(expected = MovieAlreadyExistsException.class)
    public void saveUserTestFailure() throws MovieAlreadyExistsException {
        when(movieRepository.save((Movie) any())).thenReturn(null);
        Movie savedMovie = movieService.saveMovie(movie);
        System.out.println("savedUser" + savedMovie);
        Assert.assertEquals(movie,savedMovie);
        //add verify
        verify(movieRepository,times(1)).save(movie);
       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/

    }

    @Test(expected = NoSuchElementException.class)
    public void searchById() throws MovieNotFoundException, MovieAlreadyExistsException {
        when(movieRepository.existsById(movie.getImdbId())).thenReturn(true);
        //doReturn(movie).when(movieRepository.findById(movie.getImdbId()));
        when(movieRepository.findById(movie.getImdbId()).get()).thenReturn(movie);
        Movie searchMovie= movieService.searchMovieById(movie.getImdbId());
        System.out.println("searchMovie"+ searchMovie);
        Assert.assertEquals(movie,searchMovie);
        //verify(movieRepository,times(1)).save(movie);
    }

    @Test
    public void searchByName() throws MovieNotFoundException {
        when(movieRepository.getByMovieTitle(movie.getMovieTitle())).thenReturn(list);
        List<Movie> movieList=movieService.searchByMovieName(movie.getMovieTitle());
        System.out.println(movieList.toString());
        for (int i=0;i<movieList.size();i++){
            Assert.assertEquals(list.get(i),movieList.get(i));
        }
        verify(movieRepository,times(1)).getByMovieTitle(movie.getMovieTitle());
    }


    @Test
    public void testDeleteMovieById() throws MovieNotFoundException {
        when(movieRepository.existsById(anyString())).thenReturn(true);
        doNothing().when(movieRepository).deleteById(anyString());

        //when(movieRepository.findById(movie.getImdbId())).thenReturn()
        boolean result=movieService.deleteMovieById(movie.getImdbId());
        //Movie  movie1=movieService.searchMovieById(movie.getImdbId());
        Assert.assertEquals(true,result);
        verify(movieRepository,times(1)).existsById(movie.getImdbId());
    }

    @Test(expected = MovieNotFoundException.class)
    public void testDeleteByIdFailure() throws MovieNotFoundException{
        when(movieRepository.existsById(anyString())).thenReturn(false);
        doNothing().when(movieRepository).deleteById(anyString());
        boolean result= movieService.deleteMovieById(movie.getImdbId());
        Assert.assertNotEquals(true,result);
        verify(movieRepository,times(1)).existsById(movie.getImdbId());
    }


    @Test
    public void getAllMovie(){

        movieRepository.save(movie);
        //stubbing the mock to return specific data
        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> userlist = movieService.getAllMovies();
        Assert.assertEquals(list,userlist);
        verify(movieRepository,times(1)).findAll();
    }
}
