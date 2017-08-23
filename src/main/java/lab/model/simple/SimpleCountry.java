package lab.model.simple;

import lab.model.Country;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("country")
@Value
public class SimpleCountry implements Country, Serializable {
    private int id;
    private String name;
    private String codeName;
}
