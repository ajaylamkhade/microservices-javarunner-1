package io.javarunner.moviescatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javarunner.moviescatalogservice.models.CatalogItem;
import io.javarunner.moviescatalogservice.models.Movie;
import io.javarunner.moviescatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating){
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
        System.out.println("movie.getName(): "+movie.getName());
        return new CatalogItem(movie.getName(), movie.getOverView(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating){
        return new CatalogItem("Movie Name not found !", "", rating.getRating());
    }
}
