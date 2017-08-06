package lab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component("country")
@Value
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SimpleCountry implements Country, Serializable {
    private int id;
    private String name;
    private String codeName;
}
