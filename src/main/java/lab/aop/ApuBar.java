package lab.aop;

import lab.model.Person;

public class ApuBar implements Bar {

    public Squishee sellSquishee(Person person) {
        if (person.isBroke())
            throw new CustomerBrokenException();

        AopLog.append("Here is your Squishee \n");
        return new Squishee("Usual Squishee");
    }
}