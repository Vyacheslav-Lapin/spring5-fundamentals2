package lab.config;

import lab.model.Country;
import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan("lab.model.*")
public class AppConfig {

    @Bean
    public Person person(){
        return new UsualPerson()
                .setAge(35)
                .setHeight(1.78F)
                .setProgrammer(true)
                .setName("John Smith")
                .setCountry(russia())
                .setContacts(Arrays.asList("asd@asd.ru", "+7-234-456-67-89"));
    }

    @Bean
    public Country russia() {
        return new SimpleCountry()
                .setId(1)
                .setName("Russia")
                .setCodeName("RU");
    }
}
