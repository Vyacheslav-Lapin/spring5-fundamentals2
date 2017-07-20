import lab.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleAppTest {

    private ApplicationContext context;

    private Person expectedPerson;

    @BeforeEach
    void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        expectedPerson = getExpectedPerson();
    }

    @Test
    void testInitPerson() {
        UsualPerson person = (UsualPerson) context.getBean("person");
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
                .addContact(new SimpleContact()
                        .setPhoneNumber("+7-234-456-67-89")
                        .setEmail("asd@asd.ru"))
                .addContact(new SimpleContact()
                        .setPhoneNumber("+7-000-000-67-89")
                        .setEmail("asdasd21e12dw@sddasd21asd.ru"));
    }
}
