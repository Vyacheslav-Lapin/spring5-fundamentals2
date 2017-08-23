package lab.model;

import lab.model.simple.SimpleContact;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public interface Contact {

    Type getType();

    String getValue();

    enum Type {
        TELEPHONE("^\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?$"),
        EMAIL("^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");

        private Pattern pattern;

        Type(String s) {
            pattern = Pattern.compile(s, CASE_INSENSITIVE);
        }

        public static Optional<Type> from(String contact) {
            return Arrays.stream(Type.values())
                    .filter(type -> type.pattern.matcher(contact).find())
                    .findAny();
        }
    }

    static Contact from(String contact) {
        return new SimpleContact(
                Type.from(contact)
                        .orElseThrow(() -> new RuntimeException(
                                format("Contact %s has unknown format", contact))),
                contact);
    }
}
