package aop;

<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
import lab.model.Bar;
import lab.model.CustomerBrokenException;
import lab.model.Person;
import lab.model.simple.UsualPerson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
=======
import lab.aop.AopLog;
import lab.aop.Bar;
import lab.aop.CustomerBrokenException;
import lab.model.Person;
>>>>>>> lab5 start
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
import java.lang.reflect.Field;

import static aop.TestUtils.fromSystemOut;
=======
>>>>>>> lab5 start
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
class AopAspectJExceptionTest {

<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
    @Autowired
    private Bar bar;

    @Autowired
    private Person person;

    private Field broke;

    @BeforeEach
    @SneakyThrows
    void setUp() throws Exception {
        broke = UsualPerson.class.getDeclaredField("broke");
        broke.setAccessible(true);
        broke.set(person, true);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        broke.set(person, false);
=======
	@Autowired
	private Bar bar;
    
	@Autowired
    private Person person;

    @BeforeEach
    void setUp() throws Exception {
//        person.setBroke(true);
>>>>>>> lab5 start
    }

    @Test
    void testAfterThrowingAdvice() {
<<<<<<< 5904e788b920af2aff17608ab8d96db2693f93ce
        String fromOut = fromSystemOut(() ->
                assertThrows(CustomerBrokenException.class, () -> bar.sellSquishee(person)));

        //noinspection SpellCheckingInspection
        assertTrue("Customer is not broken ", fromOut.contains("Hmmm..."));
=======
 
    	assertThrows(CustomerBrokenException.class, () -> bar.sellSquishee(person));

        assertTrue("Customer is not broken ", AopLog.getStringValue().contains("Hmmm..."));
        System.out.println(AopLog.getStringValue());
>>>>>>> lab5 start
    }
}