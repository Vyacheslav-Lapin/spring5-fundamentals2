package ioc;

import lab.model.Contact;
import lab.model.Person;
import lab.model.simple.SimpleCountry;
import lab.model.simple.SimplePerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HelloWorldTest {

//    private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "jdbc.xml";
//    private BeanFactory context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML_FILE_NAME);
//
//    @Test
//    void testInitPerson() {
//        assertEquals(getExpectedPerson(), context.getBean("person"));
//    }

    static Person getExpectedPerson() {
        List<Contact> contacts = new ArrayList<>();
        for (String s : Arrays.asList("asd@asd.ru", "+55 11 99999-5555")) {
            Contact from = Contact.from(s);
            contacts.add(from);
        }
        return new SimplePerson(
                "John",
                "Smith",
                new SimpleCountry("Russia","RU"),
                35,
                1.78F,
                true,
                false,
                contacts);
    }
}
