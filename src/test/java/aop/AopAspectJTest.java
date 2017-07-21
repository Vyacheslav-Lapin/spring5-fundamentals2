package aop;

<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
import lab.model.simple.ApuBar;
import lab.model.Bar;
=======
import lab.aop.AopLog;
import lab.aop.ApuBar;
import lab.aop.Bar;
>>>>>>> lab5 start
import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
import static aop.TestUtils.fromSystemOut;
=======
>>>>>>> lab5 start
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
class AopAspectJTest {

	@Autowired
    private Bar bar;
    
	@Autowired
    private Person person;

<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
	private String fromOut;

    @BeforeEach
    void setUp() throws Exception {
        fromOut = fromSystemOut(() -> bar.sellSquishee(person));
=======
    @BeforeEach
    void setUp() throws Exception {
        bar.sellSquishee(person);
>>>>>>> lab5 start
    }

    @Test
    void testBeforeAdvice() {
<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
        assertTrue("Before advice is not good enough...", fromOut.contains("Hello"));
        assertTrue("Before advice is not good enough...", fromOut.contains("How are you doing?"));
=======
        assertTrue("Before advice is not good enought...", AopLog.getStringValue().contains("Hello"));
        assertTrue("Before advice is not good enought...", AopLog.getStringValue().contains("How are you doing?"));
        System.out.println(AopLog.getStringValue());
>>>>>>> lab5 start
    }

    @Test
    void testAfterAdvice() {
<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
        assertTrue("After advice is not good enough...", fromOut.contains("Good Bye!"));
=======
        System.out.println(AopLog.getStringValue());
        assertTrue("After advice is not good enought...", AopLog.getStringValue().contains("Good Bye!"));
>>>>>>> lab5 start
    }

    @Test
    void testAfterReturningAdvice() {
<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
        assertTrue("Customer is broken", fromOut.contains("Good Enough?"));
=======
        assertTrue("Customer is broken", AopLog.getStringValue().contains("Good Enough?"));
        System.out.println(AopLog.getStringValue());
>>>>>>> lab5 start
    }

    @Test
    void testAroundAdvice() {
<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
        assertTrue("Around advice is not good enough...", fromOut.contains("Hi!"));
        assertTrue("Around advice is not good enough...", fromOut.contains("See you!"));
=======
        assertTrue("Around advice is not good enought...", AopLog.getStringValue().contains("Hi!"));
        assertTrue("Around advice is not good enought...", AopLog.getStringValue().contains("See you!"));
        System.out.println(AopLog.getStringValue());
>>>>>>> lab5 start
    }

    @Test
    void testAllAdvices() {
        // barObject instanceof ApuBar
        assertFalse(bar instanceof ApuBar);
    }
}