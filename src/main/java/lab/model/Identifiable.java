package lab.model;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

//@FunctionalInterface
public interface Identifiable<T extends Identifiable<T>> {

    long getId();

    @SneakyThrows
    default T setId(long id) {
        Field field = this.getClass().getField("id");
        field.setAccessible(true);
        field.set(this, id);

        //noinspection unchecked
        return (T) this;
    }
}
