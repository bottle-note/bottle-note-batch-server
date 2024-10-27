package app.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Builder;
import org.springframework.jdbc.core.RowMapper;

@Builder
public record PopularModel(
    Long alcoholId, Double reviewScore, Double ratingScore, Double pickScore, Double popularScore) {

  public static class Mapper implements RowMapper<PopularModel> {

    @Override
    public PopularModel mapRow(ResultSet rs, int rowNum) throws SQLException {
      return PopularModel.builder()
          .alcoholId(rs.getLong("alcohol_id"))
          .reviewScore(rs.getObject("review_score") != null ? rs.getDouble("review_score") : null)
          .ratingScore(rs.getObject("rating_score") != null ? rs.getDouble("rating_score") : null)
          .pickScore(rs.getObject("pick_score") != null ? rs.getDouble("pick_score") : null)
          .popularScore(
              rs.getObject("popular_score") != null ? rs.getDouble("popular_score") : null)
          .build();
    }
  }
}
