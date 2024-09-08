package app.core.facade;

import app.core.dto.response.AlcoholImageInfo;
import app.core.repository.JpaAlcoholRepository;
import app.core.repository.JpaImageMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultAlcoholFacade implements AlcoholFacade {
	private final JpaAlcoholRepository alcoholRepository;
	private final JpaImageMappingRepository imageMappingRepository;

	@Override
	public List<AlcoholImageInfo> getAlcoholImageInfos() {
		return alcoholRepository.findAll().stream()
			.map(al -> AlcoholImageInfo.of(al.getId(), al.getKorName(), al.getImageUrl()))
			.toList();
	}
}
