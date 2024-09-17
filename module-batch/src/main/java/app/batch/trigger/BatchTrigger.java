package app.batch.trigger;

import java.util.List;

public interface BatchTrigger {

	List<String> callListener() throws Exception;

	void runJob(String jobName) throws Exception;
}
