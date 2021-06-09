package project.project4.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import project.project4.user.User;
import project.project4.user.UserDetail;


public class UserItemProcessor implements ItemProcessor<UserDetail, User> {
    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

    @Override
    public User process(UserDetail item) throws Exception {

        log.info("processing User data.....{}", item);

        User transformedUser = new User();
        transformedUser.setId(item.getId());
        transformedUser.setGender(item.getGender());
        transformedUser.setAge(item.getAge());
        transformedUser.setOccupation(item.getOccupation());
        transformedUser.setZipcode(item.getZipcode());
        return transformedUser;
    }
}