package project.project4;

import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@EnableMongoAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RecommendApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(RecommendApplication.class, args);
	}              
}