package lab.service;

import lab.model.Person;
import lab.model.Squishee;

@FunctionalInterface
public interface Bar {
    Squishee sellSquishee(Person person);
}