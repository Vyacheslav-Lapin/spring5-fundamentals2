package lab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(exclude = "id")
@Data
public class UsualPerson implements Person {
    private int id;
    private String firstName;
    private String lastName;
    private Country country;

    @RandomIntInjection(min = 18, max = 65)
    private int age;
    private float height;
    private boolean isProgrammer;
    private boolean broke;
    private List<Contact> contacts;

    @Override
    public void sayHello(Person person) {
        System.out.printf("Hello, %s, I'm %s %s%n", person.getName(), firstName, lastName);
    }
}
