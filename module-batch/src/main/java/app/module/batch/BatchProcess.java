package app.module.batch;

import app.module.core.ImpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchProcess {

	private final ImpleService impleService;

	public List<String> run() {
		return List.of("batch process1", "batch process2", "batch process3");
	}
}
