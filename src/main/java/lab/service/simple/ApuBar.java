package lab.service.simple;

import lab.model.Person;
import lab.model.Squishee;
import lab.service.Bar;
import org.springframework.stereotype.Component;

@Component("bar")
public class ApuBar implements Bar {

	public Squishee sellSquishee(Person person)  {
        System.out.println("Here is your Squishee");
        return () -> "Usual Squishee";
    }
}