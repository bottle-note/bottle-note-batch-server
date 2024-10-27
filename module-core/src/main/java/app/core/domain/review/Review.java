package app.core.domain.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Setter
@Getter
@Comment("리뷰 테이블(리뷰, 평점, 이미지, 리뷰 댓글)")
@Entity(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "alcohol_id")
  private Long alcoholId;

  @Column(name = "is_best", nullable = false)
  private Boolean isBest = false;

  public Review updateBest(Boolean flag) {
    this.isBest = flag;
    return this;
  }
}
