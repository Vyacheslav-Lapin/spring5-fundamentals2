package aop;

import lab.aop.AopLog;
import lab.aop.Bar;
import lab.aop.CustomerBrokenException;
import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
class AopAspectJExceptionTest {

    @Autowired
    private Bar bar;

    @Autowired
    private Person person;

    @BeforeEach
    void setUp() throws Exception {
        person.setBroke(true);
    }

    @Test
    void testAfterThrowingAdvice() {

        assertThrows(CustomerBrokenException.class, () -> bar.sellSquishee(person));

        assertTrue(AopLog.getStringValue().contains("Hmmm..."), "Customer is not broken ");
        System.out.println(AopLog.getStringValue());
    }
}