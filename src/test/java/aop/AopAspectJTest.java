package aop;

import lab.aop.AopLog;
import lab.aop.ApuBar;
import lab.aop.Bar;
import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:aop.xml")
class AopAspectJTest {

    @Autowired
    private Bar bar;

    @Autowired
    private Person person;

    @BeforeEach
    void setUp() throws Exception {
        bar.sellSquishee(person);
    }

    @Test
    void testBeforeAdvice() {
        assertTrue(AopLog.getStringValue().contains("Hello"), "Before advice is not good enough...");
        assertTrue(AopLog.getStringValue().contains("How are you doing?"), "Before advice is not good enough...");
        System.out.println(AopLog.getStringValue());
    }

    @Test
    void testAfterAdvice() {
        System.out.println(AopLog.getStringValue());
        assertTrue(AopLog.getStringValue().contains("Good Bye!"), "After advice is not good enough...");
    }

    @Test
    void testAfterReturningAdvice() {
        assertTrue(AopLog.getStringValue().contains("Good Enough?"), "Customer is broken");
        System.out.println(AopLog.getStringValue());
    }

    @Test
    void testAroundAdvice() {
        assertTrue(AopLog.getStringValue().contains("Hi!"), "Around advice is not good enough...");
        assertTrue(AopLog.getStringValue().contains("See you!"), "Around advice is not good enough...");
        System.out.println(AopLog.getStringValue());
    }

    @Test
    void testAllAdvices() {
        // barObject instanceof ApuBar
        assertFalse(bar instanceof ApuBar);
    }
}