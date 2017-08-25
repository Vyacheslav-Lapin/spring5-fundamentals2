package lab.model;

import lombok.SneakyThrows;
import lombok.val;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface Country {
    int getId();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SneakyThrows
    default void setId(long id) {
        val idField = this.getClass().getField("id");
        idField.setAccessible(true);
        idField.set(this, id);
    }

    @Column
    String getName();

    @Column
    String getCodeName();
}
