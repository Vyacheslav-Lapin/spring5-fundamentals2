package lab.config;

import lab.model.Country;
import lab.model.Person;
import lab.model.SimpleCountry;
import lab.model.UsualPerson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("lab.*")
public class Config {

    @Bean
    public Country simpleCountry() {
        return new SimpleCountry()
                .setId(1)
                .setName("Russia")
                .setCodeName("RU");
    }


    @Bean
    public Person usualPerson() {
        return new UsualPerson()
                .setAge(35)
                .setName("John Smith")
                .setCountry(simpleCountry());
    }

}
