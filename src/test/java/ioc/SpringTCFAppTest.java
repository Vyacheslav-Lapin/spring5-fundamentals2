package ioc;

import lab.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:application-context.xml")
class SpringTCFAppTest {

    @Autowired
    private Person person;

    private Person expectedPerson = SimpleAppTest.getExpectedPerson();

    @Test
    void testInitPerson() {
        assertEquals(expectedPerson, person);
    }
}
