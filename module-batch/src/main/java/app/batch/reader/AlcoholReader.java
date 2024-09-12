package app.batch.reader;

import app.core.domain.alcohol.Alcohol;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AlcoholReader {

	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public JpaPagingItemReader<Alcohol> jpaPagingItemReader() {
		final int CHUNK_SIZE = 10;
		return new JpaPagingItemReaderBuilder<Alcohol>()
			.name("alcoholReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(CHUNK_SIZE)
			.queryString("select p from alcohol p order by p.id")
			.build();
	}
}
