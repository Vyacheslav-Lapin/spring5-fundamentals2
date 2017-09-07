package lab.model;

public interface User<T extends User<T>> extends Identifiable<T> {

    String getFirstName();

    String getLastName();
}
