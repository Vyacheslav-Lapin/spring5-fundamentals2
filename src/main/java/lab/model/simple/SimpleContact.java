package lab.model.simple;

import lab.model.Contact;
import lombok.Value;

import java.io.Serializable;

@Value
public class SimpleContact implements Contact, Serializable {
    private Type type;
    private String value;
}
