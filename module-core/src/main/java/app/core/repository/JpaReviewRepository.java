package app.core.repository;

import app.core.domain.review.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewRepository extends JpaRepository<Review, Long> {

  @Transactional
  @Modifying
  @Query("update review r set r.isBest = false")
  int bestOrInvalid();
}
