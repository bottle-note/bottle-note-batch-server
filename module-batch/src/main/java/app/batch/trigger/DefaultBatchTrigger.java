package app.batch.trigger;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBatchTrigger implements BatchTrigger {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobRegistry jobRegistry;


	@Override
	public List<String> callListener() {
		return List.of();
	}

	@Override
	public void runJob(String jobName) throws Exception {

		System.out.println(jobRegistry.getJobNames()); //popularityJob

		if (!jobRegistry.getJobNames().toString().contains(jobName)) {
			throw new IllegalAccessException("존재하지 않은 JOB 입니디.");
		}

		Job job = jobRegistry.getJob(jobName); // job 이름

		JobParametersBuilder jobParam = new JobParametersBuilder()
			.addLocalDateTime("localDateTime", LocalDateTime.now())
			.addString("jobName", jobName);

		jobLauncher.run(job, jobParam.toJobParameters());

	}
}
