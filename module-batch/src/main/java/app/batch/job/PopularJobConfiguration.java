package app.batch.job;

import app.batch.step.PopularStepConfiguration;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PopularJobConfiguration {

  private final PopularStepConfiguration stepConfiguration;

  @Bean
  public Job popularityJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {
    log.info("PopularityJobConfiguration 실행");
    return new JobBuilder("popularityJob", jobRepository)
        .start(stepConfiguration.popularStep(jobRepository, transactionManager))
        .build();
  }
}
