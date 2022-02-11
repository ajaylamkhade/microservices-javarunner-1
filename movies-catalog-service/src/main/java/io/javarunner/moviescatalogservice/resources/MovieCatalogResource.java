package io.javarunner.moviescatalogservice.resources;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javarunner.moviescatalogservice.models.CatalogItem;
import io.javarunner.moviescatalogservice.models.Movie;
import io.javarunner.moviescatalogservice.models.Rating;
import io.javarunner.moviescatalogservice.models.UserRating;
import io.javarunner.moviescatalogservice.services.MovieInfo;
import io.javarunner.moviescatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

   // @Autowired
   // private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating userRating = userRatingInfo.getUserRating(userId);
        System.out.println("userRating: " +userRating.getRatings().get(0));
       return userRating.getRatings().stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());
    }
}
