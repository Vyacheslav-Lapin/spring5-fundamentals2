package lab.model.simple;

import lab.model.Country;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Country")
@Table(name = "country")
@Component("country")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCountry implements Country, Serializable {
    //    @Getter(onMethod = @__({@Id, @GeneratedValue}))
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String codeName;
}
