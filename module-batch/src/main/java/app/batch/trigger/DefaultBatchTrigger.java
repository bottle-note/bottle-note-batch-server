package app.batch.trigger;

import app.batch.scheduler.BestReviewScheduler;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultBatchTrigger implements BatchTrigger {

  private final JobLauncher jobLauncher;
  private final JobRegistry jobRegistry;
  private final BestReviewScheduler bestReviewScheduler;

  @Override
  public List<String> callListener() {
    return jobRegistry.getJobNames().stream().toList();
  }

  @Override
  public void runJob(String jobName) throws Exception {
    if (!jobRegistry.getJobNames().toString().contains(jobName))
      throw new IllegalAccessException("존재하지 않은 JOB 입니디.");

    if (jobName.equals("bestReviewSelectedJob")) {
      callBestReviewScheduler();
    } else {
      callJob(jobName);
    }
  }

  void callBestReviewScheduler() throws Exception {
    bestReviewScheduler.bestReviewDailyRun();
  }

  void callJob(String jobName) throws Exception {

    Job job = jobRegistry.getJob(jobName); // job 이름
    JobParametersBuilder jobParam =
        new JobParametersBuilder()
            .addLocalDateTime("localDateTime", LocalDateTime.now())
            .addString("jobName", jobName);

    jobLauncher.run(job, jobParam.toJobParameters());
  }
}
