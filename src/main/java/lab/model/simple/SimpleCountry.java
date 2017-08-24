package lab.model.simple;

import lab.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "COUNTRY")
@Table(name = "COUNTRY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SimpleCountry implements Country, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    String codeName;
}
