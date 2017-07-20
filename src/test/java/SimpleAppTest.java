import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleAppTest {
	
	private static final String APPLICATION_CONTEXT_XML_FILE_NAME =
			"classpath:application-context.xml";

	private AbstractApplicationContext context;

	private Person expectedPerson;

	@BeforeEach
	void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(
				APPLICATION_CONTEXT_XML_FILE_NAME);
		expectedPerson = getExpectedPerson();
	}

	@Test
	void testInitPerson() {
		UsualPerson person = (UsualPerson) context.getBean("simpleperson");
//		FYI: Another way to achieve the bean
//		person = context.getBean(UsualPerson.class);
		assertEquals(expectedPerson, person);
		System.out.println(person);
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
