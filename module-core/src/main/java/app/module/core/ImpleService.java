package app.module.core;

import app.module.core.repository.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImpleService {
	private final AlcoholRepository whiskyRepository;

}
