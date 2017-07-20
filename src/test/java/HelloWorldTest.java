import lab.model.Country;
import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelloWorldTest {

	private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "src/test/resources/application-context.xml";

	private UsualPerson expectedPerson;
	private SimpleCountry expectedCountry;

	private BeanFactory context;

	@BeforeEach
	void setUp() throws Exception {
		context = new FileSystemXmlApplicationContext(APPLICATION_CONTEXT_XML_FILE_NAME);
		expectedPerson = getExpectedPerson();
		expectedCountry = getExpectedCountry();
	}

	@Test
	void testInitPerson() {
		Person person = context.getBean("person", Person.class);
		assertEquals(expectedPerson, person);
		System.out.println(person);
	}

	@Test
	void testInitCountry() {
		Country country = context.getBean("simpleCountry2", Country.class);
		assertEquals(expectedCountry, country);
	}



	private UsualPerson getExpectedPerson() {
		return new UsualPerson()
				.setAge(35)
				.setName("John Smith")
				.setCountry(new SimpleCountry()
						.setId(1)
						.setName("Russia")
						.setCodeName("RU"));
	}

	private SimpleCountry getExpectedCountry() {
		return new SimpleCountry()
				.setId(2)
				.setName("Israel");
	}
}
