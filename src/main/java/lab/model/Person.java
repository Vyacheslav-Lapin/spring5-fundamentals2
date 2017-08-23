package lab.model;

import java.util.List;

public interface Person {

    String getFirstName();
    String getLastName();
    Country getCountry();
    int getAge();
    float getHeight();
    boolean isProgrammer();
    List<Contact> getContacts();
    boolean isBroke();

    default String getName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    default String sayHello(Person person) {
        return String.format("Hello, %s, I`m %s%n", person.getName(), getName());
    }
}
