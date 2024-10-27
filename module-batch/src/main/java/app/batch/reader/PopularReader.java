package app.batch.reader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PopularReader {

  private final DataSource dataSource;

  @Value("${batch.query.popular}")
  private String popularQuery;

  @Bean
  public JdbcCursorItemReader<PopularModel> popularityItemReader() {

    String decodedQuery =
        new String(Base64.getDecoder().decode(popularQuery), StandardCharsets.UTF_8);

    return new JdbcCursorItemReaderBuilder<PopularModel>()
        .name("popularityItemReader")
        .dataSource(dataSource)
        .sql(decodedQuery)
        .rowMapper(new PopularModel.Mapper())
        .build();
  }
}
