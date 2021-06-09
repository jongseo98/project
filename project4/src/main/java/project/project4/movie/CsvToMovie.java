package project.project4.movie;

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
import project.project4.movie.Movie;
import project.project4.movie.MovieCompletionNotificationListener;
import project.project4.movie.MovieDetail;
import project.project4.movie.MovieItemProcessor;

@Configuration
@EnableBatchProcessing
public class CsvToMovie {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MovieDetail> readerMovie() {
        return new FlatFileItemReaderBuilder<MovieDetail>().name("movieItemReader")
                .resource(new ClassPathResource("movies_corrected.csv")).delimited()
                .names(new String[] {"id", "title", "genre"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MovieDetail>() {
                    {
                        setTargetType(MovieDetail.class);
                    }
                }).build();
    }

    @Bean
    public MongoItemWriter<Movie> writerMovie(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Movie>().template(mongoTemplate).collection("movies")
                .build();
    }


    @Bean
    public MovieItemProcessor processorMovie() {
        return new MovieItemProcessor();
    }


    @Bean
    public Step stepMovie(FlatFileItemReader<MovieDetail> itemReader, MongoItemWriter<Movie> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("stepMovie").<MovieDetail, Movie>chunk(5).reader(itemReader)
                .processor(processorMovie()).writer(itemWriter).build();
    }

    @Bean
    public Job updateMovieJob(MovieCompletionNotificationListener listener, Step stepMovie)
            throws Exception {

        return this.jobBuilderFactory.get("updateMovieJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(stepMovie).build();
    }

}