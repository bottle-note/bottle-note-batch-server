package app.batch.step;

import app.batch.reader.AlcoholReader;
import app.core.domain.alcohol.Alcohol;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class AlcoholStepConfiguration {

  private final AlcoholReader alcoholReader;
  private final AlcoholWriter alcoholWriter;

  private final int CHUNK_SIZE = 10;

  @Bean
  public Step alcoholStep(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("pagingItemReaderStepSub", jobRepository)
        .<Alcohol, Alcohol>chunk(CHUNK_SIZE, transactionManager)
        .reader(alcoholReader.jpaPagingItemReader())
        .writer(alcoholWriter.jpaPagingItemWriter())
        .build();
  }

  public Step simpleJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("simple", jobRepository)
        .<Alcohol, Alcohol>chunk(CHUNK_SIZE, transactionManager)
        .reader(alcoholReader.simpleReader())
        .writer(alcoholWriter.simpleWriter())
        .build();
  }
}
