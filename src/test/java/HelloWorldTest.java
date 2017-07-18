import static org.junit.jupiter.api.Assertions.assertEquals;

import lab.model.Person;
import lab.model.SimpleCountry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lab.model.UsualPerson;

public class HelloWorldTest {

    protected static final String APPLICATION_CONTEXT_XML_FILE_NAME = "application-context.xml";

    private Person expectedPerson;

    private BeanFactory context;

    @BeforeEach
    void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                new String[]{APPLICATION_CONTEXT_XML_FILE_NAME});
        expectedPerson = getExpectedPerson();
    }

    @Test
    void testInitPerson() {
        Person person = context.getBean("person", Person.class);
        assertEquals(expectedPerson, person);
//		System.out.println(person);
    }

    private Person getExpectedPerson() {
        return new UsualPerson()
                .setAge(35)
                .setName("John Smith")
                .setCountry(new SimpleCountry()
                        .setId(1)
                        .setName("Russia")
                        .setCodeName("RU")
                );
    }
}
