package lab.model.simple;

import lab.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class SimpleUser implements User {
	private long id;
	private String firstName;
	private String lastName;

    public SimpleUser(String firstName, String lastName) {
        this(Integer.MIN_VALUE, firstName, lastName);
    }
}
