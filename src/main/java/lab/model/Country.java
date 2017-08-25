package lab.model;

import lombok.SneakyThrows;
import lombok.val;

import javax.persistence.*;

@Entity
@Table(name = "Country")
public interface Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO  )
    int getId();

    @SneakyThrows
    default void setId(long id) {
        val idField = this.getClass().getField("id");
        idField.setAccessible(true);
        idField.set(this, id);
    }

    @Column(name = "NAME")
    String getName();

    @Column(name = "CODE_NAME")
    String getCodeName();
}
