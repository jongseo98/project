package project.project4.movie_poster;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.project4.movie_poster.MoviePoster;

@Repository
public interface MoviePosterRepository extends MongoRepository<MoviePoster, String> {
    
}