package app.batch.processor;

import app.batch.reader.PopularModel;
import app.core.domain.popular.PopularAlcohol;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PopularProcessor implements ItemProcessor<PopularModel, PopularAlcohol> {

  @Override
  public PopularAlcohol process(PopularModel popularModel) {

    LocalDate currentDate = LocalDate.now();

    final int year = currentDate.getYear();
    final int month = currentDate.getMonthValue();
    final int day = currentDate.getDayOfMonth();

    return PopularAlcohol.builder()
        .alcoholId(popularModel.alcoholId())
        .year(year)
        .month(month)
        .day(day)
        .reviewScore(BigDecimal.valueOf(popularModel.reviewScore()))
        .ratingScore(BigDecimal.valueOf(popularModel.ratingScore()))
        .pickScore(BigDecimal.valueOf(popularModel.pickScore()))
        .popularScore(BigDecimal.valueOf(popularModel.popularScore()))
        .build();
  }
}
