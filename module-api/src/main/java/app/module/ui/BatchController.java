package app.module.ui;

import app.batch.BatchProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchController {

	private final BatchProcess process;

	@GetMapping("/hello")
	public ResponseEntity<?> getNameList() {
		return ResponseEntity.ok(process.run());
	}
}
