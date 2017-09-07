package lab.model.simple;

import lab.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Country")
@Table(name = "country")
//@Component("country")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class SimpleCountry implements Country, Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String codeName;

    public SimpleCountry(String name, String codeName) {
        this(Long.MIN_VALUE, name, codeName);
    }
}
