package lab.model.simple;

import lab.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

//@Entity(name = "Country")
//@Table
//@Component("country")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class SimpleCountry implements Country, Serializable {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String codeName;
}
