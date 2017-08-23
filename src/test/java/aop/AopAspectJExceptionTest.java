package aop;

import lab.service.Bar;
import lab.service.CustomerBrokenException;
import lab.model.Person;
import lab.model.simple.SimplePerson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static aop.TestUtils.fromSystemOut;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
class AopAspectJExceptionTest {

    @Autowired
    private Bar bar;

    @Autowired
    private Person person;

    private Field broke;

    @BeforeEach
    @SneakyThrows
    void setUp() throws Exception {
        broke = SimplePerson.class.getDeclaredField("broke");
        broke.setAccessible(true);
        broke.set(person, true);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        broke.set(person, false);
    }

    @Test
    void testAfterThrowingAdvice() {
        String fromOut = fromSystemOut(() ->
                assertThrows(CustomerBrokenException.class, () -> bar.sellSquishee(person)));

        //noinspection SpellCheckingInspection
        assertTrue("Customer is not broken ", fromOut.contains("Hmmm..."));
    }
}