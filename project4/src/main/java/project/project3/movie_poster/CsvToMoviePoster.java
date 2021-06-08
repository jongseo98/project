package project.project4.movie_poster;

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
import project.project4.movie_poster.MoviePoster;
import project.project4.movie_poster.MoviePosterCompletionNotificationListener;
import project.project4.movie_poster.MoviePosterDetail;
import project.project4.movie_poster.MoviePosterItemProcessor;

@Configuration
@EnableBatchProcessing
public class CsvToMoviePoster {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MoviePosterDetail> readerMoviePoster() {
        return new FlatFileItemReaderBuilder<MoviePosterDetail>().name("moviePosterItemReader")
                .resource(new ClassPathResource("movie_poster.csv")).delimited()
                .names(new String[] {"id", "url"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MoviePosterDetail>() {
                    {
                        setTargetType(MoviePosterDetail.class);
                    }
                }).build();
    }

    @Bean
    public MongoItemWriter<MoviePoster> writerMoviePoster(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MoviePoster>().template(mongoTemplate).collection("movie_posters")
                .build();
    }


    @Bean
    public MoviePosterItemProcessor processorMoviePoster() {
        return new MoviePosterItemProcessor();
    }


    @Bean
    public Step stepMoviePoster(FlatFileItemReader<MoviePosterDetail> itemReader, MongoItemWriter<MoviePoster> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("stepMoviePoster").<MoviePosterDetail, MoviePoster>chunk(5).reader(itemReader)
                .processor(processorMoviePoster()).writer(itemWriter).build();
    }

    @Bean
    public Job updateMoviePosterJob(MoviePosterCompletionNotificationListener listener, Step stepMoviePoster)
            throws Exception {

        return this.jobBuilderFactory.get("updateMoviePosterJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(stepMoviePoster).build();
    }

}