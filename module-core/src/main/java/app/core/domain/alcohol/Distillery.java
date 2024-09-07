package app.core.domain.alcohol;

import app.core.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Comment("증류소")
@Table(name = "distillery")
@Entity(name = "core_distillery")
public class Distillery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("증류소 영어 이름")
	@Column(name = "eng_name", nullable = false)
	private String engName;

	@Comment("증류소 한글 이름")
	@Column(name = "kor_name", nullable = false)
	private String korName;

	@Comment("증류소 로고 이미지 경로")
	@Column(name = "logo_img_url")
	private String logoImgPath;
}
