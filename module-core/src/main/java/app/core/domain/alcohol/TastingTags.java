package app.core.domain.alcohol;

import app.core.domain.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Comment("알코올과 테이스팅 태그 연관관계 해소 테이블 ")
@Table(name = "alcohol_tasting_tags")
@Entity(name = "core_alcohol_tasting_tags")
public class TastingTags extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alcohol_id", nullable = false)
	private Alcohol alcohol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tasting_tag_id", nullable = false)
	private TastingTag tastingTag;
}
