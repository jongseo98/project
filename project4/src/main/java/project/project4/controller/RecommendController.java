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
import project.project4.result.Result;
import project.project4.result.ResultRepository;
import project.project4.movie_rating.MovieRating;
import project.project4.movie_rating.MovieRatingRepository;



@RestController
public class RecommendController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final LinkRepository linkRepository;
	private final MovieRepository movieRepository;
	private final MoviePosterRepository movieposterRepository;
	private final RatingRepository ratingRepository;
	private final UserRepository userRepository;
	private final ResultRepository resultRepository;
	private final MovieRatingRepository movieRatingRepository;

	public RecommendController(LinkRepository linkRepository, MovieRepository movieRepository, MoviePosterRepository movieposterRepository, 
		RatingRepository ratingRepository, UserRepository userRepository, ResultRepository resultRepository, MovieRatingRepository movieRatingRepository) {
		this.linkRepository = linkRepository;
		this.movieRepository = movieRepository;
		this.movieposterRepository = movieposterRepository;
		this.ratingRepository = ratingRepository;
		this.userRepository = userRepository;
		this.resultRepository = resultRepository;
		this.movieRatingRepository = movieRatingRepository;
	}

	@RequestMapping(value = "/users/recommendations", method = RequestMethod.POST)
    public List<Result> recommendations(@RequestParam(name = "gender") String gender, @RequestParam(name = "age") String age, 
		@RequestParam(name = "occupation") String occupation, @RequestParam(name = "genres") String genres) {
		Recommendation recommend = new Recommendation(gender, age, occupation, genres, linkRepository, movieRepository, 
			movieposterRepository, ratingRepository, userRepository, resultRepository, movieRatingRepository);
		resultRepository.deleteAll();
		recommend.getMovielist();
		List<Result> resultList = resultRepository.findAll();
		return resultList;
	}

	@RequestMapping(value = "/movies/recommendations", method = RequestMethod.POST)
    public List<Result> recommendations2(@RequestParam(name = "title") String title, @RequestParam(name = "limit") String limit) {
		// RecommendArgument2 newRecommendArgument2 = new RecommendArgument2(title, limit);
		// if (newRecommendArgument2.getLimit() == 0)
		// 	newRecommendArgument2.setLimit();
		System.out.println("title: " + title);
		System.out.println("limit: " + limit);
		
		// newRecommendArgument2.getTitle(), newRecommendArgument2.getLimit();
		Recommendation recommend2 = new Recommendation(title, Integer.parseInt(limit), linkRepository, movieRepository, 
			movieposterRepository, ratingRepository, userRepository, resultRepository, movieRatingRepository);
		resultRepository.deleteAll();
		recommend2.getMovielist();
		List<Result> resultList = resultRepository.findAll();
		return resultList;
	}

	// @GetMapping(value = "/user") 
	// public String user() {
	// 	LOG.info("Getting user.html.");
	// 	return "user.html";
	// }

	// @GetMapping(value = "/movie") 
	// public String movie() {
	// 	LOG.info("Getting movie.html.");
	// 	return "movie.html";
	// }

}