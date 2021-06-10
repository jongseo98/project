package project.project4.movie;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import project.project4.movie.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie findByTitle(String title);
    List<Movie> findByGenre(String genre);
    List<Movie> findByGenreLike(String genre);
}