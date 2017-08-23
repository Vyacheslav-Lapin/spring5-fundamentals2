package lab.service.simple;

import lab.model.Person;
import lab.model.Squishee;
import lab.service.Bar;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bar")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApuBar implements Bar {

	public Squishee sellSquishee(Person person)  {
        System.out.println("Here is your Squishee");
        return () -> "Usual Squishee";
    }
}