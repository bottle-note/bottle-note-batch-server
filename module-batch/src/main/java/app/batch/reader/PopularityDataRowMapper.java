package app.batch.reader;

import app.core.domain.common.PopularityData;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

@Slf4j
public class PopularityDataRowMapper implements RowMapper<PopularityData> {
	@Override
	public PopularityData mapRow(ResultSet rs, int rowNum) throws SQLException {
		PopularityData data = PopularityData.builder()
			.alcoholId(rs.getLong("alcohol_id"))
			.reviewScore(rs.getObject("review_score") != null ? rs.getDouble("review_score") : null)
			.ratingScore(rs.getObject("rating_score") != null ? rs.getDouble("rating_score") : null)
			.pickScore(rs.getObject("pick_score") != null ? rs.getDouble("pick_score") : null)
			.popularityScore(rs.getObject("popularity_score") != null ? rs.getDouble("popularity_score") : null)
			.build();

		log.debug("Mapped PopularityData: {}", data);

		return data;
	}
}
