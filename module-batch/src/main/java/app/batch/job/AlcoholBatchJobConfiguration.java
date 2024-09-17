package app.batch.job;

import app.batch.step.AlcoholBatchStepConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class AlcoholBatchJobConfiguration {

  private final AlcoholBatchStepConfiguration alcoholBatchStepConfiguration;

  @Bean
  public Job alcoholReadJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    log.info("AlcoholReadJobConfiguration 실행");
    long startAt = System.nanoTime();

    Job jpaPagingItemReaderJob =
        new JobBuilder("pagingItemReaderJobSub", jobRepository)
            .start(alcoholBatchStepConfiguration.alcoholStep(jobRepository, transactionManager))
            .build();

    long endAt = System.nanoTime();

    log.info("JpaPagingItemReaderJobConfiguration 실행 시간(ms) : {}", (endAt - startAt) / 1000000);
    return jpaPagingItemReaderJob;
  }

  @Bean
  public Job simpleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    log.info("SimpleJobConfiguration 실행");
    return new JobBuilder("simple", jobRepository)
        .start(alcoholBatchStepConfiguration.simpleJob(jobRepository, transactionManager))
        .build();
  }

  @Bean
  public Job popularityJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    log.info("PopularityJobConfiguration 실행");
    return new JobBuilder("popularityJob", jobRepository)
        .start(alcoholBatchStepConfiguration.popularityStep(jobRepository, transactionManager))
        .build();
  }
}
