package lab.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Component("person")
@Setter
@Getter
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsualPerson implements Person {
    private int id;
    private String firstName;
    private String lastName;
    private Country country;
    private int age;
    private float height;
    private boolean isProgrammer;
    private boolean broke;
    private List<Contact> contacts;

    @Override
    public String sayHello(Person person) {
        return String.format("Hello, %s, I`m %s%n", person.getName(), getName());
    }
}
