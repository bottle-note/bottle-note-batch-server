package app.batch.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

@Builder
public record BestReviewModel(
    Long id,
    Long alcoholId,
    Double popularityScore,
    Integer likeCount,
    Integer unlikeCount,
    Integer replyCount,
    Integer imageCount,
    Integer reviewCount) {

  @Slf4j
  public static class Mapper implements RowMapper<BestReviewModel> {
    @Override
    public BestReviewModel mapRow(ResultSet rs, int rowNum) throws SQLException {
      return BestReviewModel.builder()
          .id(rs.getLong("id"))
          .alcoholId(rs.getLong("alcohol_id"))
          .popularityScore(rs.getDouble("popularityScore"))
          .likeCount(rs.getInt("likeCount"))
          .unlikeCount(rs.getInt("unlikeCount"))
          .replyCount(rs.getInt("replyCount"))
          .imageCount(rs.getInt("imageCount"))
          .reviewCount(rs.getInt("reviewCount"))
          .build();
    }
  }
}
