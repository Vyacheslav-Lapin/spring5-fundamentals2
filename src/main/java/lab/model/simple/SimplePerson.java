package lab.model.simple;

import lab.model.Contact;
import lab.model.Country;
import lab.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity(name = "person")
@Table
@Component("person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePerson implements Person {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;

    private String lastName;

    @Column()
    private Country country;

    private int age;

    private float height;

    private boolean isProgrammer;

    private boolean broke;

    private List<Contact> contacts;
}
