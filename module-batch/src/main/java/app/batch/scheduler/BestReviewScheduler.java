package app.batch.scheduler;

import static java.time.LocalDateTime.*;

import app.core.repository.JpaReviewRepository;
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
public class BestReviewScheduler {

  private final JobLauncher jobLauncher;
  private final JobRegistry jobRegistry;
  private final JpaReviewRepository reviewRepository;

  @Scheduled(cron = "0 0 1 * * *") // 매일 1시 0분 0초에 실행
  public void bestReviewDailyRun() throws Exception {

    final String jobName = "bestReviewSelectedJob";

    log.info("start scheduler {} : {}", jobName, now());

    int count = reviewRepository.bestOrInvalid();
    log.info("count : {}", count);

    Job job = jobRegistry.getJob(jobName); // job 이름

    JobParametersBuilder jobParam =
        new JobParametersBuilder()
            .addLocalDateTime("localDateTime", now())
            .addString("jobName", jobName);

    jobLauncher.run(job, jobParam.toJobParameters());
  }
}
