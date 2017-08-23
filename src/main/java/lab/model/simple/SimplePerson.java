package lab.model.simple;

import lab.model.Contact;
import lab.model.Country;
import lab.model.Person;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("person")
@Value
public class SimplePerson implements Person {
    private String firstName;
    private String lastName;
    private Country country;
    private int age;
    private float height;
    private boolean isProgrammer;
    private boolean broke;
    private List<Contact> contacts;
}
