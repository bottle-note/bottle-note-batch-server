package app.batch.job;

import app.batch.step.AlcoholStepConfiguration;
import app.core.domain.alcohol.Alcohol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BestReviewJobConfiguration {

  private final AlcoholStepConfiguration alcoholStepConfiguration;

  @Bean
  public Job alcoholReadJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    log.info("AlcoholReadJobConfiguration 실행");
    long startAt = System.nanoTime();

    Job jpaPagingItemReaderJob =
        new JobBuilder("pagingItemReaderJobSub", jobRepository)
            .start(alcoholStepConfiguration.alcoholStep(jobRepository, transactionManager))
            .build();

    long endAt = System.nanoTime();

    log.info("JpaPagingItemReaderJobConfiguration 실행 시간(ms) : {}", (endAt - startAt) / 1000000);
    return jpaPagingItemReaderJob;
  }

  @Bean
  public Job simpleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    log.info("SimpleJobConfiguration 실행");
    return new JobBuilder("simple", jobRepository)
        .start(alcoholStepConfiguration.simpleJob(jobRepository, transactionManager))
        .build();
  }

  public ItemWriter<Alcohol> jpaPagingItemWriter() {
    return list -> {
      for (Alcohol alcohol : list) {
        log.info(
            "write thread name: {}, alcohol id: {}",
            Thread.currentThread().getName(),
            alcohol.getId());
      }
    };
  }

  public ItemWriter<? super Alcohol> simpleWriter() {
    return list -> {
      for (Alcohol alcohol : list) {
        log.info(
            "write thread name: {}, alcohol id: {}",
            Thread.currentThread().getName(),
            alcohol.getId());
      }
    };
  }
}
