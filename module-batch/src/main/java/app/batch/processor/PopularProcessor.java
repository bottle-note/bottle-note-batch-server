package app.batch.processor;

import app.core.domain.common.PopularData;
import app.core.domain.popular.PopularAlcohol;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PopularProcessor implements ItemProcessor<PopularData, PopularAlcohol> {

  @Override
  public PopularAlcohol process(PopularData popularData) {

    LocalDate currentDate = LocalDate.now();

    final int year = currentDate.getYear();
    final int month = currentDate.getMonthValue();
    final int day = currentDate.getDayOfMonth();

    return PopularAlcohol.builder()
        .alcoholId(popularData.alcoholId())
        .year(year)
        .month(month)
        .day(day)
        .reviewScore(BigDecimal.valueOf(popularData.reviewScore()))
        .ratingScore(BigDecimal.valueOf(popularData.ratingScore()))
        .pickScore(BigDecimal.valueOf(popularData.pickScore()))
        .popularScore(BigDecimal.valueOf(popularData.popularScore()))
        .build();
  }
}
