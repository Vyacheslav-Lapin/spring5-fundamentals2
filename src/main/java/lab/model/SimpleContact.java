package lab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(exclude = "id")
//@NoArgsConstructor
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SimpleContact implements Contact, Serializable {
    private long id;
    private Type type;
    private String value;
}
