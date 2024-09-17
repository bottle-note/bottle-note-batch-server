package app.batch.processor;

import app.core.domain.common.PopularityData;
import app.core.domain.popular.PopularAlcohol;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PopularityProcessor implements ItemProcessor<PopularityData, PopularAlcohol> {

  @Override
  public PopularAlcohol process(PopularityData popularityData) {
    LocalDate currentDate = LocalDate.now();
    int year = currentDate.getYear();
    int month = currentDate.getMonthValue();
    return PopularAlcohol.builder()
        .alcoholId(popularityData.alcoholId())
        .year(year)
        .month(month)
        .reviewScore(BigDecimal.valueOf(popularityData.reviewScore()))
        .ratingScore(BigDecimal.valueOf(popularityData.ratingScore()))
        .pickScore(BigDecimal.valueOf(popularityData.pickScore()))
        .popularScore(BigDecimal.valueOf(popularityData.popularityScore()))
        .build();
  }
}
