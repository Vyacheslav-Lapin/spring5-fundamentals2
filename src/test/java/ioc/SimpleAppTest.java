package ioc;

import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleAppTest {

    private static final String APPLICATION_CONTEXT_XML_FILE_NAME =
            "classpath:application-context.xml";

    private AbstractApplicationContext context = new ClassPathXmlApplicationContext(
            APPLICATION_CONTEXT_XML_FILE_NAME);

    private Person expectedPerson = getExpectedPerson();

    @Test
    void testInitPerson() {
        assertEquals(expectedPerson, context.getBean("person"));
    }

    static Person getExpectedPerson() {
        return new UsualPerson()
                .setAge(20)
                .setHeight(1.78F)
                .setProgrammer(false)
                .setName("John Doe")
                .setCountry(new SimpleCountry()
                        .setId(1)
                        .setName("Netherlands")
                        .setCodeName("NE"))
                .setContacts(Arrays.asList("some@email.com", "+7-111-111-11-11"));
    }
}
