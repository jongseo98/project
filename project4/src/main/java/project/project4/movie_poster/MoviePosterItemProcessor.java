package project.project4.movie_poster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import project.project4.movie_poster.MoviePoster;
import project.project4.movie_poster.MoviePosterDetail;


public class MoviePosterItemProcessor implements ItemProcessor<MoviePosterDetail, MoviePoster> {
    private static final Logger log = LoggerFactory.getLogger(MoviePosterItemProcessor.class);

    @Override
    public MoviePoster process(MoviePosterDetail item) throws Exception {

        log.info("processing MoviePoster data.....{}", item);

        MoviePoster transformedMoviePoster = new MoviePoster();
        transformedMoviePoster.setId(item.getId());
        transformedMoviePoster.setUrl(item.getUrl());
        return transformedMoviePoster;
    }
}