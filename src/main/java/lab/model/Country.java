package lab.model;

import lombok.SneakyThrows;
import lombok.val;

public interface Country {
    int getId();

    @SneakyThrows
    default void setId(long id) {
        val idField = this.getClass().getField("id");
        idField.setAccessible(true);
        idField.set(this, id);
    }

    String getName();
    String getCodeName();
}
