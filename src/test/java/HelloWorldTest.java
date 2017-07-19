import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lab.model.Person;
import lab.model.SimpleCountry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.core.Is.is;

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
        ((UsualPerson) person).setAge(35);
        assertEquals(expectedPerson, person);
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

    @Test
    void fakeTest() {
        Person person = context.getBean("person", Person.class);

        assertThat(person.getAge(), is(both(
                        greaterThan(18))
                        .and(lessThan(65))
        ));
    }
}