package project.project4.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.project4.user.User;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByGender(String gender);
    List<User> findByAge(String age);
    List<User> findByOccupation(String occupation);
    List<User> findByGenderAndAge(String gender, String age);
    List<User> findByGenderAndOccupation(String gender, String occupation);
    List<User> findByAgeAndOccupation(String age, String occupation);
    List<User> findByGenderAndAgeAndOccupation(String gender, String age, String occupation);
}