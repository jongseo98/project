package project.project4.link;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.project4.link.Link;

@Repository
public interface LinkRepository extends MongoRepository<Link, String> {
    
}