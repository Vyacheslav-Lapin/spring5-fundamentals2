package lab.model;

import java.util.List;

public interface Person {
    int getId();
    String getName();
    Country getCountry();
    int getAge();
    float getHeight();
    boolean isProgrammer();
    List<String> getContacts();

    void sayHello(Person person);
}
