package project.project4.rating;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import project.project4.rating.Rating;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByUserId(String userId);
}