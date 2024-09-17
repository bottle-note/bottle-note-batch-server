package app.batch.step;

import app.batch.processor.PopularProcessor;
import app.batch.reader.PopularReader;
import app.batch.writer.PopularWriter;
import app.core.domain.common.PopularData;
import app.core.domain.popular.PopularAlcohol;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class PopularStepConfiguration {

  private final PopularReader popularReader;
  private final PopularWriter popularWriter;
  private final PopularProcessor popularityProcessor;

  @Bean
  public Step popularStep(
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    int CHUNK_SIZE = 100;
    return new StepBuilder("popularStep", jobRepository)
        .<PopularData, PopularAlcohol>chunk(CHUNK_SIZE, transactionManager)
        .reader(popularReader.popularityItemReader())
        .processor(popularityProcessor)
        .writer(popularWriter.popularityItemWriter())
        .build();
  }
}
