package lab.model;

import java.util.List;

public interface Person {
    String sayHello(Person person);

    int getId();
    String getFirstName();
    String getLastName();
    Country getCountry();
    int getAge();
    float getHeight();
    boolean isProgrammer();
    List<Contact> getContacts();
    boolean isBroke();
    Person setBroke(boolean value);

    default String getName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
}
