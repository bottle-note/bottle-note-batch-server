package app.batch;

import app.core.ImpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchProcess {

	private final ImpleService impleService;

	public List<String> run() {
		return impleService.get();
	}
}
