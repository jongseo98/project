package project.project4.movie_rating;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "movie_rating")
public class MovieRating {

    @Id
	private String id;
    private int ratingSum;
    private int ratingNum;
    private String[] userId;

    public MovieRating(){}

	public MovieRating(String id, int ratingSum, int ratingNum) {
		this.id = id;
        this.ratingSum = ratingSum;
        this.ratingNum = ratingNum;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(int ratingSum) {
        this.ratingSum = ratingSum;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }
}