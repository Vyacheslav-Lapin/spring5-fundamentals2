package quartz;

import lab.schedule.ScheduleLog;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:quartz.xml")
@ActiveProfiles("interval")
class QuartzJobTest {

    @BeforeEach
    void setUp() throws Exception {
        ScheduleLog.clear();
    }

    @Test
    @SneakyThrows
    void testRepeatableJob() {
        assertTrue(ScheduleLog.getStringValue().isEmpty());
        System.out.println("1: " + ScheduleLog.getStringValue());
        Thread.sleep(3_000);

        assertTrue(ScheduleLog.getStringValue().isEmpty());
        System.out.println("2: " + ScheduleLog.getStringValue());
        int logLength = ScheduleLog.getStringValue().length();
        Thread.sleep(3_000);

        System.out.println("3: " + ScheduleLog.getStringValue());
        assertTrue(ScheduleLog.getStringValue().contains("I'm printing job..."));

        for (int i = 0; i < 5; i++) {
            System.out.println(i + 4 + ": " + ScheduleLog.getStringValue());
            assertTrue(ScheduleLog.getStringValue().length() > logLength);
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        ScheduleLog.clear();
    }
}