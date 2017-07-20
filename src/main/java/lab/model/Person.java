package lab.model;

import java.util.List;

public interface Person {
    void sayHello(Person person);

    int getId();
    String getName();
    Country getCountry();
    int getAge();
    float getHeight();
    boolean isProgrammer();
    List<String> getContacts();

}
