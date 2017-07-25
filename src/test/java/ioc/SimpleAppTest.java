package ioc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lab.model.UsualPerson;
import lab.model.SimpleCountry;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleAppTest {

    protected static final String APPLICATION_CONTEXT_XML_FILE_NAME = "classpath:application-context.xml";

    private AbstractApplicationContext context;

    private UsualPerson expectedPerson;

    @BeforeAll
    void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                APPLICATION_CONTEXT_XML_FILE_NAME);
        expectedPerson = getExpectedPerson();
    }

    @Test
    void testInitPerson() {
        UsualPerson person = (UsualPerson) context.getBean("person");
        assertEquals(expectedPerson, person);
        System.out.println(person);
    }

    private UsualPerson getExpectedPerson() {
        return new UsualPerson()
                .setAge(35)
                .setHeight(1.78F)
                .setProgrammer(true)
                .setName("John Smith")
                .setCountry(new SimpleCountry()
                        .setId(1)
                        .setName("Russia")
                        .setCodeName("RU"))
                .setContacts(
                        Arrays.asList(
                                "asd@asd.ru",
                                "+7-234-456-67-89"
                        ));
    }
}
