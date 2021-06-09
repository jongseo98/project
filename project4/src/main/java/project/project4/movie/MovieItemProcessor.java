package project.project4.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import project.project4.movie.Movie;
import project.project4.movie.MovieDetail;


public class MovieItemProcessor implements ItemProcessor<MovieDetail, Movie> {
    private static final Logger log = LoggerFactory.getLogger(MovieItemProcessor.class);

    @Override
    public Movie process(MovieDetail item) throws Exception {

        log.info("processing Movie data.....{}", item);

        Movie transformedMovie = new Movie();
        transformedMovie.setId(item.getId());
        transformedMovie.setTitle(item.getTitle());
        transformedMovie.setGenre(item.getGenre());
        return transformedMovie;
    }
}