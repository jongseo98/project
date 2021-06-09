package project.project4.movie_rating;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "movie_ratings")
public class MovieRating {

    private String movieId;
	private String ratingSum;
    private String ratingNum;
    private String ratingAvg;
    private String sort;

    public MovieRating(String movieId, String ratingSum, String ratingNum, String ratingAvg) {
        this.movieId = movieId;
        this.ratingSum = ratingSum;
        this.ratingNum = ratingNum;
        this.ratingAvg = ratingAvg;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

	public String getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(String ratingSum) {
        this.ratingSum = ratingSum;
    }

    public String getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(String ratingNum) {
        this.ratingNum = ratingNum;
    }

    public String getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(String ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}