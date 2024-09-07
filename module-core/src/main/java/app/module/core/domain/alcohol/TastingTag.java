package app.module.core.domain.alcohol;


import app.module.core.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Table(name = "tasting_tag")
@Entity(name = "core_tasting_tag")
public class TastingTag extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("태그 영어 이름")
	@Column(name = "eng_name", nullable = false)
	private String engName;

	@Comment("태그 한글 이름")
	@Column(name = "kor_name", nullable = false)
	private String korName;

	@Comment("아이콘")
	@Column(name = "icon")
	private String icon;

	@Comment("태그 설명")
	@Column(name = "description")
	private String description;
}
