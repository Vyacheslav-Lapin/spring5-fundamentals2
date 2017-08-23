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

    public SimpleCountry(int id, String name, String codeName) {
        this.id = id;
        this.name = name;
        this.codeName = codeName;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCodeName() {
        return codeName;
    }
}
