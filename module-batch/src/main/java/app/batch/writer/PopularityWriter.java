package app.batch.writer;

import app.core.domain.popular.PopularAlcohol;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PopularityWriter {

	private final DataSource dataSource;
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public JpaItemWriter<PopularAlcohol> popularityItemWriter() {
		return new JpaItemWriterBuilder<PopularAlcohol>()
			.entityManagerFactory(entityManagerFactory)
			.usePersist(false) // 존재하는 엔티티는 병합(merge)하도록 설정
			.build();
	}
}
