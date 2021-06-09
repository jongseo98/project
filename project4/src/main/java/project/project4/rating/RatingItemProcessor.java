package project.project4.rating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import project.project4.rating.Rating;
import project.project4.rating.RatingDetail;


public class RatingItemProcessor implements ItemProcessor<RatingDetail, Rating> {
    private static final Logger log = LoggerFactory.getLogger(RatingItemProcessor.class);

    @Override
    public Rating process(RatingDetail item) throws Exception {

        log.info("processing Rating data.....{}", item);

        Rating transformedRating = new Rating();
        transformedRating.setUserId(item.getUserId());
        transformedRating.setMovieId(item.getMovieId());
        transformedRating.setRating(item.getRating());
        transformedRating.setTimestamp(item.getTimestamp());
        return transformedRating;
    }

}