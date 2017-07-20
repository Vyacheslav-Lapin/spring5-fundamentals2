package lab.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsualPerson implements Person {
    private int id;
    private String name;
    private Country country;
    private int age;
    private float height;
    private boolean isProgrammer;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    public void sayHello(Person person) {
        System.out.printf("Hello, %s, I`m %s%n", person.getName(), name);
    }

    public UsualPerson addContact(Contact contact) {
        contacts.add(contact);
        return this;
    }
}