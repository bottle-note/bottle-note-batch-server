package app.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! 결과를 확인하는 시간.");
			// 후속 작업 트리거 예시: 이메일 알림 전송
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			log.error("!!! JOB FAILED! 원인 조사.");
			// 실패 시 후처리 로직 (예: 경고 알림 발송)
		}
	}
}
