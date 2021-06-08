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

import project.project4.link.LinkRepository;
import project.project4.link.Link;
import project.project4.movie.MovieRepository;
import project.project4.movie.Movie;
import project.project4.movie_poster.MoviePosterRepository;
import project.project4.movie_poster.MoviePoster;
import project.project4.rating.RatingRepository;
import project.project4.rating.Rating;
import project.project4.user.UserRepository;
import project.project4.user.User;


@RestController
public class RecommendController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final LinkRepository linkRepository;
	private final MovieRepository movieRepository;
	private final MoviePosterRepository movieposterRepository;
	private final RatingRepository ratingRepository;
	private final UserRepository userRepository;

	public RecommendController(LinkRepository linkRepository, MovieRepository movieRepository, MoviePosterRepository movieposterRepository, 
		RatingRepository ratingRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.movieRepository = movieRepository;
		this.movieposterRepository = movieposterRepository;
		this.ratingRepository = ratingRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/users/recommendations")
    public String recommendations(@RequestBody RecommendArgument newRecommendArgument) {
		Recommendation recommend = new Recommendation(newRecommendArgument.getGender(), newRecommendArgument.getAge(), newRecommendArgument.getOccupation(), newRecommendArgument.getGenres());
		recommend.getMovielist();
		String s = recommend.toString();
		return s;
	}

	@GetMapping("/movies/recommendations")
    public String recommendations2(@RequestBody RecommendArgument2 newRecommendArgument2) {
		if (newRecommendArgument2.getLimit() == 0)
			newRecommendArgument2.setLimit();
		Recommendation recommend2 = new Recommendation(newRecommendArgument2.getTitle(), newRecommendArgument2.getLimit());
		recommend2.getMovielist();
		String s = recommend2.toString();
		return s;
	}

	@RequestMapping(value = "/links", method = RequestMethod.GET)
	public List<Link> getAllLinks() {
		LOG.info("Getting all links.");
		return linkRepository.findAll();
	}

	
	// @RequestMapping(value = "/links/{linkId}", method = RequestMethod.GET)
	// public Link getLink(@PathVariable String linkId) {
	// 	LOG.info("Getting link with ID: {}.", linkId);
	// 	return linkRepository.findById(linkId);
	// }

	
	@RequestMapping(value = "/links/create", method = RequestMethod.POST)
	public Link addNewLinks(@RequestBody Link link) {
		LOG.info("Saving link.");
		return linkRepository.save(link);
	}

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public List<Movie> getAllMovies() {
		LOG.info("Getting all movies.");
		return movieRepository.findAll();
	}

	
	// @RequestMapping(value = "/movies/{movieId}", method = RequestMethod.GET)
	// public Movie getMovie(@PathVariable String movieId) {
	// 	LOG.info("Getting movie with ID: {}.", movieId);
	// 	return movieRepository.findOne(movieId);
	// }

	
	@RequestMapping(value = "/movies/create", method = RequestMethod.POST)
	public Movie addNewMovies(@RequestBody Movie movie) {
		LOG.info("Saving movie.");
		return movieRepository.save(movie);
	}

	@RequestMapping(value = "/movie_posters", method = RequestMethod.GET)
	public List<MoviePoster> getAllMoviePosters() {
		LOG.info("Getting all movie_posters.");
		return movieposterRepository.findAll();
	}

	
	// @RequestMapping(value = "/movie_posters/{movie_posterId}", method = RequestMethod.GET)
	// public MoviePoster getMovie_poster(@PathVariable String movie_posterId) {
	// 	LOG.info("Getting movie_poster with ID: {}.", movie_posterId);
	// 	return movie_posterRepository.findOne(movie_posterId);
	// }

	
	@RequestMapping(value = "/movie_posters/create", method = RequestMethod.POST)
	public MoviePoster addNewMoviePosters(@RequestBody MoviePoster movie_poster) {
		LOG.info("Saving movie_postere.");
		return movieposterRepository.save(movie_poster);
	}


	@RequestMapping(value = "/ratings", method = RequestMethod.GET)
	public List<Rating> getAllRatings() {
		LOG.info("Getting all ratings.");
		return ratingRepository.findAll();
	}

	
	// @RequestMapping(value = "/ratings/{ratingId}", method = RequestMethod.GET)
	// public Rating getrating(@PathVariable String ratingId) {
	// 	LOG.info("Getting rating with ID: {}.", ratingId);
	// 	return ratingRepository.findOne(ratingId);
	// }

	
	@RequestMapping(value = "/ratings/create", method = RequestMethod.POST)
	public Rating addNewRatings(@RequestBody Rating rating) {
		LOG.info("Saving rating.");
		return ratingRepository.save(rating);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		LOG.info("Getting all Users.");
		return userRepository.findAll();
	}

	
	// @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	// public User getUser(@PathVariable String userId) {
	// 	LOG.info("Getting User with ID: {}.", userId);
	// 	return userRepository.findOne(userId);
	// }

	
	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public User addNewUsers(@RequestBody User user) {
		LOG.info("Saving user.");
		return userRepository.save(user);
	}


}