package lab.model;

import lombok.*;

import java.io.Serializable;

//@Value
//@EqualsAndHashCode(exclude = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCountry implements Country, Serializable {
	private int id;
    private String name;
    private String codeName;
}
