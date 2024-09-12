package app.batch.processor;

import app.core.domain.alcohol.Alcohol;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PopularityProcessor implements ItemProcessor<Alcohol, Alcohol> {

	@Override
	public Alcohol process(Alcohol alcohol) throws Exception {
		return alcohol;
	}
}
