import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldTest {

	private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "application-context.xml";

	private Person expectedPerson;

	private BeanFactory context;

	@BeforeEach
	void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML_FILE_NAME);
		expectedPerson = getExpectedPerson();
	}

	@Test
	void testInitPerson() {
		assertEquals(expectedPerson, context.getBean("person"));
//		System.out.println(person);
	}

    static Person getExpectedPerson() {
        return new UsualPerson()
                .setAge(35)
                .setHeight(1.78F)
                .setProgrammer(true)
                .setName("John Smith")
                .setCountry(new SimpleCountry()
                        .setId(1)
                        .setName("Russia")
                        .setCodeName("RU"))
                .setContacts(Arrays.asList("asd@asd.ru", "+7-234-456-67-89"));
    }
}
