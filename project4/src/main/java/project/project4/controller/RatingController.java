package project.project4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import project.project4.rating.RatingRepository;
import project.project4.rating.Rating;

@RestController
public class RatingController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final RatingRepository ratingRepository;

	public RatingController(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	@RequestMapping(value = "/ratings", method = RequestMethod.GET)
	public List<Rating> getAllRatings() {
		LOG.info("Getting all ratings.");
		return ratingRepository.findAll();
	}

	
	@RequestMapping(value = "/ratings/{ratingId}", method = RequestMethod.GET)
	public Rating getrating(@PathVariable String ratingId) {
		LOG.info("Getting rating with ID: {}.", ratingId);
		return ratingRepository.findById(ratingId).orElse(null);
	}

	
	@RequestMapping(value = "/ratings/create", method = RequestMethod.POST)
	public Rating addNewRatings(@RequestBody Rating rating) {
		LOG.info("Saving rating.");
		return ratingRepository.save(rating);
	}

}