package project.project4.rating;

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
import project.project4.rating.Rating;
import project.project4.rating.RatingCompletionNotificationListener;
import project.project4.rating.RatingDetail;
import project.project4.rating.RatingItemProcessor;

@Configuration
@EnableBatchProcessing
public class CsvToRating {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<RatingDetail> readerRating() {
        return new FlatFileItemReaderBuilder<RatingDetail>().name("ratingItemReader")
                .resource(new ClassPathResource("ratings.csv")).delimited()
                .names(new String[] {"userId", "movieId", "rating", "timestamp"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<RatingDetail>() {
                    {
                        setTargetType(RatingDetail.class);
                    }
                }).build();
    }

    @Bean
    public MongoItemWriter<Rating> writerRating(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Rating>().template(mongoTemplate).collection("ratings")
                .build();
    }


    @Bean
    public RatingItemProcessor processorRating() {
        return new RatingItemProcessor();
    }


    @Bean
    public Step stepRating(FlatFileItemReader<RatingDetail> itemReader, MongoItemWriter<Rating> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("stepRating").<RatingDetail, Rating>chunk(5).reader(itemReader)
                .processor(processorRating()).writer(itemWriter).build();
    }

    @Bean
    public Job updateRatingJob(RatingCompletionNotificationListener listener, Step stepRating)
            throws Exception {

        return this.jobBuilderFactory.get("updateRatingJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(stepRating).build();
    }
}