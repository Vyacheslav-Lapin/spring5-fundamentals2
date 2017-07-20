package lab.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@org.springframework.context.annotation.Configuration
@ComponentScan
public class ApplicationConfiguration {

    @Bean
    public Person person() {
        return new UsualPerson()
                .setAge(35)
                .setCountry(country())
                .setName("John Smith")
                .addContact(contact1())
                .addContact(contact2())
                .setHeight(1.78F)
                .setProgrammer(true);
    }

    @Bean
    public Country country() {
        return new SimpleCountry()
                .setId(1)
                .setName("Russia")
                .setCodeName("RU");
    }

    @Bean
    public Contact contact1() {
        return new SimpleContact()
                .setPhoneNumber("+7-234-456-67-89")
                .setEmail("asd@asd.ru");
    }

    @Bean
    public Contact contact2() {
        return new SimpleContact()
                .setPhoneNumber("+7-000-000-67-89")
                .setEmail("asdasd21e12dw@sddasd21asd.ru");
    }
}
