package app.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class BatchScheduler {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	@Scheduled(cron = "0 0 0 * * *")//매일 0시 0분 0초에 실행
	public void dailyRun() {
		String time = LocalDateTime.now().toString();
		try {
			log.info(jobRegistry.getJobNames().toString());
			Job job = jobRegistry.getJob("simple"); // job 이름
			JobParametersBuilder jobParam = new JobParametersBuilder().addString("time", time);
			jobLauncher.run(job, jobParam.toJobParameters());

		} catch (NoSuchJobException | JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
				 JobParametersInvalidException | JobRestartException e) {
			throw new RuntimeException("Job 실행 중 오류가 발생했습니다.", e);
		}
	}
}
