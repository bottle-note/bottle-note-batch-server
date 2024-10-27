package app;

import app.batch.trigger.BatchTrigger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BatchTriggerController {

  private final BatchTrigger batchTrigger;

  @Value("${batch.password}")
  private String correctPassword;

  @PostMapping("/batch/check-password")
  @ResponseBody
  public Map<String, Boolean> checkPassword(@RequestBody Map<String, String> payload) {
    String password = payload.get("password");
    boolean isValid = correctPassword.equals(password);
    log.info("비밀번호 확인: {}", isValid);
    return Collections.singletonMap("valid", isValid);
  }

  // 배치 트리거 페이지로 이동
  @GetMapping("/batch")
  public String batchPage(Model model) throws Exception {
    List<String> strings = batchTrigger.callListener();
    model.addAttribute("jobNames", strings);

    strings.forEach(System.out::println);

    return "batch/batch-page";
  }

  // 특정 배치 작업 실행
  @PostMapping("/batch/run")
  public ResponseEntity<String> runBatch(@RequestParam String jobName) {
    try {
      batchTrigger.runJob(jobName);
      return ResponseEntity.ok("Batch job " + jobName + " started successfully.");
    } catch (Exception e) {
      log.error("Failed to start batch job: {}", jobName, e);
      return ResponseEntity.status(500).body("일괄 작업을 시작하지 못했습니다.: " + e.getMessage());
    }
  }
}
