package lab.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("person")
@Value
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsualPerson implements Person {
    @Getter
    private long id;
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
