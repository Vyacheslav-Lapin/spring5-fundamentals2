package lab.model;

import lombok.SneakyThrows;
import lombok.val;

import javax.persistence.*;



public interface Country {
    int getId();

    @SneakyThrows
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    default void setId(long id) {
        val idField = this.getClass().getField("id");
        idField.setAccessible(true);
        idField.set(this, id);
    }

    @Column
    String getName();

    @Column(name = "code_name")
    String getCodeName();
}
