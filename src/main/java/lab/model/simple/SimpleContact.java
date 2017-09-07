package lab.model.simple;

import lab.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class SimpleContact implements Contact, Serializable {
    private long id;
    private Type type;
    private String value;

    public SimpleContact(Type type, String value) {
        this(Long.MIN_VALUE, type, value);
    }
}
