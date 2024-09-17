package app;

import app.batch.trigger.BatchTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchTriggerController {
	private final BatchTrigger batchTrigger;

	@GetMapping("/batch")
	public void triggerBatch() throws Exception {
		batchTrigger.runJob("popularityJob");
	}
}
