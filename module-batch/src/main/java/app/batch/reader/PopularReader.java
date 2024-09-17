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
			-- 인기 점수 계산을 위한 SQL 쿼리
			SELECT rv.alcohol_id,
			       -- 리뷰 점수 계산
			       LEAST(1, (
			           COALESCE(rv.total_reviews / NULLIF(rv.max_reviews, 0), 0) * 0.5 +
			           COALESCE(rv.total_views / NULLIF(rv.max_views, 0), 0) * 0.3 +
			           COALESCE(rv.total_likes / NULLIF(rv.max_likes, 0), 0) * 0.2 *
			           GREATEST(0, 1 - (rv.days_since_last_review / 365))
			       )) AS review_score,
			       -- 평점 점수 계산
			       LEAST(1, (
			           (rt.avg_rating / 5) * 0.7 +
			           COALESCE(rt.total_ratings / NULLIF(rt.max_ratings, 0), 0) * 0.3 *
			           GREATEST(0, 1 - (rt.days_since_last_rating / 365))
			       )) AS rating_score,
			       -- 찜하기 점수 계산
			       LEAST(1, (
			           COALESCE(pk.total_picks / NULLIF(pk.max_picks, 0), 0) *
			           GREATEST(0, 1 - (pk.days_since_last_pick / 365))
			       )) AS pick_score,
			       -- 최종 인기 점수 계산
			       (
			           LEAST(1, (
			               COALESCE(rv.total_reviews / NULLIF(rv.max_reviews, 0), 0) * 0.5 +
			               COALESCE(rv.total_views / NULLIF(rv.max_views, 0), 0) * 0.3 +
			               COALESCE(rv.total_likes / NULLIF(rv.max_likes, 0), 0) * 0.2 *
			               GREATEST(0, 1 - (rv.days_since_last_review / 365))
			           )) +
			           LEAST(1, (
			               (rt.avg_rating / 5) * 0.7 +
			               COALESCE(rt.total_ratings / NULLIF(rt.max_ratings, 0), 0) * 0.3 *
			               GREATEST(0, 1 - (rt.days_since_last_rating / 365))
			           )) +
			           LEAST(1, (
			               COALESCE(pk.total_picks / NULLIF(pk.max_picks, 0), 0) *
			               GREATEST(0, 1 - (pk.days_since_last_pick / 365))
			           ))
			       ) / 3 AS popularity_score
			FROM
			    -- 리뷰 데이터 서브쿼리
			    (SELECT
			        r.alcohol_id,
			        COUNT(*) AS total_reviews,
			        SUM(r.view_count) AS total_views,
			        COUNT(l.id) AS total_likes,
			        DATEDIFF(NOW(), MAX(r.create_at)) AS days_since_last_review,
			        (SELECT MAX(review_count) FROM (SELECT COUNT(*) AS review_count FROM review GROUP BY alcohol_id) AS t) AS max_reviews,
			        (SELECT MAX(view_sum) FROM (SELECT SUM(view_count) AS view_sum FROM review GROUP BY alcohol_id) AS t) AS max_views,
			        (SELECT MAX(like_count) FROM (SELECT COUNT(likes.id) AS like_count FROM review LEFT JOIN likes ON review.id = likes.review_id GROUP BY review.alcohol_id) AS t) AS max_likes
			    FROM review r
			    LEFT JOIN likes l ON r.id = l.review_id
			    GROUP BY r.alcohol_id) rv
			INNER JOIN
			    -- 평점 데이터 서브쿼리
			    (SELECT
			        r.alcohol_id,
			        AVG(r.rating) AS avg_rating,
			        COUNT(*) AS total_ratings,
			        DATEDIFF(NOW(), MAX(r.create_at)) AS days_since_last_rating,
			        (SELECT MAX(rating_count) FROM (SELECT COUNT(*) AS rating_count FROM rating GROUP BY alcohol_id) AS t) AS max_ratings
			    FROM rating r
			    GROUP BY r.alcohol_id) rt ON rv.alcohol_id = rt.alcohol_id
			INNER JOIN
			    -- 찜하기 데이터 서브쿼리
			    (SELECT
			        p.alcohol_id,
			        COUNT(*) AS total_picks,
			        DATEDIFF(NOW(), MAX(p.create_at)) AS days_since_last_pick,
			        (SELECT MAX(pick_count) FROM (SELECT COUNT(*) AS pick_count FROM picks GROUP BY alcohol_id) AS t) AS max_picks
			    FROM picks p
			    GROUP BY p.alcohol_id) pk ON rv.alcohol_id = pk.alcohol_id
			ORDER BY rv.alcohol_id;
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
