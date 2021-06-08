package project.project4.link;

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
import project.project4.link.Link;
import project.project4.link.LinkCompletionNotificationListener;
import project.project4.link.LinkDetail;
import project.project4.link.LinkItemProcessor;

@Configuration
@EnableBatchProcessing
public class CsvToLink {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<LinkDetail> readerLink() {
        return new FlatFileItemReaderBuilder<LinkDetail>().name("linkItemReader")
                .resource(new ClassPathResource("links.csv")).delimited()
                .names(new String[] {"id", "imdbId"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<LinkDetail>() {
                    {
                        setTargetType(LinkDetail.class);
                    }
                }).build();
    }

    @Bean
    public MongoItemWriter<Link> writerLink(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Link>().template(mongoTemplate).collection("links")
                .build();
    }


    @Bean
    public LinkItemProcessor processorLink() {
        return new LinkItemProcessor();
    }


    @Bean
    public Step stepLink(FlatFileItemReader<LinkDetail> itemReader, MongoItemWriter<Link> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("stepLink").<LinkDetail, Link>chunk(5).reader(itemReader)
                .processor(processorLink()).writer(itemWriter).build();
    }

    @Bean
    public Job updateLinkJob(LinkCompletionNotificationListener listener, Step stepLink)
            throws Exception {

        return this.jobBuilderFactory.get("updateLinkJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(stepLink).build();
    }

}