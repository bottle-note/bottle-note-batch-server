package app.batch.reader;

import app.core.domain.common.PopularData;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PopularReader {

  private final DataSource dataSource;

  @Bean
  public JdbcCursorItemReader<PopularData> popularityItemReader() {
    String sqlQuery =
        """
		WITH rating_scores AS (SELECT pr.alcohol_id,
                                          COALESCE(
                                                  (IF(max_diff_rating = 0, 1,
                                                      1 - ((pr.max_rating - pr.min_rating) / max_diff_rating))) *
                                                  0.4 +
                                                  (IF(max_ratings = 0, 0, (pr.ratings / 5))) * 0.6,
                                                  0
                                          ) AS rating_score
                                   FROM (SELECT alcohol_id,
                                                AVG(CASE WHEN percentile_rank = 1 THEN rating END) AS min_rating,
                                                AVG(CASE WHEN percentile_rank = 3 THEN rating END) AS max_rating,
                                                AVG(rating)                                        AS ratings,
                                                MAX(AVG(CASE WHEN percentile_rank = 3 THEN rating END) -
                                                    AVG(CASE WHEN percentile_rank = 1 THEN rating END))
                                                    OVER ()                                        AS max_diff_rating,
                                                MAX(AVG(rating)) OVER ()                           AS max_ratings
                                         FROM (SELECT alcohol_id,
                                                      rating,
                                                      NTILE(3) OVER (PARTITION BY alcohol_id ORDER BY rating ASC) AS percentile_rank
                                               FROM rating) sub
                                         GROUP BY alcohol_id) pr),
                 review_scores AS (SELECT r.alcohol_id,
                                          LEAST(
                                                  1,
                                                  (
                                                      ((COUNT(*) / (SELECT MAX(total_reviews)
                                                                    FROM (SELECT COUNT(*) AS total_reviews
                                                                          FROM review
                                                                          WHERE active_status = 'ACTIVE'
                                                                          GROUP BY alcohol_id) AS max_reviews)) * 0.4) +
                                                      ((SUM(IFNULL(r.view_count, 0)) / (SELECT MAX(total_views)
                                                                                        FROM (SELECT SUM(IFNULL(view_count, 0)) AS total_views
                                                                                              FROM review
                                                                                              WHERE active_status = 'ACTIVE'
                                                                                              GROUP BY alcohol_id) AS max_views)) *
                                                       0.3) +
                                                      ((COUNT(l.id) / (SELECT MAX(total_likes)
                                                                       FROM (SELECT COUNT(l.id) AS total_likes
                                                                             FROM review r
                                                                                      LEFT JOIN likes l ON r.id = l.review_id
                                                                             WHERE r.active_status = 'ACTIVE'
                                                                             GROUP BY r.alcohol_id) AS max_likes)) * 0.3)
                                                      ) * EXP(- (LN(2) / 30) * AVG(DATEDIFF(NOW(), r.create_at)))
                                          ) AS review_score
                                   FROM review r
                                            LEFT JOIN likes l ON r.id = l.review_id
                                   WHERE r.active_status = 'ACTIVE'
                                   GROUP BY r.alcohol_id),
                 pick_scores AS (SELECT a.id                                   AS alcohol_id,
                                        COUNT(p.id) / MAX(COUNT(p.id)) OVER () AS pick_score
                                 FROM alcohol a
                                          LEFT JOIN picks p ON a.id = p.alcohol_id AND p.status = 'PICK'
                                 GROUP BY a.id)
            SELECT a.id                                   AS alcohol_id,
                   ROUND(COALESCE(rs.review_score, 0), 2) AS review_score,
                   ROUND(COALESCE(ps.rating_score, 0), 2) AS rating_score,
                   ROUND(COALESCE(pk.pick_score, 0), 2)   AS pick_score,
                   ROUND(
                           COALESCE(rs.review_score, 0) * 0.4 +
                           COALESCE(ps.rating_score, 0) * 0.3 +
                           COALESCE(pk.pick_score, 0) * 0.3,
                           2
                   )                                      AS popular_score
            FROM alcohol a
                     LEFT JOIN review_scores rs ON a.id = rs.alcohol_id
                     LEFT JOIN rating_scores ps ON a.id = ps.alcohol_id
                     LEFT JOIN pick_scores pk ON a.id = pk.alcohol_id
            where ROUND(
                          COALESCE(rs.review_score, 0) * 0.4 +
                          COALESCE(ps.rating_score, 0) * 0.3 +
                          COALESCE(pk.pick_score, 0) * 0.3,
                          2
                  ) > 0.01
            ORDER BY popular_score DESC;
		""";

    // SQL 쿼리 로그 출력
    log.debug("Executing SQL Query:\n{}", sqlQuery);
    return new JdbcCursorItemReaderBuilder<PopularData>()
        .name("popularityItemReader")
        .dataSource(dataSource)
        .sql(sqlQuery)
        .rowMapper(new PopularDataRowMapper())
        .build();
  }
}
