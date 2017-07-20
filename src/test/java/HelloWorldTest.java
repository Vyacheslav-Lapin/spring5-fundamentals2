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
	}

	static Person getExpectedPerson() {
        val contacts = Arrays.asList("asd@asd.ru", "+7-234-456-67-89");
        val country = new SimpleCountry(
                1,
                "Russia",
                "RU");

        return new UsualPerson(
				1,
				"John Smith",
                country,
				35,
				1.78F,
				true,
                contacts);
	}
}
