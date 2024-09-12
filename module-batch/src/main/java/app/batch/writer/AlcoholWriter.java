package app.batch.writer;

import app.core.domain.alcohol.Alcohol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AlcoholWriter {

	@Bean
	public ItemWriter<Alcohol> jpaPagingItemWriter() {
		return list -> {
			for (Alcohol alcohol : list) {
				log.info("write thread name: {}, alcohol id: {}", Thread.currentThread().getName(), alcohol.getId());
			}
		};
	}
}
