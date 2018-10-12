package com.stackroute.cruiser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;

@Document(collection = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    private String imdbId;
    @NotNull
    private String movieTitle;
    @NotNull
    private String posterUrl;
    @NotNull
    private double rating;
    @NotNull
    private int yearOfRelease;


}
