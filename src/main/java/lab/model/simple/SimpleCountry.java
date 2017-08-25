package lab.model.simple;

import lab.model.Country;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "country")
@Table(name = "country")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component("country")
public class SimpleCountry implements Country, Serializable {
    @Id
    @GeneratedValue
    int id;
    String name;
    String codeName;
}
