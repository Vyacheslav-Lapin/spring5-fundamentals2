package aop;

import lab.service.simple.ApuBar;
import lab.service.Bar;
import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static aop.TestUtils.fromSystemOut;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
class AopAspectJTest {

	@Autowired
    private Bar bar;
    
	@Autowired
    private Person person;

	private String fromOut;

    @BeforeEach
    void setUp() throws Exception {
        fromOut = fromSystemOut(() -> bar.sellSquishee(person));
    }

    @Test
    void testBeforeAdvice() {
        assertTrue("Before advice is not good enough...", fromOut.contains("Hello"));
        assertTrue("Before advice is not good enough...", fromOut.contains("How are you doing?"));
    }

    @Test
    void testAfterAdvice() {
        assertTrue("After advice is not good enough...", fromOut.contains("Good Bye!"));
    }

    @Test
    void testAfterReturningAdvice() {
        assertTrue("Customer is broken", fromOut.contains("Good Enough?"));
    }

    @Test
    void testAroundAdvice() {
        assertTrue("Around advice is not good enough...", fromOut.contains("Hi!"));
        assertTrue("Around advice is not good enough...", fromOut.contains("See you!"));
    }

    @Test
    void testAllAdvices() {
        // barObject instanceof ApuBar
        assertFalse(bar instanceof ApuBar);
    }
}