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

import project.project4.movie.MovieRepository;
import project.project4.movie.Movie;

@RestController
public class MovieController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final MovieRepository movieRepository;

	public MovieController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}


	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public List<Movie> getAllMovies() {
		LOG.info("Getting all movies.");
		return movieRepository.findAll();
	}

	
	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.GET)
	public Movie getMovie(@PathVariable String movieId) {
		LOG.info("Getting movie with ID: {}.", movieId);
		return movieRepository.findById(movieId).orElse(null);
	}

	
	@RequestMapping(value = "/movies/create", method = RequestMethod.POST)
	public Movie addNewMovies(@RequestBody Movie movie) {
		LOG.info("Saving movie.");
		return movieRepository.save(movie);
	}

}