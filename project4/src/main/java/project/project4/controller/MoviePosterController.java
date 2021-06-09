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

import project.project4.movie_poster.MoviePosterRepository;
import project.project4.movie_poster.MoviePoster;

@RestController
public class MoviePosterController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final MoviePosterRepository movieposterRepository;

	public MoviePosterController(MoviePosterRepository movieposterRepository) {
		this.movieposterRepository = movieposterRepository;
	}

	@RequestMapping(value = "/movie_posters", method = RequestMethod.GET)
	public List<MoviePoster> getAllMoviePosters() {
		LOG.info("Getting all movie_posters.");
		return movieposterRepository.findAll();
	}

	
	@RequestMapping(value = "/movie_posters/{movie_posterId}", method = RequestMethod.GET)
	public MoviePoster getMoviePoster(@PathVariable String movie_posterId) {
		LOG.info("Getting movie_poster with ID: {}.", movie_posterId);
		return movieposterRepository.findById(movie_posterId).orElse(null);
	}

	
	@RequestMapping(value = "/movie_posters/create", method = RequestMethod.POST)
	public MoviePoster addNewMoviePosters(@RequestBody MoviePoster movie_poster) {
		LOG.info("Saving movie_postere.");
		return movieposterRepository.save(movie_poster);
	}
}