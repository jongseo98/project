package project.project3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RecommendController {

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
}