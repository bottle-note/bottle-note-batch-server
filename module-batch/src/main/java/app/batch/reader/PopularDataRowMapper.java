package app.batch.reader;

import app.core.domain.common.PopularData;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

@Slf4j
public class PopularDataRowMapper implements RowMapper<PopularData> {

  @Override
  public PopularData mapRow(ResultSet rs, int rowNum) throws SQLException {
    PopularData data =
        PopularData.builder()
            .alcoholId(rs.getLong("alcohol_id"))
            .reviewScore(rs.getObject("review_score") != null ? rs.getDouble("review_score") : null)
            .ratingScore(rs.getObject("rating_score") != null ? rs.getDouble("rating_score") : null)
            .pickScore(rs.getObject("pick_score") != null ? rs.getDouble("pick_score") : null)
            .popularityScore(
                rs.getObject("popularity_score") != null ? rs.getDouble("popularity_score") : null)
            .build();

    log.debug("Mapped PopularData: {}", data);

    return data;
  }
}
