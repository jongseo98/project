package project.project4.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.project4.user.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
}