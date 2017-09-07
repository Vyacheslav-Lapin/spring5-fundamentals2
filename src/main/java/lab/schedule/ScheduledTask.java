package lab.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ScheduledTask {

	@Scheduled(fixedDelay=5000)
	void doSomething() {
		log.info("Appending log message into ScheduleLog ...");
		ScheduleLog.append("I'm printing job...\n");
	}
}
