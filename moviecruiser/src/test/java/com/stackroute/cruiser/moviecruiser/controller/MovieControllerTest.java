package com.stackroute.cruiser.moviecruiser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.cruiser.controller.MovieController;
import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import com.stackroute.cruiser.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

//import static org.graalvm.word.LocationIdentity.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Movie movie;

    @MockBean
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;

    private List<Movie> list =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movie = new Movie();
        movie.setImdbId("tt0061725");
        movie.setMovieTitle("The Graduate");
        movie.setPosterUrl("http://ia.media-imdb.com/images/M/MV5BMTQ0ODc4MDk4Nl5BMl5BanBnXkFtZTcwMTEzNzgzNA@@._V1_SX300.jpg");
        movie.setRating(8.1);
        movie.setYearOfRelease(1967);
        list = new ArrayList();
        list.add(movie);
    }

    @Test
    public void saveMovie() throws Exception {
        when(movieService.saveMovie(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void saveMovieFailure() throws Exception {
        when(movieService.saveMovie(any())).thenThrow(MovieAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMovie() throws Exception {
        when(movieService.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testSearchById() throws Exception {
        //movieService.saveMovie(movie);
        when(movieService.searchMovieById(movie.getImdbId())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/imdbId/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSearchMovieByIdFailure() throws Exception{
        when(movieService.searchMovieById(movie.getImdbId())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/imdbId/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void searchByName() throws Exception{
        movieService.saveMovie(movie);
        when(movieService.searchByMovieName(movie.getMovieTitle())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/movieName/{movieName}",movie.getMovieTitle())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieByID() throws Exception{
        when(movieService.deleteMovieById(movie.getImdbId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteMovieByIdFailure() throws Exception{
        when(movieService.deleteMovieById(movie.getImdbId())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateMovieById() throws Exception {
        when(movieService.updateMovieById(movie.getImdbId(),movie)).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateMovieByIdFailure() throws Exception{
        when(movieService.updateMovieById(movie.getImdbId(),movie)).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }
/*
    @Test
    public void */


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
