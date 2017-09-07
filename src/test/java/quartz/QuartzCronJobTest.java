package quartz;

import lab.schedule.ScheduleLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:quartz.xml")
@ActiveProfiles("cron")
class QuartzCronJobTest{

	@BeforeEach
    void setUp() throws Exception {
        ScheduleLog.clear();
    }

	@Test
    void testCronJob() {
        try {
            Thread.sleep(5000);
            assertTrue(ScheduleLog.getStringValue().contains("I'm printing job..."));
            System.out.println("1: " + ScheduleLog.getStringValue());
            int logLength = ScheduleLog.getStringValue().length();

            Thread.sleep(4000);
            System.out.println("2: " + ScheduleLog.getStringValue());
            assertTrue(ScheduleLog.getStringValue().length() > logLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Test
    void tearDown() throws Exception {
        ScheduleLog.clear();
    }
}