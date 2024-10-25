package app.batch.scheduler;

import static java.time.LocalDateTime.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewScheduler {

  private final JobLauncher jobLauncher;
  private final JobRegistry jobRegistry;

  @Scheduled(cron = "0 0 1 * * *") // 매일 1시 0분 0초에 실행
  public void dailyRun() throws Exception {

    final String jobName = "bestReviewJob";

    log.info("start scheduler {} : {}", jobName, now());

    Job job = jobRegistry.getJob(jobName); // job 이름

    JobParametersBuilder jobParam =
        new JobParametersBuilder()
            .addLocalDateTime("localDateTime", now())
            .addString("jobName", jobName);

    jobLauncher.run(job, jobParam.toJobParameters());
  }
}
