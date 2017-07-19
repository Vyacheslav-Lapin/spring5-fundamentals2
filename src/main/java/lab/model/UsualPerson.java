package lab.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Data
public class UsualPerson implements Person {
    private int id;
    private String name;
    private Country country;
    private int age;
    private float height;
    private boolean isProgrammer;
    private List<String> contacts;

    @Override
    public void sayHello(Person person) {
        System.out.printf("Hello, %s, I`m %s%n", person.getName(), name);
    }

}