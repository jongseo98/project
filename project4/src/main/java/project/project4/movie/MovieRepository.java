package project.project4.movie;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.project4.movie.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    
}