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



@RestController
public class RecommendController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final LinkRepository linkRepository;
	private final MovieRepository movieRepository;
	private final MoviePosterRepository movieposterRepository;
	private final RatingRepository ratingRepository;
	private final UserRepository userRepository;
	private final ResultRepository resultRepository;

	public RecommendController(LinkRepository linkRepository, MovieRepository movieRepository, MoviePosterRepository movieposterRepository, 
		RatingRepository ratingRepository, UserRepository userRepository, ResultRepository resultRepository) {
		this.linkRepository = linkRepository;
		this.movieRepository = movieRepository;
		this.movieposterRepository = movieposterRepository;
		this.ratingRepository = ratingRepository;
		this.userRepository = userRepository;
		this.resultRepository = resultRepository;
	}

	@GetMapping("/users/recommendations")
    public List<Result> recommendations(@RequestBody RecommendArgument newRecommendArgument) {
		Recommendation recommend = new Recommendation(newRecommendArgument.getGender(), newRecommendArgument.getAge(), newRecommendArgument.getOccupation(), newRecommendArgument.getGenres(), 
			linkRepository, movieRepository, movieposterRepository, ratingRepository, userRepository, resultRepository);
		resultRepository.deleteAll();
		recommend.getMovielist();
		List<Result> resultList = resultRepository.findAll();
		return resultList;
	}

	@GetMapping("/movies/recommendations")
    public String recommendations2(@RequestBody RecommendArgument2 newRecommendArgument2) {
		if (newRecommendArgument2.getLimit() == 0)
			newRecommendArgument2.setLimit();
		Recommendation recommend2 = new Recommendation(newRecommendArgument2.getTitle(), newRecommendArgument2.getLimit(),
			linkRepository, movieRepository, movieposterRepository, ratingRepository, userRepository, resultRepository);
		resultRepository.deleteAll();
		recommend2.getMovielist();
		String s = recommend2.toString();
		return s;
	}

	// @GetMapping("/index") 
	// public String index() {
	// 	LOG.info("Getting index.html.");
	// 	return "index.html";
	// }

}