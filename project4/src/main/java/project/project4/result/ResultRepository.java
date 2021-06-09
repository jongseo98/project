package project.project4.result;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.project4.result.Result;

@Repository
public interface ResultRepository extends MongoRepository<Result, String> {
//     List<User> findBy(String gender);
}