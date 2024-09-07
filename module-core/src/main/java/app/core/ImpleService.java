package app.core;

import app.core.repository.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpleService {
	private final AlcoholRepository whiskyRepository;

	public List<String> get() {
		//PageRequest
		PageRequest pageRequest = PageRequest.of(0, 10);
		return whiskyRepository.findAll(pageRequest).stream().map(
			whisky -> whisky.getKorName().concat("(").concat(String.valueOf(whisky.getId())).concat(")")
		).toList();
	}
}
