package project.project4.movie_rating;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.project4.movie_rating.MovieRating;

@Repository
public interface MovieRatingRepository extends MongoRepository<MovieRating, String> {
    // List<User> findBy(String gender);
}