package project.project4.user;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import project.project4.user.User;
import project.project4.user.UserCompletionNotificationListener;
import project.project4.user.UserDetail;
import project.project4.user.UserItemProcessor;

@Configuration
@EnableBatchProcessing
public class CsvToUser {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<UserDetail> readerUser() {
        return new FlatFileItemReaderBuilder<UserDetail>().name("userItemReader")
                .resource(new ClassPathResource("users.csv")).delimited()
                .names(new String[] {"userId", "gender", "age", "occupation", "zipcode"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<UserDetail>() {
                    {
                        setTargetType(UserDetail.class);
                    }
                }).build();
    }

    @Bean
    public MongoItemWriter<User> writerUser(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<User>().template(mongoTemplate).collection("users")
                .build();
    }


    @Bean
    public UserItemProcessor processorUser() {
        return new UserItemProcessor();
    }


    @Bean
    public Step stepUser(FlatFileItemReader<UserDetail> itemReader, MongoItemWriter<User> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("stepUser").<UserDetail, User>chunk(5).reader(itemReader)
                .processor(processorUser()).writer(itemWriter).build();
    }

    @Bean
    public Job updateUserJob(UserCompletionNotificationListener listener, Step stepUser)
            throws Exception {

        return this.jobBuilderFactory.get("updateUserJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(stepUser).build();
    }

}