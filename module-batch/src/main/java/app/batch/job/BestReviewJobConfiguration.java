package app.batch.job;

import app.batch.reader.BestReviewModel;
import app.core.domain.review.Review;
import app.core.repository.JpaReviewRepository;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BestReviewJobConfiguration {

  private static final int CHUNK_SIZE = 10;
  private final DataSource dataSource;
  private final JpaReviewRepository reviewRepository;

  @Value("${batch.query.best-review}")
  private String besetReviewQuery;

  @Bean
  public Job bestReviewSelectedJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new JobBuilder("bestReviewSelectedJob", jobRepository)
        .start(reviewStep(jobRepository, transactionManager))
        .build();
  }

  public Step reviewStep(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("bestReviewSelectedStep", jobRepository)
        .<BestReviewModel, Review>chunk(CHUNK_SIZE, transactionManager)
        .reader(reviewReader())
        .processor(reviewProcessor())
        .writer(reviewWriter())
        .build();
  }

  public JdbcCursorItemReader<BestReviewModel> reviewReader() {

    String decodedQuery =
        new String(Base64.getDecoder().decode(besetReviewQuery), StandardCharsets.UTF_8);

    return new JdbcCursorItemReaderBuilder<BestReviewModel>()
        .name("reviewReader")
        .dataSource(dataSource)
        .sql(decodedQuery)
        .rowMapper(new BestReviewModel.Mapper())
        .build();
  }

  public ItemProcessor<BestReviewModel, Review> reviewProcessor() {
    return dto -> {
      Review review = reviewRepository.findById(dto.id()).orElse(null);
      if (review != null) {
        review.updateBest(true);
      }
      return review;
    };
  }

  public ItemWriter<Review> reviewWriter() {
    return list -> {
      reviewRepository.saveAll(list);
      list.forEach(
          review ->
              log.info(
                  "Best review saved: review id: {}, best: {}",
                  review.getId(),
                  review.getIsBest()));
    };
  }
}
