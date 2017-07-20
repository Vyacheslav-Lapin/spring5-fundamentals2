import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:application-context.xml")
class SpringTCFAppTest {
	@Qualifier("simpleperson")
	@Autowired
	private Person person;

	private Person expectedPerson;
	

	@BeforeEach
	void setUp() throws Exception {
		expectedPerson = SimpleAppTest.getExpectedPerson();
	}

	@Test
	void testInitPerson() {
		assertEquals(expectedPerson, person);
		System.out.println(person);
	}
}
